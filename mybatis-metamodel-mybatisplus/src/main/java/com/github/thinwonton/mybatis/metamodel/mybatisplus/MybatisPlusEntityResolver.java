package com.github.thinwonton.mybatis.metamodel.mybatisplus;

import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.Table;
import com.github.thinwonton.mybatis.metamodel.core.register.TableField;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Collection;

/**
 * MybatisPlusEntityResolver
 *
 * @author hugo
 * @date 2020/1/9
 */
public class MybatisPlusEntityResolver implements EntityResolver {
    @Override
    public Class<?> getMappedEntityClass(MappedStatement mappedStatement) {
        return null;
    }

    @Override
    public String resolveTableName(Class<?> entityClass) {
        return null;
    }

    @Override
    public Collection<TableField> resolveTableFields(Table table, Class<?> entityClass) {
        return null;
    }
}
