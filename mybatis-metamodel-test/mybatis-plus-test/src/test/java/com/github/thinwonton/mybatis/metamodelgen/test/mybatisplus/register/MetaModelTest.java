package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.register;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Music_;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * 测试生成的 MetaModel
 */
public class MetaModelTest {

    private MetaModelContext metaModelContext;

    @Before
    public void setup() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(reader);
        Configuration configuration = factory.getConfiguration();
        EntityResolver entityResolver = new MybatisPlusEntityResolver();
        metaModelContext = new MetaModelContext(configuration, entityResolver);
    }

    /**
     * 测试表名
     */
    @Test
    public void testTableName() {
        String simpleTableName = metaModelContext.getTableName(Music_.class);
        Assert.assertEquals("music", simpleTableName);
        String complicatedTableName = metaModelContext.getComplicatedTableName(Music_.class);
        Assert.assertEquals("music", complicatedTableName);
    }

    /**
     * 测试tableField
     */
    @Test
    public void testTableField() {
        //id
        Assert.assertEquals("id", Music_.id.getColumn());
        Assert.assertTrue(Music_.id.isId());
        Assert.assertEquals(Long.class, Music_.id.getJavaType());
        Assert.assertEquals("id", Music_.id.getProperty());

        //name
        Assert.assertEquals("name", Music_.name.getColumn());
        Assert.assertFalse(Music_.name.isId());
        Assert.assertEquals(String.class, Music_.name.getJavaType());
        Assert.assertEquals("name", Music_.name.getProperty());

        //authorName
        Assert.assertEquals("authorName", Music_.authorName.getColumn());
        Assert.assertFalse(Music_.authorName.isId());
        Assert.assertEquals(String.class, Music_.authorName.getJavaType());
        Assert.assertEquals("authorName", Music_.authorName.getProperty());
    }
}
