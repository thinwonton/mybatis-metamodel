package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.register;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.Table;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.register.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.register.MybatisPlusGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.MybatisPlusTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Address;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.ComplexEntity_;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.State;
import org.apache.ibatis.type.JdbcType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ComplexEntityTest extends MybatisPlusTestBase {
    private MetaModelContext metaModelContext;

    @Override
    protected void configMybatisConfiguration(MybatisConfiguration mybatisConfiguration) {
        com.baomidou.mybatisplus.core.config.GlobalConfig globalConfig = mybatisConfiguration.getGlobalConfig();
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = new com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig();
        globalConfig.setDbConfig(dbConfig);
        dbConfig.setTableUnderline(true);
    }

    @Override
    public void setup() throws IOException {
        super.setup();
        MybatisConfiguration configuration = (MybatisConfiguration) getSqlSessionFactory().getConfiguration();
        EntityResolver entityResolver = new MybatisPlusEntityResolver();
        metaModelContext = new MetaModelContext(
                new MybatisPlusGlobalConfigFactory(configuration),
                entityResolver);
    }


    @Test
    public void test() {
        //测试生成的字段
        List<String> expectedFields = Arrays.asList(
                "id", "userName", "age", "address", "state", "list"
        );

        Field[] declaredFields = ComplexEntity_.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Assert.assertTrue(expectedFields.contains(declaredField.getName()));
        }
        Assert.assertEquals(expectedFields.size(), declaredFields.length);

        //测试表名
        String expectedSimpleTableName = "complex_entity";
        String expectedTableName = Table.makeTableName(null, expectedSimpleTableName);
        String simpleTableName = metaModelContext.getSimpleTableName(ComplexEntity_.class);
        Assert.assertEquals(expectedSimpleTableName, simpleTableName);
        String complicatedTableName = metaModelContext.getTableName(ComplexEntity_.class);
        Assert.assertEquals(expectedTableName, complicatedTableName);

        //测试字段
        //id
        Assert.assertEquals("id", ComplexEntity_.id.getColumn());
        Assert.assertTrue(ComplexEntity_.id.isId());
        Assert.assertEquals(Long.class, ComplexEntity_.id.getJavaType());
        Assert.assertEquals("id", ComplexEntity_.id.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, ComplexEntity_.id.getJdbcType());

        //name
        Assert.assertEquals("user_name", ComplexEntity_.userName.getColumn());
        Assert.assertFalse(ComplexEntity_.userName.isId());
        Assert.assertEquals(String.class, ComplexEntity_.userName.getJavaType());
        Assert.assertEquals("userName", ComplexEntity_.userName.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, ComplexEntity_.userName.getJdbcType());

        //age
        Assert.assertEquals("age", ComplexEntity_.age.getColumn());
        Assert.assertFalse(ComplexEntity_.age.isId());
        Assert.assertEquals(Integer.class, ComplexEntity_.age.getJavaType());
        Assert.assertEquals("age", ComplexEntity_.age.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, ComplexEntity_.age.getJdbcType());

        //address
        Assert.assertEquals("address", ComplexEntity_.address.getColumn());
        Assert.assertFalse(ComplexEntity_.address.isId());
        Assert.assertEquals(Address.class, ComplexEntity_.address.getJavaType());
        Assert.assertEquals("address", ComplexEntity_.address.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, ComplexEntity_.address.getJdbcType());

        //state
        Assert.assertEquals("state", ComplexEntity_.state.getColumn());
        Assert.assertFalse(ComplexEntity_.state.isId());
        Assert.assertEquals(State.class, ComplexEntity_.state.getJavaType());
        Assert.assertEquals("state", ComplexEntity_.state.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, ComplexEntity_.state.getJdbcType());

        //list
        Assert.assertEquals("list", ComplexEntity_.list.getColumn());
        Assert.assertFalse(ComplexEntity_.list.isId());
        Assert.assertEquals(List.class, ComplexEntity_.list.getJavaType());
        Assert.assertEquals("list", ComplexEntity_.list.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, ComplexEntity_.list.getJdbcType());

    }
}
