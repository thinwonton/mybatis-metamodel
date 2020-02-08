package com.github.thinwonton.mybatis.metamodel.core.util;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * 参考 org.apache.ibatis.type.SimpleTypeRegistry
 */
public class TypeUtils {
    public static final String[] JAVA8_DATE_TIME = {
            "java.time.Instant",
            "java.time.LocalDateTime",
            "java.time.LocalDate",
            "java.time.LocalTime",
            "java.time.OffsetDateTime",
            "java.time.OffsetTime",
            "java.time.ZonedDateTime",
            "java.time.Year",
            "java.time.Month",
            "java.time.YearMonth"
    };
    private static final Set<Class<?>> SIMPLE_TYPE_SET = new HashSet<>();

    private static final Set<Class<?>> PRIMITIVE_TYPE_SET = new HashSet<>();

    private static final Map<TypeKind, String> TYPEKIND_PRIMITIVES_MAPPING = new HashMap<TypeKind, String>();


    /**
     * 特别注意：由于基本类型有默认值，因此在实体类中不建议使用基本类型作为数据库字段类型
     */
    static {
        SIMPLE_TYPE_SET.add(byte[].class);
        SIMPLE_TYPE_SET.add(String.class);
        SIMPLE_TYPE_SET.add(Byte.class);
        SIMPLE_TYPE_SET.add(Short.class);
        SIMPLE_TYPE_SET.add(Character.class);
        SIMPLE_TYPE_SET.add(Integer.class);
        SIMPLE_TYPE_SET.add(Long.class);
        SIMPLE_TYPE_SET.add(Float.class);
        SIMPLE_TYPE_SET.add(Double.class);
        SIMPLE_TYPE_SET.add(Boolean.class);
        SIMPLE_TYPE_SET.add(Date.class);
        SIMPLE_TYPE_SET.add(Timestamp.class);
        SIMPLE_TYPE_SET.add(Class.class);
        SIMPLE_TYPE_SET.add(BigInteger.class);
        SIMPLE_TYPE_SET.add(BigDecimal.class);
        //反射方式设置 java8 中的日期类型
        for (String time : JAVA8_DATE_TIME) {
            registerSimpleTypeSilence(time);
        }
    }

    static {
        PRIMITIVE_TYPE_SET.add(boolean.class);
        PRIMITIVE_TYPE_SET.add(byte.class);
        PRIMITIVE_TYPE_SET.add(short.class);
        PRIMITIVE_TYPE_SET.add(int.class);
        PRIMITIVE_TYPE_SET.add(long.class);
        PRIMITIVE_TYPE_SET.add(char.class);
        PRIMITIVE_TYPE_SET.add(float.class);
        PRIMITIVE_TYPE_SET.add(double.class);
    }

    static {
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.CHAR, char.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.BYTE, byte.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.SHORT, short.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.INT, int.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.LONG, long.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.BOOLEAN, boolean.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.FLOAT, float.class.toString());
        TYPEKIND_PRIMITIVES_MAPPING.put(TypeKind.DOUBLE, double.class.toString());
    }

    /**
     * 注册新的类型，不存在时不抛出异常
     *
     * @param clazz
     */
    private static void registerSimpleTypeSilence(String clazz) {
        try {
            SIMPLE_TYPE_SET.add(Class.forName(clazz));
        } catch (ClassNotFoundException e) {
            //ignore
        }
    }

    /*
     * Tells us if the class passed in is a known common type
     *
     * @param clazz The class to check
     * @return True if the class is known
     */
    public static boolean isSimpleType(Class<?> clazz) {
        return SIMPLE_TYPE_SET.contains(clazz);
    }

    public static boolean isSimpleType(String className) {
        for (Class<?> aClass : SIMPLE_TYPE_SET) {
            if (aClass.getName().equals(className)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPrimitiveType(String className) {
        for (Class<?> aClass : PRIMITIVE_TYPE_SET) {
            if (aClass.getName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static String toTypeString(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return TYPEKIND_PRIMITIVES_MAPPING.get(type.getKind());
        }
        return type.toString();
    }

}
