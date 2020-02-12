package com.github.thinwonton.mybatis.metamodel.mybatisplus.spring;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContextImpl;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register.MybatisPlusGlobalConfigFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * MetaModelContextFactoryBean
 */
public class MetaModelContextFactoryBean implements FactoryBean<MetaModelContext>, ApplicationListener<ContextRefreshedEvent>, InitializingBean {

    private MybatisConfiguration mybatisConfiguration;

    private SqlSessionFactory sqlSessionFactory;
    private GlobalConfig globalConfig;

    private MetaModelContext metaModelContext;

    public MetaModelContextFactoryBean(MybatisConfiguration mybatisConfiguration) {
        this.mybatisConfiguration = mybatisConfiguration;
    }

    public MetaModelContextFactoryBean(SqlSessionFactory sqlSessionFactory, GlobalConfig globalConfig) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.globalConfig = globalConfig;
    }

    @Override
    public MetaModelContext getObject() {
        if (metaModelContext == null) {
            afterPropertiesSet();
        }
        return metaModelContext;
    }

    @Override
    public Class<?> getObjectType() {
        return MetaModelContext.class;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        metaModelContext.refresh();
    }

    @Override
    public void afterPropertiesSet() {
        GlobalConfigFactory globalConfigFactory;
        if (sqlSessionFactory != null && globalConfig != null) {
            globalConfigFactory = new MybatisPlusGlobalConfigFactory(sqlSessionFactory, globalConfig);
        } else {
            globalConfigFactory = new MybatisPlusGlobalConfigFactory(mybatisConfiguration);
        }
        metaModelContext = new MetaModelContextImpl(
                globalConfigFactory,
                new MybatisPlusEntityResolver()
        );
    }

}
