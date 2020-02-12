package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper;

import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.MetaModelContextHolder;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.UserInfo_;
import org.apache.ibatis.jdbc.SQL;

public class UserInfoSQLProvider {
    public String selectUsernameAndPassword() {
        MetaModelContext metaModelContext = MetaModelContextHolder.getInstance();

        return new SQL()
                .SELECT(
                        UserInfo_.id.getColumn(),
                        UserInfo_.username.getColumn(),
                        UserInfo_.password.getColumn()
                )
                // 从context获取表名
                .FROM(metaModelContext.getTable(UserInfo_.class).getTableName())
                .toString();
    }
}
