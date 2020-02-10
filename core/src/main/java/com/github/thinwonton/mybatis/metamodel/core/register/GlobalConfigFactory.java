package com.github.thinwonton.mybatis.metamodel.core.register;

import com.github.thinwonton.mybatis.metamodel.core.Refreshable;

/**
 * 全局配置工厂
 */
public interface GlobalConfigFactory extends Refreshable {
    /**
     * 获取全局配置
     *
     * @return {@link  GlobalConfig}
     */
    GlobalConfig getGlobalConfig();
}
