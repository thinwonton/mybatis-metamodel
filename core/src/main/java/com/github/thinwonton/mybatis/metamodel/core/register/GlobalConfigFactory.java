package com.github.thinwonton.mybatis.metamodel.core.register;

/**
 * 全局配置工厂
 */
public interface GlobalConfigFactory {
    /**
     * 获取全局配置
     *
     * @return {@link  GlobalConfig}
     */
    GlobalConfig getGlobalConfig();
}
