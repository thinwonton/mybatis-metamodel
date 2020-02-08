package com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.gen;

import com.github.thinwonton.mybatis.metamodel.core.gen.*;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;

import javax.lang.model.element.TypeElement;

public class MybatisPlusMetaModelGenProcessor extends AbstractMetaModelGenProcessor {

    @Override
    public ElementResolver getElementResolver(MetaModelGenContext metaModelGenContext, TypeElement element) {
        ImportContext importContext = new ImportContextImpl(GenerateUtils.getPackageName(metaModelGenContext, element));
        return new DefaultElementResolver(metaModelGenContext, importContext, new MybatisPlusMetaAttributeConverter());
    }

    @Override
    public ClassWriter getClassWriter(MetaModelGenContext metaModelGenContext) {
        return new MybatisPlusClassWriter();
    }
}
