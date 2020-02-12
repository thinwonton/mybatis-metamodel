package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MybatisPlusTestBase {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setup() throws IOException {
        try {
            //配置设置
            Reader reader = getConfigFileAsReader();
            MybatisXMLConfigBuilder xmlConfigBuilder = new MybatisXMLConfigBuilder(reader);
            MybatisConfiguration mybatisConfiguration = xmlConfigBuilder.getConfiguration();
            configMybatisConfiguration(mybatisConfiguration);
            sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(xmlConfigBuilder.parse());

            //执行初始化 SQL
            List<Reader> sqlFiles = getSqlFilesAsReader();
            for (Reader sqlFile : sqlFiles) {
                runSql(sqlFile);
            }

            initInternal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected void initInternal() {
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

    /**
     * 获取初始化 sql 脚本
     *
     * @return
     */
    protected List<Reader> getSqlFilesAsReader() throws IOException {
        Reader schemaReader = Resources.getResourceAsReader("schema.sql");
        Reader dataReader = Resources.getResourceAsReader("data.sql");
        List<Reader> list = new ArrayList<>();
        list.add(schemaReader);
        list.add(dataReader);
        return list;
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
