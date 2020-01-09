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

    public static final Map<TypeKind, String> PRIMITIVE_WRAPPERS = new HashMap<TypeKind, String>();
    public static final Map<TypeKind, String> PRIMITIVES = new HashMap<TypeKind, String>();
    public static final Set<String> EXCLUDING_SUPERCLASS_TYPES = new HashSet<>();
    public static final List<String> BASIC_WRAPPER_TYPES = new ArrayList<String>();

    //初始化原语类型
    static {
        PRIMITIVE_WRAPPERS.put(TypeKind.CHAR, "Character");

        PRIMITIVE_WRAPPERS.put(TypeKind.BYTE, "Byte");
        PRIMITIVE_WRAPPERS.put(TypeKind.SHORT, "Short");
        PRIMITIVE_WRAPPERS.put(TypeKind.INT, "Integer");
        PRIMITIVE_WRAPPERS.put(TypeKind.LONG, "Long");

        PRIMITIVE_WRAPPERS.put(TypeKind.BOOLEAN, "Boolean");

        PRIMITIVE_WRAPPERS.put(TypeKind.FLOAT, "Float");
        PRIMITIVE_WRAPPERS.put(TypeKind.DOUBLE, "Double");

        PRIMITIVES.put(TypeKind.CHAR, "char");
        PRIMITIVES.put(TypeKind.BYTE, "byte");
        PRIMITIVES.put(TypeKind.SHORT, "short");
        PRIMITIVES.put(TypeKind.INT, "int");
        PRIMITIVES.put(TypeKind.LONG, "long");
        PRIMITIVES.put(TypeKind.BOOLEAN, "boolean");
        PRIMITIVES.put(TypeKind.FLOAT, "float");
        PRIMITIVES.put(TypeKind.DOUBLE, "double");

    }

    //初始化查找父类时需要排除的类型
    static {
        EXCLUDING_SUPERCLASS_TYPES.add(Object.class.getCanonicalName());
    }

    //初始化基本类型
    static {
        BASIC_WRAPPER_TYPES.add(java.lang.String.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Boolean.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Byte.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Character.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Short.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Integer.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Long.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Float.class.getName());
        BASIC_WRAPPER_TYPES.add(java.lang.Double.class.getName());
        BASIC_WRAPPER_TYPES.add(java.math.BigInteger.class.getName());
        BASIC_WRAPPER_TYPES.add(java.math.BigDecimal.class.getName());
        BASIC_WRAPPER_TYPES.add(java.util.Date.class.getName());
        BASIC_WRAPPER_TYPES.add(java.util.Calendar.class.getName());
        BASIC_WRAPPER_TYPES.add(java.sql.Date.class.getName());
        BASIC_WRAPPER_TYPES.add(java.sql.Time.class.getName());
        BASIC_WRAPPER_TYPES.add(java.sql.Timestamp.class.getName());
        BASIC_WRAPPER_TYPES.add(java.sql.Blob.class.getName());
    }

    private GenerateUtils() {
    }

    /**
     * 获取注解中parameterKey的值
     * @param annotationMirror
     * @param parameterKey
     * @return
     */
    public static Object getAnnotationValue(AnnotationMirror annotationMirror, String parameterKey) {
        assert annotationMirror != null;
        assert parameterKey != null;

        Object returnValue = null;
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues()
                .entrySet()) {
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
     * 基本类型转换成包装类，返回包装类的类型字符串
     *
     * @param type
     * @return
     */
    public static String toWrapperTypeString(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return PRIMITIVE_WRAPPERS.get(type.getKind());
        }
        return type.toString();
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
