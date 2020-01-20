package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;

import java.io.IOException;
import java.io.Reader;

public class MybatisPlusTestBase {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setup() throws IOException {
        //配置设置
        Reader reader = getConfigFileAsReader();
        MybatisXMLConfigBuilder xmlConfigBuilder = new MybatisXMLConfigBuilder(reader);
        MybatisConfiguration mybatisConfiguration = xmlConfigBuilder.getConfiguration();
        configMybatisConfiguration(mybatisConfiguration);
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(xmlConfigBuilder.parse());
    }

    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 获取 mybatis 配置
     *
     * @return
     */
    protected Reader getConfigFileAsReader() throws IOException {
        return Resources.getResourceAsReader("mybatis-config.xml");
    }

    protected void configMybatisConfiguration(MybatisConfiguration mybatisConfiguration) {

    }
}
