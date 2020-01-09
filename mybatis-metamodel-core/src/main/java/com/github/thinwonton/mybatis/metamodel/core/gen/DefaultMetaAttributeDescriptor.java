package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.lang.model.element.Element;

public class DefaultMetaAttributeDescriptor implements MetaAttributeDescriptor {
    //对应的元素.类的成员变量
    private final Element element;
    //所属的持久化实体
    private final MetaEntity hostingEntity;
    //属性的类型全名
    private final String type;

    public DefaultMetaAttributeDescriptor(MetaEntity hostingEntity, Element element, String type) {
        this.element = element;
        this.hostingEntity = hostingEntity;
        this.type = type;
    }

    @Override
    public String getAttributeDeclarationString() {
        return new StringBuilder().append("public static volatile ")
                .append(hostingEntity.importType(getMetaType()))
                .append(" ")
                .append(getPropertyName())
                .append(";")
                .toString();
    }

    @Override
    public String getPropertyName() {
        return element.getSimpleName().toString();
    }

    @Override
    public MetaEntity getHostingEntity() {
        return hostingEntity;
    }

    public String getTypeDeclaration() {
        return type;
    }

    @Override
    public String getMetaType() {
        return PersistentAttribute.class.getCanonicalName();
    }
}