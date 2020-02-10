package com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register;

import com.github.thinwonton.mybatis.metamodel.core.TKMapperConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.util.Utils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSessionFactory;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.util.SimpleTypeUtil;

import java.util.ArrayList;

/**
 * TKMapper 的 GlobalConfigFactory 实现类
 */
public class TKMapperGlobalConfigFactory implements GlobalConfigFactory {

    private GlobalConfig globalConfig = new GlobalConfig();

    private SqlSessionFactory sqlSessionFactory;
    private MapperHelper mapperHelper;

    public TKMapperGlobalConfigFactory(SqlSessionFactory sqlSessionFactory, MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
        this.sqlSessionFactory = sqlSessionFactory;
        init();
    }

    private void init() {

        //获取tkmapper config
        Config mapperConfig = mapperHelper.getConfig();

        //mappedStatements
        for (Object object : new ArrayList<Object>(sqlSessionFactory.getConfiguration().getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                globalConfig.addMappedStatement(ms);
            }
        }

        TKMapperConfig internalTKMapperConfig = globalConfig.getTkMapperConfig();

        // 全局的catalog 和 schema
        internalTKMapperConfig.setCatalog(mapperConfig.getCatalog());
        internalTKMapperConfig.setSchema(mapperConfig.getSchema());

        // style
        internalTKMapperConfig.setStyle(Utils.transform(mapperConfig.getStyle()));

        TKMapperConfig internalMapperConfig = globalConfig.getTkMapperConfig();

        //通过查找是否有原语类型，判断是否支持原语
        boolean usePrimitiveType = SimpleTypeUtil.isSimpleType(boolean.class);
        internalMapperConfig.setUsePrimitiveType(usePrimitiveType);

        internalMapperConfig.setEnumAsSimpleType(mapperConfig.isEnumAsSimpleType());
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
