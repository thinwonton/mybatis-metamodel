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

    private boolean logDebug;

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
    }

    public void logMessage(Diagnostic.Kind type, String message) {
        if (!logDebug && type.equals(Diagnostic.Kind.OTHER)) {
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

}
