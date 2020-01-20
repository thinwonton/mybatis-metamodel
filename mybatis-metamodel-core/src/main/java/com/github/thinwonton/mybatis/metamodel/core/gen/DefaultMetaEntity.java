package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.util.AccessType;
import com.github.thinwonton.mybatis.metamodel.core.util.GenerateUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultMetaEntity implements MetaEntity {
    private final MetaModelGenContext metaModelGenContext;
    // ORM实体对应的element
    private final TypeElement element;
    // meta model的成员
    private final Map<String, MetaAttributeDescriptor> members;

    private final ImportContext importContext;

    private final MetaAttributeConverter metaAttributeConverter;

    public DefaultMetaEntity(MetaModelGenContext metaModelGenContext,
                             TypeElement element,
                             ImportContext importContext,
                             MetaAttributeConverter metaAttributeConverter) {
        this.element = element;
        this.metaModelGenContext = metaModelGenContext;
        this.importContext = importContext;
        this.metaAttributeConverter = metaAttributeConverter;
        this.members = new HashMap<>();

        init();
    }

    protected final void init() {
        this.metaModelGenContext.logMessage(Diagnostic.Kind.NOTE, "Initializing type " + getQualifiedName());

        List<? extends Element> fieldsOfClass = ElementFilter.fieldsIn(element.getEnclosedElements());
        addPersistentMembers(fieldsOfClass, AccessType.FIELD);

        List<? extends Element> methodsOfClass = ElementFilter.methodsIn(element.getEnclosedElements());
        List<Element> gettersAndSettersOfClass = new ArrayList<>();

        // 注解有可能在 getter setter 方法上，所以获取相应的getter setter
        for (Element rawMethodOfClass : methodsOfClass) {
            if (isGetterOrSetter(rawMethodOfClass)) {
                gettersAndSettersOfClass.add(rawMethodOfClass);
            }
        }
        addPersistentMembers(gettersAndSettersOfClass, AccessType.PROPERTY);
    }

    private void addPersistentMembers(List<? extends Element> membersOfClass, AccessType accessType) {
        //暂时不支持方法上注解
        if (AccessType.PROPERTY.equals(accessType)) {
            return;
        }

        for (Element memberOfClass : membersOfClass) {
            if (!metaAttributeConverter.filter(memberOfClass)) {
                metaModelGenContext.logMessage(Diagnostic.Kind.NOTE, "Starting convert " + this.element.toString() + " member: " + memberOfClass.getSimpleName());
                //类型转换
                MetaAttributeGenerationVisitor visitor = new MetaAttributeGenerationVisitor(metaModelGenContext, this, metaAttributeConverter);
                MetaAttributeDescriptor result = memberOfClass.asType().accept(visitor, memberOfClass);
                if (result != null) {
                    members.put(result.getPropertyName(), result);
                }
            }
        }
    }

    /**
     * 判断是否getter setter方法
     *
     * @param methodOfClass
     */
    private boolean isGetterOrSetter(Element methodOfClass) {
        ExecutableType methodType = (ExecutableType) methodOfClass.asType();
        String methodSimpleName = methodOfClass.getSimpleName().toString();
        List<? extends TypeMirror> methodParameterTypes = methodType.getParameterTypes();
        TypeMirror returnType = methodType.getReturnType();

        if (methodSimpleName.startsWith("set")
                && methodParameterTypes.size() == 1
                && "void".equalsIgnoreCase(returnType.toString())) {
            return true;
        } else if ((methodSimpleName.startsWith("get") || methodSimpleName.startsWith("is"))
                && methodParameterTypes.isEmpty()
                && !"void".equalsIgnoreCase(returnType.toString())) {
            return true;
        } else {
            return false;
        }
    }

    public final String getPackageName() {
        return GenerateUtils.getPackageName(metaModelGenContext, element);
    }

    public final String getQualifiedName() {
        return element.getQualifiedName().toString();
    }

    @Override
    public final String getSimpleName() {
        return element.getSimpleName().toString();
    }

    @Override
    public List<MetaAttributeDescriptor> getMembers() {
        return new ArrayList<>(members.values());
    }

    @Override
    public MetaModelGenContext getMetaModelGenContext() {
        return this.metaModelGenContext;
    }

    @Override
    public TypeElement getElement() {
        return element;
    }

    @Override
    public String importType(String qualifiedName) {
        return importContext.importType(qualifiedName);
    }

    @Override
    public String generateImports() {
        return importContext.generateImports();
    }
}
