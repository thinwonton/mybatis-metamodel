package com.github.thinwonton.mybatis.metamodel.core.gen;

import java.io.PrintWriter;

public interface ClassWriter {
    /**
     * 生成 {@link javax.annotation.Generated} 注解的内容
     * @param metaModelGenContext
     * @param metaEntity
     * @param pw
     */
    void printGeneratedAnnotation(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw);

    /**
     * 生成 {@link com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel} 注解的内容
     * @param metaModelGenContext
     * @param metaEntity
     * @param pw
     */
    void printMetaModelAnnotation(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw);

    /**
     * 生成类的成员
     * @param metaModelGenContext
     * @param metaEntity
     * @param pw
     */
    void printClassMembers(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw);

    /**
     * 生成类的声明
     * @param metaModelGenContext
     * @param metaEntity
     * @param pw
     */
    void printClassDeclaration(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, PrintWriter pw);
}
