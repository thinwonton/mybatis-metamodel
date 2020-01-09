package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.lang.model.element.TypeElement;

public class DefaultElementResolver implements ElementResolver {
    private MetaModelGenContext metaModelGenContext;

    private final ImportContext importContext;

    private final MetaAttributeConverter metaAttributeConverter;

    public DefaultElementResolver(MetaModelGenContext metaModelGenContext, ImportContext importContext, MetaAttributeConverter metaAttributeConverter) {
        this.metaModelGenContext = metaModelGenContext;
        this.importContext = importContext;
        this.metaAttributeConverter = metaAttributeConverter;
    }

    @Override
    public MetaEntity resolveElement(TypeElement element) {
        return new DefaultMetaEntity(metaModelGenContext, element, importContext, metaAttributeConverter);
    }
}
