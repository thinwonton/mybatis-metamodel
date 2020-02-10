package com.github.thinwonton.mybatis.metamodel.core.register;

import com.github.thinwonton.mybatis.metamodel.core.Refreshable;

public interface MetaModelContext extends Refreshable {

    Table getTable(Class<?> metaModelClass);

    /**
     * 获取不带schema的表名
     */
    String getSimpleTableName(Class<?> metaModelClass);

    /**
     * 获取带schema的表名
     */
    String getTableName(Class<?> metaModelClass);

    GlobalConfig getGlobalConfig();
}
