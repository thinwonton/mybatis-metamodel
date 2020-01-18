package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;

/**
 * 指定schema
 */
@GenMetaModel
@TableName(schema = SpecSchemaSport.SCHEMA)
public class SpecSchemaSport {
    public static final String SCHEMA = "SpecSchemaSport_schema";

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
