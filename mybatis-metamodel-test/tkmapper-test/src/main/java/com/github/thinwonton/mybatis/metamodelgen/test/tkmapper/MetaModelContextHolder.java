package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper;

import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;

import java.util.Optional;

/**
 * MetaModelContextHolder
 */
public class MetaModelContextHolder {
    private static MetaModelContext instance;

    public static MetaModelContext getInstance() {
        return Optional.of(instance).get();
    }

    public static void setup(MetaModelContext metaModelContext) {
        instance = metaModelContext;
    }
}
