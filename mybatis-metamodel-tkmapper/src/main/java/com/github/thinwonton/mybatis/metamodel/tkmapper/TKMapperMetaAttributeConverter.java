package com.github.thinwonton.mybatis.metamodel.tkmapper;

import com.github.thinwonton.mybatis.metamodel.core.gen.*;
import com.github.thinwonton.mybatis.metamodel.core.util.AccessType;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;

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
    public boolean canConvert(Element memberOfClass, AccessType accessType) {
        //TODO 处理置于getter/setter的property
        if (AccessType.PROPERTY.equals(accessType)) {
            return false;
        }

        return !GenerateUtils.containsAnnotation(memberOfClass, Transient.class.getCanonicalName()) //不是Transient注解修饰的成员
                && !memberOfClass.getModifiers().contains(Modifier.TRANSIENT) //不是transient修饰的成员
                && !memberOfClass.getModifiers().contains(Modifier.STATIC); //不是static修饰的成员
    }

    @Override
    public MetaAttributeDescriptor visitPrimitive(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrimitiveType t, Element element) {
        // 原语类型不支持
        return null;
    }

    @Override
    public MetaAttributeDescriptor visitDeclared(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, DeclaredType t, Element element) {
        TypeElement returnedElement = (TypeElement) metaModelGenContext.getProcessingEnvironment().getTypeUtils().asElement(t);
        String typeName = returnedElement.getQualifiedName().toString();

        //转换java基本Class类型
        if (ElementKind.CLASS.equals(returnedElement.getKind())
                && isJavaBasicTypes(typeName)) {
            return new DefaultMetaAttributeDescriptor(
                    metaEntity,
                    element,
                    returnedElement.getQualifiedName().toString()
            );
        }

        //TODO 处理 ENUM

        return null;
    }


    private boolean isJavaBasicTypes(String typeName) {
        return GenerateUtils.BASIC_WRAPPER_TYPES.contains(typeName);
    }
}
