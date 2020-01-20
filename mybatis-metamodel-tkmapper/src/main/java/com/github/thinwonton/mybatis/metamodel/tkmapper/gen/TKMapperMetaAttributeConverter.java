package com.github.thinwonton.mybatis.metamodel.tkmapper.gen;

import com.github.thinwonton.mybatis.metamodel.core.gen.*;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.TypeUtils;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * TKMapper
 */
public class TKMapperMetaAttributeConverter implements MetaAttributeConverter {

    @Override
    public boolean filter(MetaModelGenContext metaModelGenContext, Element memberOfClass) {
        TKMapperConfig tkMapperConfig = metaModelGenContext.getTkMapperConfig();
        // 以下情况忽略生成 meta model 的 attr
        // 1. javax.persistence.Transient注解修饰的成员
        // 2. TRANSIENT 修饰词
        // 3. STATIC 修饰词
        return GenerateUtils.containsAnnotation(memberOfClass, Transient.class.getCanonicalName())
                || memberOfClass.getModifiers().contains(Modifier.TRANSIENT)
                || memberOfClass.getModifiers().contains(Modifier.STATIC)
                ;
    }

    @Override
    public MetaAttributeDescriptor visitPrimitive(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrimitiveType t, Element element) {
        TKMapperConfig tkMapperConfig = metaModelGenContext.getTkMapperConfig();
        if ((tkMapperConfig.isUseSimpleType() && tkMapperConfig.isUsePrimitiveType())
                || (GenerateUtils.containsAnnotation(element, Column.class.getName(), ColumnType.class.getName()))
        ) {
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
        //类型
        TypeElement returnedElement = (TypeElement) metaModelGenContext.getProcessingEnvironment().getTypeUtils().asElement(t);
        String typeName = returnedElement.getQualifiedName().toString();

        TKMapperConfig tkMapperConfig = metaModelGenContext.getTkMapperConfig();

        if ((ElementKind.CLASS.equals(returnedElement.getKind()) || ElementKind.INTERFACE.equals(returnedElement.getKind()))
                &&
                ((tkMapperConfig.isUseSimpleType() && TypeUtils.isSimpleType(typeName))
                        ||
                        GenerateUtils.containsAnnotation(element, Column.class.getName(), ColumnType.class.getName())
                )
        ) {
            return new DefaultMetaAttributeDescriptor(
                    metaEntity,
                    element,
                    typeName
            );
        }

        // 处理 ENUM
        if (ElementKind.ENUM.equals(returnedElement.getKind())) {
            // 有注解标注或者开启了enumAsSimpleType
            if (GenerateUtils.containsAnnotation(element, Column.class.getName(), ColumnType.class.getName())
                    ||
                    (tkMapperConfig.isUseSimpleType() && tkMapperConfig.isEnumAsSimpleType())
            ) {
                return new DefaultMetaAttributeDescriptor(
                        metaEntity,
                        element,
                        typeName
                );
            }
        }

        return null;
    }

}
