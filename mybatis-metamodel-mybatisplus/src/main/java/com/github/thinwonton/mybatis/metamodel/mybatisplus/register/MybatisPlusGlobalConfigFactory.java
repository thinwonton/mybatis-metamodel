package com.github.thinwonton.mybatis.metamodel.mybatisplus.register;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.github.thinwonton.mybatis.metamodel.core.MybatisPlusConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.util.Utils;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.ArrayList;

/**
 * mybatis-plus 的 GlobalConfigFactory 实现类
 */
public class MybatisPlusGlobalConfigFactory implements GlobalConfigFactory {

    private GlobalConfig globalConfig = new GlobalConfig();

    public MybatisPlusGlobalConfigFactory(MybatisConfiguration mybatisConfiguration) {

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
}
