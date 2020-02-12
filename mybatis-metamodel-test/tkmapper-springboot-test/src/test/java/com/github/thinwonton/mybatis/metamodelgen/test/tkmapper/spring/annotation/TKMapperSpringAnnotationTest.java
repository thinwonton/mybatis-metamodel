package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.annotation;

import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.TKMapperSpringbootApplication;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.entity.State;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.entity.UserInfo_;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.mapper.UserInfoMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TKMapperSpringbootApplication.class)
public class TKMapperSpringAnnotationTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private MetaModelContext metaModelContext;

    @Test
    public void test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setState(State.ENABLE);
        userInfo.setPassword("123456");
        userInfo.setUsertype("普通用户");
        userInfo.setRealname("云吞");
        userInfo.setAddress("中国");

        //插入10条记录
        for (long i = 1; i <= 10; i++) {
            userInfo.setId(i);
            userInfo.setUsername("hugo_" + i);
            Assert.assertEquals(1, userInfoMapper.insert(userInfo));
        }

        // 根据 example 条件查询
        String queryUserName = "hugo_1";
        Example userInfoQueryExample = new Example(UserInfo.class);
        Example.Criteria criteria = userInfoQueryExample.createCriteria();
        System.out.println(UserInfo_.class);
        criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);
        List<UserInfo> userInfosByQuery = userInfoMapper.selectByExample(userInfoQueryExample);
        Assert.assertNotNull(userInfosByQuery);
        Assert.assertEquals(1, userInfosByQuery.size());
        UserInfo userInfoByQuery = userInfosByQuery.get(0);
        Assert.assertEquals(queryUserName, userInfoByQuery.getUsername());

        String tableName = metaModelContext.getTableName(UserInfo_.class);
        Assert.assertNotNull(tableName);
        Assert.assertEquals("user_info", tableName);

    }


}
