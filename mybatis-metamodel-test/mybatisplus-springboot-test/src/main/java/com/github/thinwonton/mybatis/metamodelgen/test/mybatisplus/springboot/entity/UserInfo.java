package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试用户类
 */
@GenMetaModel
@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @TableId
    private Long id;
    private String username;
    private String password;
    @TableField("user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}
