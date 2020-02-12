package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.register;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.github.thinwonton.mybatis.metamodel.core.MybatisPlusConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.*;
import com.github.thinwonton.mybatis.metamodel.core.util.StringUtils;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register.MybatisPlusGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.MybatisPlusTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Music_;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.SpecSchemaSport;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.SpecSchemaSport_;
import org.apache.ibatis.type.JdbcType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * 测试生成的 MetaModel
 */
public class MybatisPlusMetaModelTest extends MybatisPlusTestBase {

    private MetaModelContext metaModelContext;

    private String GLOBAL_SCHEMA_NAME = "global_schema";

    @Override
    protected void configMybatisConfiguration(MybatisConfiguration mybatisConfiguration) {
        com.baomidou.mybatisplus.core.config.GlobalConfig globalConfig = mybatisConfiguration.getGlobalConfig();
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig = new com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig();
        globalConfig.setDbConfig(dbConfig);
        dbConfig.setSchema(GLOBAL_SCHEMA_NAME);
        dbConfig.setTableUnderline(true);
    }

    @Override
    public void setup() throws IOException {
        super.setup();
        MybatisConfiguration configuration = (MybatisConfiguration) getSqlSessionFactory().getConfiguration();
        EntityResolver entityResolver = new MybatisPlusEntityResolver();
        metaModelContext = new MetaModelContextImpl(
                new MybatisPlusGlobalConfigFactory(configuration),
                entityResolver);
    }

    /**
     * 测试表名
     */
    @Test
    public void testTableName() {
        String expectedSimpleTableName = "music";
        String expectedTableName = Table.makeTableName(GLOBAL_SCHEMA_NAME, expectedSimpleTableName);
        String simpleTableName = metaModelContext.getSimpleTableName(Music_.class);
        Assert.assertEquals(expectedSimpleTableName, simpleTableName);
        String complicatedTableName = metaModelContext.getTableName(Music_.class);
        Assert.assertEquals(expectedTableName, complicatedTableName);

        String expectedSimpleTableName2 = "spec_schema_sport";
        String expectedTableName2 = Table.makeTableName(SpecSchemaSport.SCHEMA, expectedSimpleTableName2);
        String simpleTableName2 = metaModelContext.getSimpleTableName(SpecSchemaSport_.class);
        Assert.assertEquals(expectedSimpleTableName2, simpleTableName2);
        String complicatedTableName2 = metaModelContext.getTableName(SpecSchemaSport_.class);
        Assert.assertEquals(expectedTableName2, complicatedTableName2);
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

        //length
        Assert.assertEquals("length", Music_.length.getColumn());
        Assert.assertFalse(Music_.length.isId());
        Assert.assertEquals(int.class, Music_.length.getJavaType());
        Assert.assertEquals("length", Music_.length.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, Music_.length.getJdbcType());

        //createDate
        Assert.assertEquals("create_date", Music_.createDate.getColumn());
        Assert.assertFalse(Music_.createDate.isId());
        Assert.assertEquals(Date.class, Music_.createDate.getJavaType());
        Assert.assertEquals("createDate", Music_.createDate.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, Music_.createDate.getJdbcType());

        //updateDate
        Assert.assertEquals("update_date", Music_.updateDate.getColumn());
        Assert.assertFalse(Music_.updateDate.isId());
        Assert.assertEquals(Date.class, Music_.updateDate.getJavaType());
        Assert.assertEquals("updateDate", Music_.updateDate.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, Music_.updateDate.getJdbcType());

    }

    @Test
    public void testCatalogAndSchema() {
        Assert.assertNotNull(metaModelContext);
        GlobalConfig globalConfig = metaModelContext.getGlobalConfig();
        Assert.assertNotNull(globalConfig);

        MybatisPlusConfig mybatisPlusConfig = globalConfig.getMybatisPlusConfig();
        Assert.assertNotNull(mybatisPlusConfig);

        Assert.assertEquals(GLOBAL_SCHEMA_NAME, mybatisPlusConfig.getSchema());

        Table musicTable = metaModelContext.getTable(Music_.class);
        Assert.assertNotNull(musicTable);
        Assert.assertNotNull(musicTable.getCatalogSchemaInfo());
        Assert.assertTrue(StringUtils.isEmpty(musicTable.getCatalogSchemaInfo().getGlobalCatalog()));
        Assert.assertEquals(GLOBAL_SCHEMA_NAME, musicTable.getCatalogSchemaInfo().getGlobalSchema());
        Assert.assertTrue(StringUtils.isEmpty(musicTable.getCatalogSchemaInfo().getCatalog()));
        Assert.assertTrue(StringUtils.isEmpty(musicTable.getCatalogSchemaInfo().getSchema()));

        Table specSchemaSportTable = metaModelContext.getTable(SpecSchemaSport_.class);
        Assert.assertNotNull(specSchemaSportTable);
        Assert.assertNotNull(specSchemaSportTable.getCatalogSchemaInfo());
        Assert.assertEquals(GLOBAL_SCHEMA_NAME, specSchemaSportTable.getCatalogSchemaInfo().getGlobalSchema());
        Assert.assertEquals(SpecSchemaSport.SCHEMA, specSchemaSportTable.getCatalogSchemaInfo().getSchema());
        Assert.assertTrue(StringUtils.isEmpty(specSchemaSportTable.getCatalogSchemaInfo().getCatalog()));

    }
}
