package com.github.thinwonton.mybatis.metamodel.core.register;

import org.apache.ibatis.mapping.MappedStatement;

import java.util.Collection;

public interface EntityResolver {
    Class<?> getMappedEntityClass(MappedStatement mappedStatement);

    String resolveTableName(Class<?> entityClass);

    Collection<TableField> resolveTableFields(Table table, Class<?> entityClass);
}
