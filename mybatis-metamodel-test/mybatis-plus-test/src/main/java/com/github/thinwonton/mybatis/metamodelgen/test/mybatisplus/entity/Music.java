package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;

@GenMetaModel
@TableName("music")
public class Music extends Entity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
