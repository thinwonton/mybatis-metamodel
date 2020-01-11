package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper;

import org.apache.ibatis.jdbc.SQL;

public class MusicSelectProvider {
    public String getMusicById(Long id) {
        return new SQL()
                .SELECT("*")
                .FROM("music")
                .WHERE("id=#{id}")
                .toString();
    }
}
