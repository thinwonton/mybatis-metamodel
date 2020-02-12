package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.entity.State;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.entity.UserInfo_;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.mapper.UserInfoMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisplusSpringXmlTest {
    /**
     * 测试使用mybatis-plus的 configuration 配置
     */
    @Test
    public void testUseMybatisPlusConfiguration() {
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

        // 根据 map 查询
        Map<String, Object> map = new HashMap<>();
        map.put(UserInfo_.username.getColumn(), "hugo_1"); //获取元数据
        userInfo = userInfoMapper.selectByMap(map).get(0);
        Assert.assertEquals(Long.valueOf(1), userInfo.getId());

        // wrapper查询
        // 查询列表
        List<UserInfo> userList = userInfoMapper.selectList(
                new QueryWrapper<UserInfo>().eq(UserInfo_.address.getColumn(), "中国")
        );
        Assert.assertEquals(10, userList.size());

        MetaModelContext metaModelContext = context.getBean(MetaModelContext.class);
        String tableName = metaModelContext.getTableName(UserInfo_.class);
        Assert.assertNotNull(tableName);
        Assert.assertEquals("user_info", tableName);
    }

    /**
     * 测试使用mybatis的 sqlSessionFactory 配置
     */
    @Test
    public void testUseMybatisSqlSessionFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext("globalconfig/spring.xml");
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

        // 根据 map 查询
        Map<String, Object> map = new HashMap<>();
        map.put(UserInfo_.username.getColumn(), "hugo_1"); //获取元数据
        userInfo = userInfoMapper.selectByMap(map).get(0);
        Assert.assertEquals(Long.valueOf(1), userInfo.getId());

        // wrapper查询
        // 查询列表
        List<UserInfo> userList = userInfoMapper.selectList(
                new QueryWrapper<UserInfo>().eq(UserInfo_.address.getColumn(), "中国")
        );
        Assert.assertEquals(10, userList.size());

        MetaModelContext metaModelContext = context.getBean(MetaModelContext.class);
        String tableName = metaModelContext.getTableName(UserInfo_.class);
        Assert.assertNotNull(tableName);
        Assert.assertEquals("user_info", tableName);
    }

}
