package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.springboot.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.spring.MetaModelContextFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Configuration
@MapperScan("com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.springboot.mapper")
public class MybatisConfig {

    @Bean
    public MetaModelContextFactoryBean metaModelContextFactory(SqlSessionFactory sqlSessionFactory,
                                                               MybatisPlusProperties mybatisPlusProperties) {
        MetaModelContextFactoryBean metaModelContextFactoryBean = new MetaModelContextFactoryBean(
                sqlSessionFactory,
                mybatisPlusProperties.getGlobalConfig());
        return metaModelContextFactoryBean;
    }
}
