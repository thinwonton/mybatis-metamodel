package com.github.thinwonton.mybatis.metamodel.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.thinwonton.mybatis.metamodel.core.gen.*;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.TypeUtils;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;

public class MybatisPlusMetaAttributeConverter implements MetaAttributeConverter {

    @Override
    public boolean filter(Element memberOfClass) {
        //TableField注解标注为exist=false
        boolean isTableField = true;
        AnnotationMirror annotationMirror = GenerateUtils.getAnnotationMirror(memberOfClass, TableField.class.getCanonicalName());
        if (annotationMirror != null) {
            Object exist = GenerateUtils.getAnnotationValue(annotationMirror, "exist");
            if (exist != null) {
                isTableField = (boolean) exist;
            }
        }

        // 以下情况忽略生成 meta model 的 attr
        // 1. TRANSIENT 修饰词
        // 2. STATIC 修饰词
        // 3. 原语类型
        return memberOfClass.getModifiers().contains(Modifier.TRANSIENT)
                || memberOfClass.getModifiers().contains(Modifier.STATIC)
                || !isTableField;
    }

    @Override
    public MetaAttributeDescriptor visitPrimitive(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrimitiveType t, Element element) {
        return new DefaultMetaAttributeDescriptor(
                metaEntity,
                element,
                TypeUtils.toTypeString(t)
        );
    }

    @Override
    public MetaAttributeDescriptor visitDeclared(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, DeclaredType t, Element element) {
        TypeElement returnedElement = (TypeElement) metaModelGenContext.getProcessingEnvironment().getTypeUtils().asElement(t);
        String typeName = returnedElement.getQualifiedName().toString();

        //转换java基本Class类型
        if (ElementKind.CLASS.equals(returnedElement.getKind())
                && TypeUtils.isSimpleType(typeName)) {
            return new DefaultMetaAttributeDescriptor(
                    metaEntity,
                    element,
                    returnedElement.getQualifiedName().toString()
            );
        }

        //TODO 处理 ENUM

        return null;
    }

}
