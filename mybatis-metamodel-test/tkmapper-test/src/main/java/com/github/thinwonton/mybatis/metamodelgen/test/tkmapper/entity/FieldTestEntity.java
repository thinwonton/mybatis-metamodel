package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Column;
import javax.persistence.Table;

import static com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.FieldTestEntity.SCHEMA;


/**
 * 指定schema
 */
@GenMetaModel
@Table(schema = SCHEMA)
@NameStyle(Style.uppercase)
public class FieldTestEntity {
    public static final String SCHEMA = "FieldTestEntity_schema";

    private Long id;

    @Column(name = "name2")
    private String name;

    private long age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
