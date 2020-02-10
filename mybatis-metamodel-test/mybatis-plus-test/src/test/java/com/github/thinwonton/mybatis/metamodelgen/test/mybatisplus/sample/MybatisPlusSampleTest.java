package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.sample;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContextImpl;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.register.MybatisPlusGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.MetaModelContextHolder;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.MybatisPlusTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.State;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.UserInfo_;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper.UserInfoMapper;
import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisPlusSampleTest extends MybatisPlusTestBase {

    @Override
    protected List<Reader> getSqlFilesAsReader() throws IOException {
        Reader schemaReader = Resources.getResourceAsReader("schema.sql");
        List<Reader> list = new ArrayList<>();
        list.add(schemaReader);
        return list;
    }

    @Override
    protected void configMybatisConfiguration(MybatisConfiguration mybatisConfiguration) {
        GlobalConfig globalConfig = mybatisConfiguration.getGlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        globalConfig.setDbConfig(dbConfig);
        dbConfig.setTableUnderline(true); //驼峰
    }

    @Override
    protected void initInternal() {
        MybatisConfiguration configuration = (MybatisConfiguration) getSqlSessionFactory().getConfiguration();
        EntityResolver entityResolver = new MybatisPlusEntityResolver();
        MetaModelContext metaModelContext = new MetaModelContextImpl(
                new MybatisPlusGlobalConfigFactory(configuration),
                entityResolver);
        //单例
        MetaModelContextHolder.setup(metaModelContext);
    }

    @Test
    public void testSample() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.setState(State.ENABLE);
        userInfo.setPassword("123456");
        userInfo.setUsertype("普通用户");
        userInfo.setRealname("云吞");
        userInfo.setAddress("中国");

        UserInfoMapper userInfoMapper = getSqlSession().getMapper(UserInfoMapper.class);

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

        //@SelectProvider注解方式查询
        List<UserInfo> userList2 = userInfoMapper.selectUsernameAndPassword();
        Assert.assertNotNull(userList2);
        Assert.assertEquals(10, userList2.size());
        for (UserInfo userInfo2 : userList2) {
            Assert.assertNotNull(userInfo2.getId());
            Assert.assertNotNull(userInfo2.getUsername());
            Assert.assertNotNull(userInfo2.getPassword());
        }

    }
}
