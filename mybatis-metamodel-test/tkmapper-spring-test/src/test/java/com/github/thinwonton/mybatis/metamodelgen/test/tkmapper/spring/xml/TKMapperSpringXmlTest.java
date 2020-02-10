package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.xml;

import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity.State;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.entity.UserInfo_;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.mapper.UserInfoMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class TKMapperSpringXmlTest {
    /**
     * 测试使用TKMapper的MapperScannerConfigurer
     */
    @Test
    public void testUseTKMapperMapperScannerConfigurer() {
        ApplicationContext context = new ClassPathXmlApplicationContext("xml/spring.xml");
        UserInfoMapper userInfoMapper = context.getBean(UserInfoMapper.class);

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
        criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);
        List<UserInfo> userInfosByQuery = userInfoMapper.selectByExample(userInfoQueryExample);
        Assert.assertNotNull(userInfosByQuery);
        Assert.assertEquals(1, userInfosByQuery.size());
        UserInfo userInfoByQuery = userInfosByQuery.get(0);
        Assert.assertEquals(queryUserName, userInfoByQuery.getUsername());

        MetaModelContext metaModelContext = context.getBean(MetaModelContext.class);
        String tableName = metaModelContext.getTableName(UserInfo_.class);
        Assert.assertNotNull(tableName);
        Assert.assertEquals("user_info", tableName);
    }

    /**
     * 测试使用TKMapper的 Configuration
     */
    @Test
    public void testUseTKMapperConfiguration() {
        ApplicationContext context = new ClassPathXmlApplicationContext("configuration/spring.xml");
        UserInfoMapper userInfoMapper = context.getBean(UserInfoMapper.class);

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
        criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);
        List<UserInfo> userInfosByQuery = userInfoMapper.selectByExample(userInfoQueryExample);
        Assert.assertNotNull(userInfosByQuery);
        Assert.assertEquals(1, userInfosByQuery.size());
        UserInfo userInfoByQuery = userInfosByQuery.get(0);
        Assert.assertEquals(queryUserName, userInfoByQuery.getUsername());

        MetaModelContext metaModelContext = context.getBean(MetaModelContext.class);
        String tableName = metaModelContext.getTableName(UserInfo_.class);
        Assert.assertNotNull(tableName);
        Assert.assertEquals("user_info", tableName);
    }
}
