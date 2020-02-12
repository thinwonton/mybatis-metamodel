package com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.github.thinwonton.mybatis.metamodel.core.MybatisPlusConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.util.Utils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * mybatis-plus 的 GlobalConfigFactory 实现类
 */
public class MybatisPlusGlobalConfigFactory implements GlobalConfigFactory {

    private GlobalConfig globalConfig = new GlobalConfig();

    private MybatisConfiguration mybatisConfiguration;

    private SqlSessionFactory sqlSessionFactory;
    private com.baomidou.mybatisplus.core.config.GlobalConfig mybatisPlusGlobalConfig;

    public MybatisPlusGlobalConfigFactory(MybatisConfiguration mybatisConfiguration) {
        this.mybatisConfiguration = Optional.of(mybatisConfiguration).get();
        init();
    }

    public MybatisPlusGlobalConfigFactory(SqlSessionFactory sqlSessionFactory,
                                          com.baomidou.mybatisplus.core.config.GlobalConfig mybatisPlusGlobalConfig) {
        this.sqlSessionFactory = Optional.of(sqlSessionFactory).get();
        this.mybatisPlusGlobalConfig = Optional.of(mybatisPlusGlobalConfig).get();
        init();
    }

    private void init() {
        //mappedStatements
        Configuration mybatisConfiguration = getMybatisConfiguration();
        if (mybatisConfiguration != null) {
            for (Object object : new ArrayList<Object>(getMybatisConfiguration().getMappedStatements())) {
                if (object instanceof MappedStatement) {
                    MappedStatement ms = (MappedStatement) object;
                    globalConfig.addMappedStatement(ms);
                }
            }
        }

        //mybatis plus 的配置
        MybatisPlusConfig internalMybatisPlusConfig = globalConfig.getMybatisPlusConfig();

        com.baomidou.mybatisplus.core.config.GlobalConfig mybatisPlusGlobalConfig = getMybatisPlusGlobalConfig();
        if (mybatisPlusGlobalConfig != null) {
            com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = mybatisPlusGlobalConfig.getDbConfig();

            internalMybatisPlusConfig.setSchema(dbConfig.getSchema());

            //style
            internalMybatisPlusConfig.setStyle(Utils.getStyle(dbConfig));
        }

    }

    private Configuration getMybatisConfiguration() {
        if (Objects.nonNull(sqlSessionFactory) && Objects.nonNull(mybatisPlusGlobalConfig)) {
            return sqlSessionFactory.getConfiguration();
        }

        return this.mybatisConfiguration;
    }

    private com.baomidou.mybatisplus.core.config.GlobalConfig getMybatisPlusGlobalConfig() {
        if (Objects.nonNull(sqlSessionFactory) && Objects.nonNull(mybatisPlusGlobalConfig)) {
            return mybatisPlusGlobalConfig;
        }

        return this.mybatisConfiguration.getGlobalConfig();
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
