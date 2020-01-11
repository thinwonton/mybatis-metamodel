package com.github.thinwonton.mybatis.metamodel.core.register;

import java.util.Collection;
import java.util.HashSet;

/**
 * Table
 */
public class Table {

    // 对应的实体名
    private Class<?> entityClass;

    // 数据库的schema
    private String schema;

    // 表名
    private String tableName;

    private Collection<TableField> tableFields = new HashSet<>();

    public Table(Class<?> entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addTableFields(Collection<TableField> tableFields) {
        if (tableFields != null) {
            this.tableFields.addAll(tableFields);
        }
    }

    public Collection<TableField> getTableFields() {
        return tableFields;
    }

}
