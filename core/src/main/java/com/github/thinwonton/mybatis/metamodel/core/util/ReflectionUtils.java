package com.github.thinwonton.mybatis.metamodel.core.util;

import java.lang.reflect.Field;

/**
 * ReflectionUtils
 *
 * @author hugo
 */
public class ReflectionUtils {
    public static Class<?> loadClass(String className, ClassLoader classLoader) {
        try {
            return Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            // we'll ignore this until all class loaders fail to locate the class
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object object, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> c = object.getClass();
        Field propertyField = c.getDeclaredField(propertyName);
        propertyField.setAccessible(true);
        return (T) propertyField.get(object);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Class<?> clazz, String propertyName) throws NoSuchFieldException, IllegalAccessException {
        Field propertyField = clazz.getDeclaredField(propertyName);
        propertyField.setAccessible(true);
        return (T) propertyField.get(clazz);
    }

}
