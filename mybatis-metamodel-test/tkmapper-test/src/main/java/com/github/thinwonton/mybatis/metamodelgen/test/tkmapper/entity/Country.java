package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.TestEntity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table()
public class Country implements Speakable, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String countryname;
    private String countrycode;
    private int age;

    private TestEntity testEntity;

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean canSpeak() {
        return false;
    }
}
