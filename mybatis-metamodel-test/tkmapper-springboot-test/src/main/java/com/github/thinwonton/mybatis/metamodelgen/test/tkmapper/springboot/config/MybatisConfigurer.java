package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.config;

import com.github.thinwonton.mybatis.metamodel.tkmapper.spring.MetaModelContextFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.mapper.SpringBootBindUtil;


@MapperScan(
        basePackages = "com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.mapper",
        mapperHelperRef = "mapperHelper"
)
@Configuration
public class MybatisConfigurer {
    @Bean
    public MapperHelper mapperHelper(Environment environment) {
        Config config = SpringBootBindUtil.bind(environment, Config.class, Config.PREFIX);
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(config);
        return mapperHelper;
    }

    @Bean
    public MetaModelContextFactoryBean metaModelContext(SqlSessionFactory sqlSessionFactory, MapperHelper mapperHelper) {
        MetaModelContextFactoryBean metaModelContextFactoryBean = new MetaModelContextFactoryBean();
        metaModelContextFactoryBean.setMapperHelper(mapperHelper);
        metaModelContextFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return metaModelContextFactoryBean;
    }

}
