package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.UserInfo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @SelectProvider(type = UserInfoSQLProvider.class, method = "selectUsernameAndPassword")
    List<UserInfo> selectUsernameAndPassword();
}
