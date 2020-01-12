package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 测试与lombok插件的兼容性
 */
@GenMetaModel
@Data
@Table
public class UseLombokEntity {
    @Id
    private Long id;
    private String name;
}
