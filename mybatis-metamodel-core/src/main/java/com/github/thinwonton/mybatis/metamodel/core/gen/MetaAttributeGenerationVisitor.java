package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.util.SimpleTypeVisitor6;

/**
 * MetaAttributeGenerationVisitor
 */
public class MetaAttributeGenerationVisitor extends SimpleTypeVisitor6<MetaAttributeDescriptor, Element> {
    private final MetaEntity metaEntity;
    private final MetaModelGenContext metaModelGenContext;
    private final MetaAttributeConverter metaAttributeConverter;

    public MetaAttributeGenerationVisitor(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, MetaAttributeConverter metaAttributeConverter) {
        this.metaEntity = metaEntity;
        this.metaModelGenContext = metaModelGenContext;
        this.metaAttributeConverter = metaAttributeConverter;
    }

    @Override
    public MetaAttributeDescriptor visitPrimitive(PrimitiveType t, Element element) {
        return metaAttributeConverter.visitPrimitive(metaModelGenContext, metaEntity, t, element);
    }

    @Override
    public MetaAttributeDescriptor visitDeclared(DeclaredType t, Element element) {
        return metaAttributeConverter.visitDeclared(metaModelGenContext, metaEntity, t, element);
    }
}
