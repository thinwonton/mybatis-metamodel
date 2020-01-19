package com.github.thinwonton.mybatis.metamodel.tkmapper;

import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.core.util.Style;
import com.github.thinwonton.mybatis.metamodel.tkmapper.util.Utils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.util.ArrayList;

/**
 * TKMapper 的 GlobalConfigFactory 实现类
 */
public class TKMapperGlobalConfigFactory implements GlobalConfigFactory {

    private GlobalConfig globalConfig = new GlobalConfig();


    public TKMapperGlobalConfigFactory(Configuration mybatisConfig, Config tkMapperConfig) {
        init(mybatisConfig, tkMapperConfig);
    }

    public TKMapperGlobalConfigFactory(Configuration mybatisConfig, MapperHelper mapperHelper) {
        this(mybatisConfig, mapperHelper.getConfig());
    }

    private void init(Configuration mybatisConfig, Config tkMapperConfig) {

        //mappedStatements
        for (Object object : new ArrayList<Object>(mybatisConfig.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                globalConfig.addMappedStatement(ms);
            }
        }

        // 全局的catalog 和 schema
        globalConfig.setCatalog(tkMapperConfig.getCatalog());
        globalConfig.setSchema(tkMapperConfig.getSchema());

        // style
        globalConfig.setStyle(Utils.transform(tkMapperConfig.getStyle()));

    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }
}
