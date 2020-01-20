package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Context
 */
public final class MetaModelGenContext {
    private final ProcessingEnvironment processingEnvironment;

    private final Processor metaModelProducer;

    private boolean logDebug = false;

    private TKMapperConfig tkMapperConfig = new TKMapperConfig();

    /**
     * 已经解析的orm entity
     */
    private final Map<String, MetaEntity> resolvedMetaEntities = new HashMap<>();

    private final Collection<String> generatedModelClasses = new HashSet<>();

    public MetaModelGenContext(ProcessingEnvironment processingEnvironment, Processor metaModelProducer) {
        this.processingEnvironment = processingEnvironment;
        this.metaModelProducer = metaModelProducer;
        config();
    }

    private void config() {
        logDebug = Boolean.parseBoolean(processingEnvironment.getOptions().get(AbstractMetaModelGenProcessor.DEBUG_OPTION));
        boolean usePrimitiveType = Boolean.parseBoolean(processingEnvironment.getOptions().get(AbstractMetaModelGenProcessor.USE_PRIMITIVE_TYPE_OPTION));
        boolean enumAsSimpleType = Boolean.parseBoolean(processingEnvironment.getOptions().get(AbstractMetaModelGenProcessor.ENUM_AS_SIMPLE_TYPE_OPTION));

        tkMapperConfig.setUsePrimitiveType(usePrimitiveType);
        tkMapperConfig.setEnumAsSimpleType(enumAsSimpleType);

        // useSimpleTypeOption默认为true
        String useSimpleTypeOption = processingEnvironment.getOptions().get(AbstractMetaModelGenProcessor.USE_SIMPLE_TYPE_OPTION);
        if (useSimpleTypeOption != null && (useSimpleTypeOption.toLowerCase().equals("true") || useSimpleTypeOption.toLowerCase().equals("false"))) {
            tkMapperConfig.setUseSimpleType(Boolean.parseBoolean(useSimpleTypeOption));
        } else {
            tkMapperConfig.setUseSimpleType(true);
        }

    }

    public void logMessage(Diagnostic.Kind type, String message) {
        if (!logDebug && type.equals(Diagnostic.Kind.NOTE)) {
            return;
        }
        this.processingEnvironment.getMessager().printMessage(type, message);
    }

    public boolean isAlreadyResolved(String elementName) {
        return resolvedMetaEntities.containsKey(elementName);
    }

    public void addResolvedMetaEntity(String qualifiedName, MetaEntity metaEntity) {
        resolvedMetaEntities.put(qualifiedName, metaEntity);
    }

    public Collection<MetaEntity> getResolvedMetaEntities() {
        return resolvedMetaEntities.values();
    }

    public boolean isAlreadyGenerated(String qualifiedName) {
        return generatedModelClasses.contains(qualifiedName);
    }

    public void markGenerated(String qualifiedName) {
        generatedModelClasses.add(qualifiedName);
    }

    public Processor getMetaModelProducer() {
        return metaModelProducer;
    }

    public ProcessingEnvironment getProcessingEnvironment() {
        return processingEnvironment;
    }

    public TKMapperConfig getTkMapperConfig() {
        return tkMapperConfig;
    }
}
