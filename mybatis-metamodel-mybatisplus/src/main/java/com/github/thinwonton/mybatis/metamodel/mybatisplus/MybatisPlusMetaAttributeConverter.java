package com.github.thinwonton.mybatis.metamodel.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.thinwonton.mybatis.metamodel.core.gen.*;
import com.github.thinwonton.mybatis.metamodel.core.util.AccessType;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;

public class MybatisPlusMetaAttributeConverter implements MetaAttributeConverter {

    @Override
    public boolean canConvert(Element memberOfClass, AccessType accessType) {
        //TODO 处理置于getter/setter的property
        if (AccessType.PROPERTY.equals(accessType)) {
            return false;
        }

        //获取TableField注解
        boolean isTableField = true;
        AnnotationMirror annotationMirror = GenerateUtils.getAnnotationMirror(memberOfClass, TableField.class.getCanonicalName());
        if (annotationMirror != null) {
            Object existObject = GenerateUtils.getAnnotationValue(annotationMirror, "exist");
            if (existObject != null) {
                isTableField = (boolean) existObject;
            }
        }
        return !memberOfClass.getModifiers().contains(Modifier.TRANSIENT) //不是transient修饰的成员
                && !memberOfClass.getModifiers().contains(Modifier.STATIC) //不是static修饰的成员
                && isTableField;
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
