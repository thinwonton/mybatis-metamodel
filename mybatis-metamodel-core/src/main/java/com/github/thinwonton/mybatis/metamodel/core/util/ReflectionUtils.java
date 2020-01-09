package com.github.thinwonton.mybatis.metamodel.core.util;

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
}
