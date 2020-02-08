package com.github.thinwonton.mybatis.metamodel.core.gen;

import org.apache.ibatis.type.JdbcType;

/**
 * PersistentAttribute
 */
public interface PersistentAttribute {

    /**
     * 获取数据库中对应的列名
     */
    String getColumn();

    /**
     * 获取该属性在实体中的java类型
     */
    Class<?> getJavaType();

    /**
     * 是否是ID字段
     */
    boolean isId();

    /**
     * 获取属性名
     */
    String getProperty();

    /**
     * 获取jdbc type
     */
    JdbcType getJdbcType();
}
