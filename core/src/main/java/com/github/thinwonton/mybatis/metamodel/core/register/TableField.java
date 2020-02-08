package com.github.thinwonton.mybatis.metamodel.core.register;

import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TableField {

    //对应的表信息
    private Table table;
    private Field field;
//    private Method setter;
//    private Method getter;

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

    public TableField() {
    }

//    /**
//     * 是否有该注解
//     *
//     * @param annotationClass 需要判断是否标注的注解类
//     */
//    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
//        boolean result = false;
//        if (field != null) {
//            result = field.isAnnotationPresent(annotationClass);
//        }
//        if (!result && setter != null) {
//            result = setter.isAnnotationPresent(annotationClass);
//        }
//        if (!result && getter != null) {
//            result = getter.isAnnotationPresent(annotationClass);
//        }
//        return result;
//    }
//
//    /**
//     * 获取指定的注解
//     *
//     * @param annotationClass
//     * @param <T>
//     * @return
//     */
//    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
//        T result = null;
//        if (field != null) {
//            result = field.getAnnotation(annotationClass);
//        }
//        if (result == null && setter != null) {
//            result = setter.getAnnotation(annotationClass);
//        }
//        if (result == null && getter != null) {
//            result = getter.getAnnotation(annotationClass);
//        }
//        return result;
//    }

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

    public void setField(Field field) {
        this.field = field;
    }

//    public Method getSetter() {
//        return setter;
//    }
//
//    public void setSetter(Method setter) {
//        this.setter = setter;
//    }
//
//    public Method getGetter() {
//        return getter;
//    }
//
//    public void setGetter(Method getter) {
//        this.getter = getter;
//    }
}
