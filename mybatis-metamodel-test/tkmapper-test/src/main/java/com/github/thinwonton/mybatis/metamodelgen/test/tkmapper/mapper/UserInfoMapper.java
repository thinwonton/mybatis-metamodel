package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.mapper;

import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.UserInfo;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {
    @SelectProvider(type = UserInfoSQLProvider.class, method = "selectUsernameAndPassword")
    List<UserInfo> selectUsernameAndPassword();
}
