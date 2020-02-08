package com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register;

import com.github.thinwonton.mybatis.metamodel.core.TKMapperConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.util.Utils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.util.SimpleTypeUtil;

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

    private void init(Configuration mybatisConfig, Config externalMapperConfig) {

        //mappedStatements
        for (Object object : new ArrayList<Object>(mybatisConfig.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                globalConfig.addMappedStatement(ms);
            }
        }

        TKMapperConfig internalTKMapperConfig = globalConfig.getTkMapperConfig();

        // 全局的catalog 和 schema
        internalTKMapperConfig.setCatalog(externalMapperConfig.getCatalog());
        internalTKMapperConfig.setSchema(externalMapperConfig.getSchema());

        // style
        internalTKMapperConfig.setStyle(Utils.transform(externalMapperConfig.getStyle()));

        TKMapperConfig internalMapperConfig = globalConfig.getTkMapperConfig();

        //通过查找是否有原语类型，判断是否支持原语
        boolean usePrimitiveType = SimpleTypeUtil.isSimpleType(boolean.class);
        internalMapperConfig.setUsePrimitiveType(usePrimitiveType);

        internalMapperConfig.setEnumAsSimpleType(externalMapperConfig.isEnumAsSimpleType());

    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }
}
