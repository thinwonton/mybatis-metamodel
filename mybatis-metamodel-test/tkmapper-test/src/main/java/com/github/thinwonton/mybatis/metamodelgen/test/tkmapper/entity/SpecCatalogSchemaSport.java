package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;

import javax.persistence.Table;

import static com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.SpecCatalogSchemaSport.CATALOG;
import static com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.SpecCatalogSchemaSport.SCHEMA;

/**
 * 指定schema
 */
@GenMetaModel
@Table(catalog = CATALOG, schema = SCHEMA)
public class SpecCatalogSchemaSport {
    public static final String SCHEMA = "SpecCatalogSchemaSport_schema";
    public static final String CATALOG = "SpecCatalogSchemaSport_catalog";

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
