package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.annotation.MetaModel;
import com.github.thinwonton.mybatis.metamodel.core.util.ClassWriterUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;

import javax.annotation.Generated;
import javax.lang.model.element.TypeElement;
import java.io.PrintWriter;
import java.util.List;

public abstract class AbstractClassWriter implements ClassWriter {
    @Override
    public void printGeneratedAnnotation(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw) {
        StringBuilder generatedAnnotation = new StringBuilder();
        generatedAnnotation.append("@")
                .append(metaEntity.importType(Generated.class.getName()))
                .append("(value = \"")
                .append(metaModelGenContext.getMetaModelProducer().getClass().getName())
                .append("\")");
        pw.println(generatedAnnotation.toString());
    }

    @Override
    public void printMetaModelAnnotation(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw) {
        StringBuilder metaModelAnnotation = new StringBuilder();
        metaModelAnnotation.append("@")
                .append(metaEntity.importType(MetaModel.class.getName()))
                .append("(source = ")
                .append(metaEntity.getSimpleName())
                .append(".class")
                .append(")");
        pw.println(metaModelAnnotation.toString());
    }

    @Override
    public void printClassMembers(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw) {
        List<MetaAttributeDescriptor> members = metaEntity.getMembers();
        for (MetaAttributeDescriptor metaMember : members) {
            pw.println("    " + metaMember.getAttributeDeclarationString());
        }
    }

    @Override
    public void printClassDeclaration(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw) {
        pw.print("public abstract class " + metaEntity.getSimpleName() + ClassWriterUtils.META_MODEL_CLASS_NAME_SUFFIX);
        String superClassName = findMappedSuperClass(metaEntity);
        if (superClassName != null) {
            pw.print(" extends " + superClassName + ClassWriterUtils.META_MODEL_CLASS_NAME_SUFFIX);
        }
    }

    private String findMappedSuperClass(MetaEntity entity) {
        TypeElement superclassTypeElement = GenerateUtils.getSuperclassTypeElement(entity.getElement());
        if (superclassTypeElement != null) {
            return superclassTypeElement.getQualifiedName().toString();
        } else {
            return null;
        }
    }
}
