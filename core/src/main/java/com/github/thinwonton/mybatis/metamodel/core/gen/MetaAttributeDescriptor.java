package com.github.thinwonton.mybatis.metamodel.core.gen;

public interface MetaAttributeDescriptor {
    /**
     * 属性名
     * @return
     */
    String getPropertyName();

    /**
     * 该属性的宿主实体
     * @return
     */
    MetaEntity getHostingEntity();

    /**
     * 在meta model中对应的类型
     * @return
     */
    String getMetaType();

    /**
     * 该属性在宿主实体中声明的类型
     * @return
     */
    String getTypeDeclaration();

    /**
     * 获取在meta model中该属性的声明类型字符串
     * @return
     */
    String getAttributeDeclarationString();
}
