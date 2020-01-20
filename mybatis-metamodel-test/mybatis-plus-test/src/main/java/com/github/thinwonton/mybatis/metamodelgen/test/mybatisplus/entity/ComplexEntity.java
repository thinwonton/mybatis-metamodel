package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 复杂的实体Entity
 */
@GenMetaModel
@Getter
@Setter
public class ComplexEntity implements Serializable {
    private static final long serialVersionUID = -3111558058262086115L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private State state;

    private String userName;

    private Integer age;

    @TableField(typeHandler = Address.AddressHandler.class)
    private Address address;

    private List<String> list;

}
