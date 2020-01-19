package com.github.thinwonton.mybatis.metamodel.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.core.util.Style;
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

        com.baomidou.mybatisplus.core.config.GlobalConfig mybatisPlusConfig = mybatisConfiguration.getGlobalConfig();

        // 全局的 catalog 和 schema
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = mybatisPlusConfig.getDbConfig();
        globalConfig.setSchema(dbConfig.getSchema());

        //style
        globalConfig.setStyle(Utils.getStyle(dbConfig));
    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }
}
