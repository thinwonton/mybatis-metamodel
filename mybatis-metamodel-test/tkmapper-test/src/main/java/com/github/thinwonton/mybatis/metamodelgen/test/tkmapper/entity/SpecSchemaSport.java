package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.SpecSchemaSport.SCHEMA;

/**
 * 指定schema
 */
@GenMetaModel
@Table(schema = SCHEMA)
@NameStyle(Style.uppercase)
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
