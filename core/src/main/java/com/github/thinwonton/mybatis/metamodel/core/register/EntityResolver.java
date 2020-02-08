package com.github.thinwonton.mybatis.metamodel.core.register;

import org.apache.ibatis.mapping.MappedStatement;

import java.util.Collection;

public interface EntityResolver {
    /**
     * 获取 MappedStatement 对应的 数据库实体类
     */
    Class<?> getMappedEntityClass(MappedStatement mappedStatement);

    /**
     * 解析不带schema的表名
     * @param globalConfig
     * @param entityClass
     * @return
     */
    String resolveSimpleTableName(GlobalConfig globalConfig, Class<?> entityClass);

    /**
     * 获取数据表的字段信息
     * @param globalConfig
     * @param table
     * @param entityClass
     * @return
     */
    Collection<TableField> resolveTableFields(GlobalConfig globalConfig, Table table, Class<?> entityClass);

    Table.CatalogSchemaInfo resolveTableCatalogSchemaInfo(GlobalConfig globalConfig, Class<?> entityClass);
}
