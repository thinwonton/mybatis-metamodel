package com.github.thinwonton.mybatis.metamodel.core.util;

import com.github.thinwonton.mybatis.metamodel.core.exception.MetaModelRegisterException;

/**
 * RegisterUtils
 *
 * @author hugo
 * @date 2020/1/5
 */
public class RegisterUtils {

    /**
     * 根据 mappedStatementId 获取 mappedStatement 对应的 mapper 类
     *
     * @param mappedStatementId mappedStatement的唯一Id
     * @return
     */
    public static Class<?> getMapperClass(String mappedStatementId, ClassLoader classLoader) {
        String mapperClassStr = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
        Class<?> mapperClass = null;
        try {
            mapperClass = Class.forName(mapperClassStr, true, classLoader);
        } catch (ClassNotFoundException e) {
            // we'll ignore this until all class loaders fail to locate the class
        }
        if (mapperClass == null) {
            throw new MetaModelRegisterException("class loaders failed to locate the class " + mapperClassStr);
        }
        return mapperClass;
    }
}
