package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 * 复杂的实体Entity
 */
@GenMetaModel
@Getter
@Setter
public class ComplexEntity {
    @Id
    private Long id;
    private String userName;

    private List<String> list;

    @Column
    @ColumnType(typeHandler = Address.AddressHandler.class)
    private Address address;

    @Column
    private State state;
}
