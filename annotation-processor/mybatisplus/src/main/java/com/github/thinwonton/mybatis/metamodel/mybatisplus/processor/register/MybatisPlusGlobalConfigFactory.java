package com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.github.thinwonton.mybatis.metamodel.core.MybatisPlusConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.util.Utils;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.ArrayList;

/**
 * mybatis-plus 的 GlobalConfigFactory 实现类
 */
public class MybatisPlusGlobalConfigFactory implements GlobalConfigFactory {

    private GlobalConfig globalConfig = new GlobalConfig();
    private MybatisConfiguration mybatisConfiguration;

    public MybatisPlusGlobalConfigFactory(MybatisConfiguration mybatisConfiguration) {
        this.mybatisConfiguration = mybatisConfiguration;
        init();
    }

    private void init() {
        //mappedStatements
        for (Object object : new ArrayList<Object>(mybatisConfiguration.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                globalConfig.addMappedStatement(ms);
            }
        }

        //mybatis plus 的配置
        MybatisPlusConfig internalMybatisPlusConfig = globalConfig.getMybatisPlusConfig();
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = mybatisConfiguration.getGlobalConfig().getDbConfig();

        internalMybatisPlusConfig.setSchema(dbConfig.getSchema());

        //style
        internalMybatisPlusConfig.setStyle(Utils.getStyle(dbConfig));

    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }

    @Override
    public void refresh() {
        globalConfig = new GlobalConfig();
        init();
    }
}
