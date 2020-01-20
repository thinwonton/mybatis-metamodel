package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.register;

import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.Table;
import com.github.thinwonton.mybatis.metamodel.core.util.StringUtils;
import com.github.thinwonton.mybatis.metamodel.tkmapper.register.TKMapperEntityResolver;
import com.github.thinwonton.mybatis.metamodel.tkmapper.register.TKMapperGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.TKMapperTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;

import java.util.Date;

/**
 * 测试生成的 MetaModel
 */
public class TKMapperMetaModelTest extends TKMapperTestBase {

    private MetaModelContext metaModelContext;

    private String GLOBAL_SCHEMA_NAME = "global_schema";

    private String GLOBAL_CATALOG_NAME = "global_catalog";

    @Override
    public void setup() {
        super.setup();

        Configuration configuration = getSqlSessionFactory().getConfiguration();
        EntityResolver entityResolver = new TKMapperEntityResolver();
        metaModelContext = new MetaModelContext(
                new TKMapperGlobalConfigFactory(configuration, getMapperHelper()),
                entityResolver);
    }

    @Override
    protected Config getConfig() {
        Config config = new Config();
        config.setSchema(GLOBAL_SCHEMA_NAME);
        config.setCatalog(GLOBAL_CATALOG_NAME);
        config.setStyle(Style.camelhump); //驼峰
        return config;
    }

    /**
     * 测试表名
     */
    @Test
    public void testTableName() {
        String expectedSimpleTableName = "music";
        String expectedTableName = Table.makeTableName(GLOBAL_CATALOG_NAME, expectedSimpleTableName);
        String simpleTableName = metaModelContext.getSimpleTableName(Music_.class);
        Assert.assertEquals(expectedSimpleTableName, simpleTableName);
        String complicatedTableName = metaModelContext.getTableName(Music_.class);
        Assert.assertEquals(expectedTableName, complicatedTableName);

        String expectedSimpleTableName2 = "spec_catalog_schema_sport";
        String expectedTableName2 = Table.makeTableName(SpecCatalogSchemaSport.CATALOG, expectedSimpleTableName2);
        String simpleTableName2 = metaModelContext.getSimpleTableName(SpecCatalogSchemaSport_.class);
        Assert.assertEquals(expectedSimpleTableName2, simpleTableName2);
        String complicatedTableName2 = metaModelContext.getTableName(SpecCatalogSchemaSport_.class);
        Assert.assertEquals(expectedTableName2, complicatedTableName2);

        String expectedSimpleTableName3 = "SPECSCHEMASPORT";
        String expectedTableName3 = Table.makeTableName(SpecSchemaSport.SCHEMA, expectedSimpleTableName3);
        String simpleTableName3 = metaModelContext.getSimpleTableName(SpecSchemaSport_.class);
        Assert.assertEquals(expectedSimpleTableName3, simpleTableName3);
        String complicatedTableName3 = metaModelContext.getTableName(SpecSchemaSport_.class);
        Assert.assertEquals(expectedTableName3, complicatedTableName3);
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

        //测试nameStyle
        //id
        Assert.assertEquals("ID", FieldTestEntity_.id.getColumn());
        Assert.assertFalse(FieldTestEntity_.id.isId());
        Assert.assertEquals(Long.class, FieldTestEntity_.id.getJavaType());
        Assert.assertEquals("id", FieldTestEntity_.id.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, FieldTestEntity_.id.getJdbcType());

        //name
        Assert.assertEquals("name2", FieldTestEntity_.name.getColumn());
        Assert.assertFalse(FieldTestEntity_.name.isId());
        Assert.assertEquals(String.class, FieldTestEntity_.name.getJavaType());
        Assert.assertEquals("name", FieldTestEntity_.name.getProperty());
        Assert.assertEquals(JdbcType.UNDEFINED, FieldTestEntity_.name.getJdbcType());
    }

    @Test
    public void testCatalogAndSchema() {
        Assert.assertNotNull(metaModelContext);
        GlobalConfig globalConfig = metaModelContext.getGlobalConfig();
        Assert.assertNotNull(globalConfig);

        Assert.assertEquals(GLOBAL_SCHEMA_NAME, globalConfig.getSchema());
        Assert.assertEquals(GLOBAL_CATALOG_NAME, globalConfig.getCatalog());

        Table musicTable = metaModelContext.getTable(Music_.class);
        Assert.assertNotNull(musicTable);
        Assert.assertNotNull(musicTable.getCatalogSchemaInfo());
        Assert.assertEquals(GLOBAL_CATALOG_NAME, musicTable.getCatalogSchemaInfo().getGlobalCatalog());
        Assert.assertEquals(GLOBAL_SCHEMA_NAME, musicTable.getCatalogSchemaInfo().getGlobalSchema());
        Assert.assertTrue(StringUtils.isEmpty(musicTable.getCatalogSchemaInfo().getCatalog()));
        Assert.assertTrue(StringUtils.isEmpty(musicTable.getCatalogSchemaInfo().getSchema()));

        Table specCatalogSchemaSportTable = metaModelContext.getTable(SpecCatalogSchemaSport_.class);
        Assert.assertNotNull(specCatalogSchemaSportTable);
        Assert.assertNotNull(specCatalogSchemaSportTable.getCatalogSchemaInfo());
        Assert.assertEquals(GLOBAL_CATALOG_NAME, specCatalogSchemaSportTable.getCatalogSchemaInfo().getGlobalCatalog());
        Assert.assertEquals(GLOBAL_SCHEMA_NAME, specCatalogSchemaSportTable.getCatalogSchemaInfo().getGlobalSchema());
        Assert.assertEquals(SpecCatalogSchemaSport.SCHEMA, specCatalogSchemaSportTable.getCatalogSchemaInfo().getSchema());
        Assert.assertEquals(SpecCatalogSchemaSport.CATALOG, specCatalogSchemaSportTable.getCatalogSchemaInfo().getCatalog());

        Table specSchemaSportTable = metaModelContext.getTable(SpecSchemaSport_.class);
        Assert.assertNotNull(specSchemaSportTable);
        Assert.assertNotNull(specSchemaSportTable.getCatalogSchemaInfo());
        Assert.assertEquals(GLOBAL_CATALOG_NAME, specSchemaSportTable.getCatalogSchemaInfo().getGlobalCatalog());
        Assert.assertEquals(GLOBAL_SCHEMA_NAME, specSchemaSportTable.getCatalogSchemaInfo().getGlobalSchema());
        Assert.assertEquals(SpecSchemaSport.SCHEMA, specSchemaSportTable.getCatalogSchemaInfo().getSchema());
        Assert.assertTrue(StringUtils.isEmpty(specSchemaSportTable.getCatalogSchemaInfo().getCatalog()));

    }

}
