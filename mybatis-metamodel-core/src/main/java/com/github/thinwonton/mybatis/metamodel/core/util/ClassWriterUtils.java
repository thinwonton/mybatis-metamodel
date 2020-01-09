package com.github.thinwonton.mybatis.metamodel.core.util;

import com.github.thinwonton.mybatis.metamodel.core.gen.ClassWriter;
import com.github.thinwonton.mybatis.metamodel.core.gen.MetaEntity;
import com.github.thinwonton.mybatis.metamodel.core.gen.MetaModelGenContext;

import javax.annotation.processing.FilerException;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 类生成器
 *
 * @author hugo
 * @date 2019/12/26
 */
public class ClassWriterUtils {
    public static final String META_MODEL_CLASS_NAME_SUFFIX = "_";

    public static String getMappedMetaModelClassName(Class<?> entityClass) {
        return entityClass.getName() + META_MODEL_CLASS_NAME_SUFFIX;
    }

    public static String getMappedEntityClassName(Class<?> metaModelClass) {
        int length = metaModelClass.getCanonicalName().length();
        return metaModelClass.getName().substring(0, length - 1);
    }

    /**
     * 写 metaModel 源文件
     */
    public static void writeFile(MetaEntity metaEntity, ClassWriter classWriter) {

        MetaModelGenContext metaModelGenContext = metaEntity.getMetaModelGenContext();

        OutputStream os = null;
        PrintWriter pw = null;

        try {
            String metaModelPackage = metaEntity.getPackageName();

            String metaModelBody = generateBody(metaModelGenContext, metaEntity, classWriter);

            String metaModelImports = generateImports(metaEntity, metaModelGenContext);

            JavaFileObject metaModelSourceFile = metaModelGenContext.getProcessingEnvironment().getFiler().createSourceFile(
                    getFullyQualifiedClassName(metaEntity, metaModelPackage)
            );

            os = metaModelSourceFile.openOutputStream();
            pw = new PrintWriter(os);

            if (!metaModelPackage.isEmpty()) {
                pw.println("package " + metaModelPackage + ";");
                pw.println();
            }
            pw.println(metaModelImports);
            pw.println(metaModelBody);

            pw.flush();
        } catch (FilerException filerEx) {
            metaModelGenContext.logMessage(
                    Diagnostic.Kind.ERROR, "Problem with Filer: " + filerEx.getMessage()
            );
        } catch (IOException ioEx) {
            metaModelGenContext.logMessage(
                    Diagnostic.Kind.ERROR,
                    "Problem opening file to write MetaModel for " + metaEntity.getQualifiedName() + ioEx.getMessage()
            );
        } finally {
            if (pw != null) {
                pw.close();
            }

        }

    }

    private static String getFullyQualifiedClassName(MetaEntity metaEntity, String metaModelPackage) {
        String fullyQualifiedClassName = "";
        if (!metaModelPackage.isEmpty()) {
            fullyQualifiedClassName = fullyQualifiedClassName + metaModelPackage + ".";
        }
        fullyQualifiedClassName = fullyQualifiedClassName + metaEntity.getSimpleName() + META_MODEL_CLASS_NAME_SUFFIX;
        return fullyQualifiedClassName;
    }

    /**
     * 生成 import 导入部分
     *
     * @param metaEntity
     * @param metaModelGenContext
     * @return
     */
    private static String generateImports(MetaEntity metaEntity, MetaModelGenContext metaModelGenContext) {
        return metaEntity.generateImports();
    }


    /**
     * 生成类中import块以下部分
     *
     * @param metaEntity
     * @param metaModelGenContext
     * @return
     */
    private static String generateBody(MetaModelGenContext metaModelGenContext, MetaEntity metaEntity, ClassWriter classWriter) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = null;
        pw = new PrintWriter(sw);

        //生成 @Generated
        classWriter.printGeneratedAnnotation(metaModelGenContext, metaEntity, pw);

        //生成注解 @Metamodel
        classWriter.printMetaModelAnnotation(metaModelGenContext, metaEntity, pw);

        //添加类的声明
        classWriter.printClassDeclaration(metaModelGenContext, metaEntity, pw);

        pw.println(" {");

        pw.println();

        //添加成员变量
        classWriter.printClassMembers(metaModelGenContext, metaEntity, pw);

        pw.println();

        pw.println("}");

        return sw.getBuffer().toString();
    }

}
