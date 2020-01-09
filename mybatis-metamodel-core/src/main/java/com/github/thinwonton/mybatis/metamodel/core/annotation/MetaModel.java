package com.github.thinwonton.mybatis.metamodel.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识该类是 meta model
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaModel {
    /**
     * MetaModel关联的原实体类
     * @return
     */
    Class<?> source();
}