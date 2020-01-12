package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.SqlServerMapper;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;

public class TKMapperTestBase {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setup() {
        try {
            Reader reader = getConfigFileAsReader();
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
            //配置通用 Mapper
            configMapperHelper();
            //执行初始化 SQL
            runSql(getSqlFileAsReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 配置通用 Mapper
     */
    protected void configMapperHelper() {
        //创建一个MapperHelper
        MapperHelper mapperHelper = new MapperHelper();
        //设置配置
        mapperHelper.setConfig(getConfig());
        //配置完成后，执行下面的操作
        mapperHelper.processConfiguration(getSqlSessionFactory().getConfiguration());
    }

    /**
     * 获取 Mapper 配置
     *
     * @return
     */
    protected Config getConfig() {
        return new Config();
    }


    /**
     * 获取 mybatis 配置
     *
     * @return
     */
    protected Reader getConfigFileAsReader() throws IOException {
        return Resources.getResourceAsReader("mybatis-config.xml");
    }

    /**
     * 获取初始化 sql
     *
     * @return
     */
    protected Reader getSqlFileAsReader() throws IOException {
        return Resources.getResourceAsReader("db.sql");
    }

    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 获取Session
     *
     * @return
     */
    protected SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * 执行 Sql
     *
     * @param reader
     */
    protected void runSql(Reader reader) {
        if (reader == null) {
            return;
        }
        SqlSession sqlSession = getSqlSession();
        try {
            Connection conn = sqlSession.getConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.runScript(reader);
            try {
                reader.close();
            } catch (IOException e) {
                //ignore
            }
        } finally {
            sqlSession.close();
        }
    }

}
