package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.Serializable;

@GenMetaModel
public class MusicWithTransientField extends Entity implements Serializable {
    private static final long serialVersionUID = 767652858834706096L;

    private transient Log log = LogFactory.getLog(getClass());

    @TableField(exist = false)
    private String desc;

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
