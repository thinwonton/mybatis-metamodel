package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.sample;

import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContext;
import com.github.thinwonton.mybatis.metamodel.core.register.MetaModelContextImpl;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register.TKMapperEntityResolver;
import com.github.thinwonton.mybatis.metamodel.tkmapper.processor.register.TKMapperGlobalConfigFactory;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.MetaModelContextHolder;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.TKMapperTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.State;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.UserInfo_;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.mapper.UserInfoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class TKMapperSampleTest extends TKMapperTestBase {

    @Override
    protected List<Reader> getSqlFilesAsReader() throws IOException {
        Reader schemaReader = Resources.getResourceAsReader("schema.sql");
        List<Reader> list = new ArrayList<>();
        list.add(schemaReader);
        return list;
    }

    @Override
    protected Config getConfig() {
        Config config = new Config();
        config.setStyle(Style.camelhump); //驼峰
        return config;
    }

    @Override
    protected void initInternal() {
        EntityResolver entityResolver = new TKMapperEntityResolver();
        MetaModelContext metaModelContext = new MetaModelContextImpl(
                new TKMapperGlobalConfigFactory(getSqlSessionFactory(), getMapperHelper()),
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
