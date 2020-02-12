package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.entity.UserInfo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
