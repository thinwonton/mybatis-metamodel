package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.annotation;

import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.tkmapper.spring.MetaModelContextFactoryBean;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity.State;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity.UserInfo_;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.mapper.UserInfoMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.List;

public class TKMapperSpringAnnotationTest {
    @Test
    public void testUseMybatisMapperScanner() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyBatisConfiguration.class);
        UserInfoMapper userInfoMapper = applicationContext.getBean(UserInfoMapper.class);

        UserInfo userInfo = new UserInfo();
        userInfo.setState(State.ENABLE);
        userInfo.setPassword("123456");
        userInfo.setUsertype("普通用户");
        userInfo.setRealname("云吞");
        userInfo.setAddress("中国");

        //插入10条记录
        for (long i = 1; i <= 10; i++) {
            userInfo.setId(i);
            userInfo.setUsername("hugo_" + i);
            Assert.assertEquals(1, userInfoMapper.insert(userInfo));
        }


        // 根据 example 条件查询
        String queryUserName = "hugo_1";
        Example userInfoQueryExample = new Example(UserInfo.class);
        Example.Criteria criteria = userInfoQueryExample.createCriteria();
        criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);
        List<UserInfo> userInfosByQuery = userInfoMapper.selectByExample(userInfoQueryExample);
        Assert.assertNotNull(userInfosByQuery);
        Assert.assertEquals(1, userInfosByQuery.size());
        UserInfo userInfoByQuery = userInfosByQuery.get(0);
        Assert.assertEquals(queryUserName, userInfoByQuery.getUsername());

        MetaModelContext metaModelContext = applicationContext.getBean(MetaModelContext.class);
        String tableName = metaModelContext.getTableName(UserInfo_.class);
        Assert.assertNotNull(tableName);
        Assert.assertEquals("user_info", tableName);
    }


    @Configuration
    @MapperScan(value = "com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.mapper")
    public static class MyBatisConfiguration {
        @Bean
        public DataSource dataSource() {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setUrl("jdbc:h2:mem:test;MODE=mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
            dataSource.setUser("sa");
            return dataSource;
        }

        @Bean
        public DataSourceInitializer dataSourceInitializer() throws FileNotFoundException {
            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource("annotation/db.sql"));
            dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
            dataSourceInitializer.setEnabled(true);
            dataSourceInitializer.setDataSource(dataSource());
            return dataSourceInitializer;
        }

        @Bean
        public MapperHelper mapperHelper() {
            Config config = new Config();
            config.setStyle(Style.camelhump); //驼峰
            MapperHelper mapperHelper = new MapperHelper();
            mapperHelper.setConfig(config);
            return mapperHelper;
        }

        @Bean
        public SqlSessionFactoryBean sqlSessionFactory() {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource());
            // 使用tkmapper的configuration
            tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
            configuration.setMapperHelper(mapperHelper());
            sessionFactoryBean.setConfiguration(configuration);
            return sessionFactoryBean;
        }

        @Bean
        public MetaModelContextFactoryBean metaModelContext(SqlSessionFactory sqlSessionFactory) {
            MetaModelContextFactoryBean metaModelContextFactoryBean = new MetaModelContextFactoryBean();
            metaModelContextFactoryBean.setMapperHelper(mapperHelper());
            metaModelContextFactoryBean.setSqlSessionFactory(sqlSessionFactory);
            return metaModelContextFactoryBean;
        }

    }
}
