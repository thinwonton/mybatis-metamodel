package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import com.github.thinwonton.mybatis.metamodel.core.util.ClassWriterUtils;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public abstract class AbstractMetaModelGenProcessor extends AbstractProcessor {
    //打印processor日志的开关
    public static final String DEBUG_OPTION = "debug";

    private static final Boolean ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS = false;

    private MetaModelGenContext metaModelGenContext;

    //meta model的writer
    private ClassWriter classWriter;

    private boolean initialized = false;

    public abstract ElementResolver getElementResolver(MetaModelGenContext metaModelGenContext, TypeElement element);

    public abstract ClassWriter getClassWriter(MetaModelGenContext metaModelGenContext);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        if (!isInitialized()) {
            super.init(processingEnv);

            this.metaModelGenContext = new MetaModelGenContext(processingEnv, this);
            this.classWriter = getClassWriter(metaModelGenContext);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(GenMetaModel.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver() || annotations.size() == 0) {
            return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
        }

        //获取注解标识的类及其父类
        Set<TypeElement> processingEntityElements = new HashSet<>();
        for (Element rootElement : roundEnvironment.getRootElements()) {
            Set<TypeElement> elements = resolveElements(rootElement);
            processingEntityElements.addAll(elements);
        }

        //解析
        for (TypeElement element : processingEntityElements) {
            String elementName = element.getQualifiedName().toString();
            if (!metaModelGenContext.isAlreadyResolved(elementName)) {
                metaModelGenContext.logMessage(Diagnostic.Kind.OTHER, "Processing orm class element " + element.toString());
                MetaEntity metaEntity = getElementResolver(metaModelGenContext, element).resolveElement(element);
                metaModelGenContext.addResolvedMetaEntity(elementName, metaEntity);
            }
        }

        createMetaModelClasses();

        return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
    }

    /**
     * 获取注解标识的类及其父类
     */
    private Set<TypeElement> resolveElements(Element rootElement) {
        Set<TypeElement> result = new HashSet<>();
        //orm entity标注了@GenMetaModel才处理
        if (markedSupportedAnnotation(rootElement)) {
            TypeElement childrenElement;
            childrenElement = (TypeElement) rootElement;
            result.add(childrenElement);

            //遍历获取父类的element
            TypeElement superclassTypeElement;
            while ((superclassTypeElement = GenerateUtils.getSuperclassTypeElement(childrenElement)) != null) {
                result.add(superclassTypeElement);
                childrenElement = superclassTypeElement;
            }
        }
        return result;
    }

    /**
     * 创建类文件
     */
    private void createMetaModelClasses() {
        for (MetaEntity entity : metaModelGenContext.getResolvedMetaEntities()) {
            if (metaModelGenContext.isAlreadyGenerated(entity.getQualifiedName())) {
                continue;
            }
            metaModelGenContext.logMessage(Diagnostic.Kind.OTHER, "Writing meta model for entity " + entity.getQualifiedName());
            ClassWriterUtils.writeFile(entity, classWriter);
            metaModelGenContext.markGenerated(entity.getQualifiedName());
        }
    }

    private boolean markedSupportedAnnotation(Element element) {
        return GenerateUtils.containsAnnotation(element, getSupportedAnnotationTypes())
                && isClassElement(element);
    }

    private boolean isClassElement(Element element) {
        return ElementKind.CLASS.equals(element.getKind());
    }


}
