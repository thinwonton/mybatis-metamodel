package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;

public interface MetaAttributeConverter {
    /**
     * 是否过滤该member
     *
     * @param metaModelGenContext
     * @param memberOfClass
     * @return
     */
    boolean filter(MetaModelGenContext metaModelGenContext, Element memberOfClass);

    MetaAttributeDescriptor visitPrimitive(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrimitiveType t, Element element);

    MetaAttributeDescriptor visitDeclared(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, DeclaredType t, Element element);
}
