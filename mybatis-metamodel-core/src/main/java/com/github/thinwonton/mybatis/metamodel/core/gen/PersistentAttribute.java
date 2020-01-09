package com.github.thinwonton.mybatis.metamodel.core.gen;

/**
 * PersistentAttribute
 */
public interface PersistentAttribute {

    /**
     * 获取数据库中对应的列名
     *
     * @return
     */
    String getColumn();

    /**
     * 获取该属性在实体中的java类型
     *
     * @return
     */
    Class<?> getJavaType();

    /**
     * 是否是ID字段
     * @return
     */
    boolean isId();

    /**
     * 获取属性名
     * @return
     */
    String getProperty();
}
