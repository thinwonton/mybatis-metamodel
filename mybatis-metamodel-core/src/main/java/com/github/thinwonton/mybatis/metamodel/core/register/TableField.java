package com.github.thinwonton.mybatis.metamodel.core.register;

import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;

public class TableField {

    //对应的表信息
    private Table table;
    private final Field field;
    //是否ID字段
    private boolean id;
    //实体类的属性名
    private String property;
    //数据库中的列名
    private String column;
    //java类型
    private Class<?> javaType;
    //jdbc类型
    private JdbcType jdbcType;

    public TableField(Table table, Field field) {
        this.table = table;
        this.field = field;
    }

    public void markId() {
        this.id = true;
    }

    public boolean isId() {
        return this.id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Field getField() {
        return field;
    }
}
