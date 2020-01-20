package com.github.thinwonton.mybatis.metamodel.core.util;

import com.github.thinwonton.mybatis.metamodel.core.gen.MetaModelGenContext;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.*;

/**
 *
 */
public final class GenerateUtils {

    public static final Set<String> EXCLUDING_SUPERCLASS_TYPES = new HashSet<>();

    //初始化查找父类时需要排除的类型
    static {
        EXCLUDING_SUPERCLASS_TYPES.add(Object.class.getCanonicalName());
    }

    private GenerateUtils() {
    }

    /**
     * 获取注解中parameterKey的值
     *
     * @param annotationMirror
     * @param parameterKey
     * @return
     */
    public static Object getAnnotationValue(AnnotationMirror annotationMirror, String parameterKey) {
        assert annotationMirror != null;
        assert parameterKey != null;

        Object returnValue = null;
        Set<? extends Map.Entry<? extends ExecutableElement, ? extends AnnotationValue>> entries = annotationMirror.getElementValues().entrySet();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : entries) {
            if (parameterKey.equals(entry.getKey().getSimpleName().toString())) {
                returnValue = entry.getValue().getValue();
                break;
            }
        }
        return returnValue;
    }

    public static boolean isAnnotationMirrorOfType(AnnotationMirror annotationMirror, String annotationName) {
        String annotationClassName = annotationMirror.getAnnotationType().toString();
        return annotationClassName.equals(annotationName);
    }

    /**
     * 获取注解
     *
     * @param element
     * @param annotationName
     * @return
     */
    public static AnnotationMirror getAnnotationMirror(Element element, String annotationName) {
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotationMirrors) {
            if (isAnnotationMirrorOfType(mirror, annotationName)) {
                return mirror;
            }
        }
        return null;
    }


    /**
     * 是否包含指定的注解
     *
     * @param element              待判断的元素
     * @param annotationClassNames 注解的全类名
     * @return
     */
    public static boolean containsAnnotation(Element element, Collection<String> annotationClassNames) {
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotationMirrors) {
            if (annotationClassNames.contains(mirror.getAnnotationType().toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含指定的注解
     *
     * @param element              待判断的元素
     * @param annotationClassNames 注解的全类名
     * @return
     */
    public static boolean containsAnnotation(Element element, String... annotationClassNames) {
        if (annotationClassNames.length <= 0) {
            return false;
        }
        return containsAnnotation(element, Arrays.asList(annotationClassNames));
    }

    /**
     * 获取父类
     *
     * @param element
     * @return
     */
    public static TypeElement getSuperclassTypeElement(TypeElement element) {
        return getSuperclassTypeElement(element, EXCLUDING_SUPERCLASS_TYPES);
    }

    /**
     * 获取父类
     *
     * @param element
     * @return
     */
    public static TypeElement getSuperclassTypeElement(TypeElement element, Collection<String> excludingTypes) {
        final TypeMirror superClass = element.getSuperclass();
        if (superClass.getKind() == TypeKind.DECLARED) {
            final TypeElement superClassElement = (TypeElement) ((DeclaredType) superClass).asElement();
            if (excludingTypes.contains(superClassElement.getQualifiedName().toString())) {
                return null;
            }
            return superClassElement;
        } else {
            return null;
        }
    }

    /**
     * 获取Element的包名
     *
     * @param metaModelGenContext
     * @param element
     * @return
     */
    public static String getPackageName(MetaModelGenContext metaModelGenContext, TypeElement element) {
        Elements elementUtils = metaModelGenContext.getProcessingEnvironment().getElementUtils();
        PackageElement packageOf = elementUtils.getPackageOf(element);
        return packageOf.getQualifiedName().toString();
    }
}
