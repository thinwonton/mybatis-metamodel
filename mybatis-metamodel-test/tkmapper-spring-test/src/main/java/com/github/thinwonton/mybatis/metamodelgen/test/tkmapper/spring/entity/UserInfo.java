package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 测试用户类
 */
@GenMetaModel
@Data
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @Id
    private Long id;
    private String username;
    private String password;
    @Column(name = "user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}
