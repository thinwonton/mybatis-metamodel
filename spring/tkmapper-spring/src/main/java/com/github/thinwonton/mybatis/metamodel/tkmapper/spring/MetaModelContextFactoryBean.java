package com.github.thinwonton.mybatis.metamodel.tkmapper.spring;

import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContextImpl;
import com.github.thinwonton.mybatis.metamodel.core.register.Table;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register.TKMapperEntityResolver;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register.TKMapperGlobalConfigFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import static org.springframework.util.Assert.notNull;

/**
 * MetaModelContextFactoryBean
 */
public class MetaModelContextFactoryBean implements SmartFactoryBean<MetaModelContext>, ApplicationListener<ContextRefreshedEvent>, InitializingBean {

    private SqlSessionFactory sqlSessionFactory;

    private MapperHelper mapperHelper;

    private MetaModelContext metaModelContext;

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public MetaModelContextFactoryBean() {
    }

    @Override
    public MetaModelContext getObject() {
        metaModelContext = new MetaModelContextImpl(
                new TKMapperGlobalConfigFactory(sqlSessionFactory, mapperHelper),
                new TKMapperEntityResolver()
        );
        return metaModelContext;
    }

    @Override
    public boolean isEagerInit() {
        return true;
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
    public void afterPropertiesSet() throws Exception {
        notNull(sqlSessionFactory, "Property 'sqlSessionFactory' is required");
        notNull(mapperHelper, "Property 'mapperHelper' is required");
    }

}
