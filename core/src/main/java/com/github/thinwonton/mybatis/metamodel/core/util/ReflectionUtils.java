package com.github.thinwonton.mybatis.metamodel.core.util;

import java.lang.reflect.Field;

/**
 * ReflectionUtils
 *
 * @author hugo
 * @date 2020/1/5
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
        Field propertyField = c.getDeclaredField(propertyName); //获取属性study1中的属性a
        propertyField.setAccessible(true);//设置a属性的访问权限，保证private属性的访问
        return (T) propertyField.get(object);
    }

}
