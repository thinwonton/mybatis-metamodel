package com.github.thinwonton.mybatis.metamodel.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.Table;
import com.github.thinwonton.mybatis.metamodel.core.register.TableField;
import com.github.thinwonton.mybatis.metamodel.core.util.RegisterUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MybatisPlusEntityResolver
 */
public class MybatisPlusEntityResolver implements EntityResolver {
    @Override
    public Class<?> getMappedEntityClass(MappedStatement mappedStatement) {
        String mappedStatementId = mappedStatement.getId();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //获取 mappedStatement 对应的 mapper类
        Class<?> mapperClass = RegisterUtils.getMapperClass(mappedStatementId, classLoader);

        //通过 BaseMapper 或者 Mapper 接口，获取entity
        Type[] types = mapperClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                Type rawType = t.getRawType();
                Class<?> rawClass = (Class<?>) rawType;
                if (BaseMapper.class.isAssignableFrom(rawClass) || Mapper.class.isAssignableFrom(rawClass)) {
                    //获取泛型
                    return (Class<?>) t.getActualTypeArguments()[0];
                }
            }
        }

        return null;
    }

    @Override
    public String resolveSimpleTableName(GlobalConfig globalConfig, Class<?> entityClass) {
        String tableName = null;
        if (entityClass.isAnnotationPresent(TableName.class)) {
            TableName tableNameAnnotation = entityClass.getAnnotation(TableName.class);
            if (StringUtils.isNotEmpty(tableNameAnnotation.value())) {
                tableName = tableNameAnnotation.value();
            }
        }

        if (tableName == null) {
            //TODO 根据规则处理表名
            tableName = entityClass.getSimpleName();
        }
        return tableName;
    }

    @Override
    public Collection<TableField> resolveTableFields(GlobalConfig globalConfig, Table table, Class<?> entityClass) {
        //TODO 处理setter里面的属性
        List<TableField> tableFields = new ArrayList<>();
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field field : declaredFields) {
            TableField tableField = resolveTableField(table, entityClass, field);
            if (tableField != null) {
                tableFields.add(tableField);
            }
        }
        return tableFields;
    }

    @Override
    public Table.CatalogSchemaInfo resolveTableCatalogSchemaInfo(GlobalConfig globalConfig, Class<?> entityClass) {
        Table.CatalogSchemaInfo catalogSchemaInfo = new Table.CatalogSchemaInfo();

        if (entityClass.isAnnotationPresent(TableName.class)) {
            TableName table = entityClass.getAnnotation(TableName.class);
            catalogSchemaInfo.setSchema(table.schema());
        }

        catalogSchemaInfo.setGlobalCatalog(globalConfig.getCatalog());
        catalogSchemaInfo.setGlobalSchema(globalConfig.getSchema());

        return catalogSchemaInfo;
    }

    private TableField resolveTableField(Table table, Class<?> entityClass, Field field) {
        //排除 static，Transient
        //TODO 过滤声明类型
        com.baomidou.mybatisplus.annotation.TableField tableFieldAnnotation = field.getAnnotation(com.baomidou.mybatisplus.annotation.TableField.class);
        boolean markedNotTableField = (tableFieldAnnotation != null && !tableFieldAnnotation.exist());
        if (Modifier.isStatic(field.getModifiers())
                || Modifier.isTransient(field.getModifiers())
                || markedNotTableField) {
            return null;
        }

        TableField tableField = new TableField(table, field);

        //Id信息
        if (field.isAnnotationPresent(TableId.class)) {
            tableField.markId();
        }

        //属性名
        tableField.setProperty(field.getName());

        //column name
        String columnName = getColumnName(field, tableField.isId());
        tableField.setColumn(columnName);

        //java type
        tableField.setJavaType(field.getType());

        // jdbcType
        JdbcType jdbcType = JdbcType.UNDEFINED;
        if (tableFieldAnnotation != null && tableFieldAnnotation.jdbcType() != JdbcType.UNDEFINED) {
            jdbcType = tableFieldAnnotation.jdbcType();
        }
        tableField.setJdbcType(jdbcType);

        return tableField;
    }

    private String getColumnName(Field field, boolean isIdColumn) {

        String columnName;
        if (isIdColumn) {
            //处理ID列
            TableId tableIdAnnotation = field.getAnnotation(TableId.class);
            if (StringUtils.isNotEmpty(tableIdAnnotation.value())) {
                columnName = tableIdAnnotation.value();
            } else {
                columnName = field.getName();
                // //TODO 需要根据配置规则获取命名
            }
        } else {
            //处理非ID列
            com.baomidou.mybatisplus.annotation.TableField tableFieldAnnotation = field.getAnnotation(com.baomidou.mybatisplus.annotation.TableField.class);
            if (tableFieldAnnotation != null && StringUtils.isNotEmpty(tableFieldAnnotation.value())) {
                columnName = tableFieldAnnotation.value();
            } else {
                columnName = field.getName();
                // //TODO 需要根据配置规则获取命名
            }
        }
        return columnName;
    }
}
