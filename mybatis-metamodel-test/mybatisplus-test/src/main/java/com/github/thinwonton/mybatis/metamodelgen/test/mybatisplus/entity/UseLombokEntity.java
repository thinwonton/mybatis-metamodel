package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Data;

/**
 * 测试与lombok插件的兼容性
 */
@GenMetaModel
@Data
@TableName("music")
public class UseLombokEntity {
    @TableId
    private Long id;
    private String name;
}
