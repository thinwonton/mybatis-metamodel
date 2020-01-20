package com.github.thinwonton.mybatis.metamodel.tkmapper;

import com.github.thinwonton.mybatis.metamodel.core.gen.*;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.TypeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.persistence.Transient;

/**
 * TKMapper
 */
public class TKMapperMetaAttributeConverter implements MetaAttributeConverter {

    @Override
    public boolean filter(Element memberOfClass) {
        // 以下情况忽略生成 meta model 的 attr
        // 1. javax.persistence.Transient注解修饰的成员
        // 2. TRANSIENT 修饰词
        // 3. STATIC 修饰词
        return GenerateUtils.containsAnnotation(memberOfClass, Transient.class.getCanonicalName())
                || memberOfClass.getModifiers().contains(Modifier.TRANSIENT)
                || memberOfClass.getModifiers().contains(Modifier.STATIC);
    }

    @Override
    public MetaAttributeDescriptor visitPrimitive(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrimitiveType t, Element element) {
        //对于tk mapper，有个全局配置 usePrimitiveType 控制是否对原语类型生成meta attr
        if (metaModelGenContext.isUsePrimitiveType()) {
            return new DefaultMetaAttributeDescriptor(
                    metaEntity,
                    element,
                    TypeUtils.toTypeString(t)
            );
        }
        return null;
    }

    @Override
    public MetaAttributeDescriptor visitDeclared(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, DeclaredType t, Element element) {
        TypeElement returnedElement = (TypeElement) metaModelGenContext.getProcessingEnvironment().getTypeUtils().asElement(t);
        String typeName = returnedElement.getQualifiedName().toString();

        //是简单包装类
        if (ElementKind.CLASS.equals(returnedElement.getKind()) && TypeUtils.isSimpleType(typeName)) {
            return new DefaultMetaAttributeDescriptor(
                    metaEntity,
                    element,
                    returnedElement.getQualifiedName().toString()
            );
        }

        if (ElementKind.ENUM.equals(returnedElement.getKind())) {
            //TODO 处理 ENUM
        }

        // TODO 实体类型的, Collection/Map

        return null;
    }

}
