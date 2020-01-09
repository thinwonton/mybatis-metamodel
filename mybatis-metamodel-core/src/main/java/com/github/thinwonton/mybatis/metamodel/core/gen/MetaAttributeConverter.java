package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.util.AccessType;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;

public interface MetaAttributeConverter {
    boolean canConvert(Element memberOfClass, AccessType accessType);

    MetaAttributeDescriptor visitPrimitive(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrimitiveType t, Element element);

    MetaAttributeDescriptor visitDeclared(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, DeclaredType t, Element element);
}
