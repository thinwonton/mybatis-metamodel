package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.MybatisPlusGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Music;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper.MusicMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.Date;

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
