package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.register;

import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.Table;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register.TKMapperEntityResolver;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register.TKMapperGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.TKMapperTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.Address;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.ComplexEntity_;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.State;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ComplexEntityTest extends TKMapperTestBase {
    private MetaModelContext metaModelContext;

    @Override
    protected void initInternal() {
        Configuration configuration = getSqlSessionFactory().getConfiguration();
        EntityResolver entityResolver = new TKMapperEntityResolver();
        metaModelContext = new MetaModelContext(
                new TKMapperGlobalConfigFactory(configuration, getMapperHelper()),
                entityResolver);
    }

    @Override
    protected Config getConfig() {
        Config config = new Config();
        config.setStyle(Style.camelhump); //驼峰
        return config;
    }

    @Test
    public void test() {
        //测试生成的字段
        List<String> expectedFields = Arrays.asList(
                "id", "userName", "address", "state"
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

    }
}
