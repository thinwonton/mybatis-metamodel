package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Data;

@GenMetaModel
@Data
@TableName("music")
public class MusicWithLombok extends Entity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
}
