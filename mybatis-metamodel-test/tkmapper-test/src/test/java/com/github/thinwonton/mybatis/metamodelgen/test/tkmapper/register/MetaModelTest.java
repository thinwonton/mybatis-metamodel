package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.register;

import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.tkmapper.TKMapperEntityResolver;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.TKMapperTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.Music_;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试生成的 MetaModel
 */
public class MetaModelTest extends TKMapperTestBase {

    private MetaModelContext metaModelContext;

    @Override
    public void setup() {
        super.setup();

        Configuration configuration = getSqlSessionFactory().getConfiguration();
        EntityResolver entityResolver = new TKMapperEntityResolver();
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
        Assert.assertEquals(JdbcType.UNDEFINED, Music_.id.getJdbcType());

        //name
        Assert.assertEquals("name", Music_.name.getColumn());
        Assert.assertFalse(Music_.name.isId());
        Assert.assertEquals(String.class, Music_.name.getJavaType());
        Assert.assertEquals("name", Music_.name.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, Music_.name.getJdbcType());

        //authorName
        Assert.assertEquals("authorName", Music_.authorName.getColumn());
        Assert.assertFalse(Music_.authorName.isId());
        Assert.assertEquals(String.class, Music_.authorName.getJavaType());
        Assert.assertEquals("authorName", Music_.authorName.getProperty());
        Assert.assertEquals(JdbcType.VARCHAR, Music_.authorName.getJdbcType());
    }
}
