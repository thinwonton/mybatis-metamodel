package com.github.thinwonton.mybatis.metamodel.core.register;

import com.github.thinwonton.mybatis.metamodel.core.annotation.MetaModel;
import com.github.thinwonton.mybatis.metamodel.core.exception.MetaModelRegisterException;
import com.github.thinwonton.mybatis.metamodel.core.gen.PersistentAttribute;
import com.github.thinwonton.mybatis.metamodel.core.gen.PersistentAttributeImpl;
import com.github.thinwonton.mybatis.metamodel.core.util.ClassWriterUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.ReflectionUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.*;

public class MetaModelContext {
    private final Configuration configuration;

    private final EntityResolver entityResolver;

    private final Set<Class<?>> processedMetaModelClasses = new HashSet<>();

    private final Map<Class<?>, Table> tableMapping = new HashMap<>();

    public MetaModelContext(Configuration configuration, EntityResolver entityResolver) {
        this.configuration = configuration;
        this.entityResolver = entityResolver;
        registerMetaModels();
    }

    private void registerMetaModels() {
        for (Object object : new ArrayList<Object>(configuration.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement mappedStatement = (MappedStatement) object;

                //mappedStatement映射的实体类
                Class<?> entityClass = entityResolver.getMappedEntityClass(mappedStatement);

                //创建table
                String tableName = entityResolver.resolveTableName(entityClass);
                Table table = new Table(entityClass, tableName);
                table.setTableName(tableName);

                //根据实体类获取对应的metaModel类
                Class<?> metaModelClass = tryGettingMappedMetaModelClass(entityClass);

                //通过metaModel类，遍历获取所有的父类
                Set<Class<?>> processingMetaModelSet = new HashSet<>();
                if (metaModelClass != null) {
                    processingMetaModelSet.add(metaModelClass);
                    Set<Class<?>> founds = lookupSuperMetaModelClasses(metaModelClass);
                    processingMetaModelSet.addAll(founds);
                }

                //对MetaModel注入metaAttribute
                for (Class<?> processingMetaModel : processingMetaModelSet) {
                    if (processedMetaModelClasses.add(processingMetaModel)) {
                        registerMetaModel(table, processingMetaModel);
                    }
                }

            }
        }
    }

    private void registerMetaModel(Table table, final Class<?> metaModelClass) {
        Class<?> entityClass = loadEntityClass(metaModelClass);

        Collection<TableField> tableFields = entityResolver.resolveTableFields(table, entityClass);
        table.addTableFields(tableFields);

        tableMapping.put(metaModelClass, table);

        // 转换为map,方便查找
        Map<String, TableField> tableFieldMap = new HashMap<>();
        for (TableField tableField : tableFields) {
            tableFieldMap.put(tableField.getProperty(), tableField);
        }

        //注入metamodel的属性
        for (Field field : metaModelClass.getDeclaredFields()) {
            try {
                TableField tableField = tableFieldMap.get(field.getName());
                PersistentAttribute persistentAttribute = new PersistentAttributeImpl(tableField);
                field.set(null, persistentAttribute);
            } catch (IllegalAccessException e) {
                throw new MetaModelRegisterException(
                        "Unable to inject static metamodel attribute : " + metaModelClass.getName() + '#' + field.getName(),
                        e);
            }
        }
    }

    private Class<?> loadEntityClass(Class<?> metaModelClass) {
        String entityClassName = ClassWriterUtils.getMappedEntityClassName(metaModelClass);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return ReflectionUtils.loadClass(entityClassName, classLoader);
    }

    /**
     * 获取所有的父类 metaModel
     *
     * @param clazz
     * @return
     */
    private Set<Class<?>> lookupSuperMetaModelClasses(Class<?> clazz) {
        Class<?> children = clazz;
        Set<Class<?>> founds = new HashSet<>();
        Class<?> parent = null;
        while ((parent = children.getSuperclass()) != null) {
            children = parent;
            if (parent.isAnnotationPresent(MetaModel.class)) {
                founds.add(parent);
            }
        }
        return founds;
    }

    private Class<?> tryGettingMappedMetaModelClass(Class<?> entityClass) {
        final String metaModelClassName = ClassWriterUtils.getMappedMetaModelClassName(entityClass);
        Class<?> clazz = ReflectionUtils.loadClass(metaModelClassName, Thread.currentThread().getContextClassLoader());
        //如果有@MetaModel注解则为metaModel
        if (clazz != null && clazz.isAnnotationPresent(MetaModel.class)) {
            return clazz;
        }
        return null;
    }

}
