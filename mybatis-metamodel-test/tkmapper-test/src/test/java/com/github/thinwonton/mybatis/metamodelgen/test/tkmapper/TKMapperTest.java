package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper;

import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.Country;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.Music;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.UserInfo;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.mapper.CountryMapper;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.mapper.MusicMapper;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.mapper.UserInfoMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * TKMapper测试
 *
 * @author hugo
 */
@RunWith(JUnit4.class)
public class TKMapperTest extends TKMapperTestBase {
    @Test
    public void testInsert() {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//        MetaModelContext metaModelContext = new MetaModelContext(sqlSessionFactory.getConfiguration());

        SqlSession sqlSession = getSqlSession();
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("hugo");
        userInfo.setPassword("123456");
        userInfo.setUsertype("2");
        userInfo.setEmail("work.hugo.huang@gmail.com");

        Assert.assertEquals(1, mapper.insert(userInfo));

        Assert.assertNotNull(userInfo.getId());
        Assert.assertTrue((int) userInfo.getId() >= 6);

        Assert.assertEquals(1, mapper.deleteByPrimaryKey(userInfo));

        sqlSession.close();
    }

    @Test
    public void testInsertMusic() {
        SqlSession sqlSession = getSqlSession();
        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);
        Music music = new Music();
        music.setName("饿狼传说");
        Date now = new Date();
        music.setCreateDate(now);
        music.setUpdateDate(now);

        Assert.assertEquals(1, mapper.insert(music));

        Assert.assertNotNull(music.getId());

        Music musicFromDb = mapper.selectByPrimaryKey(music);
        Assert.assertNotNull(musicFromDb);
        Assert.assertEquals(now.getTime(), musicFromDb.getCreateDate().getTime());
        Assert.assertEquals(now.getTime(), musicFromDb.getUpdateDate().getTime());

        sqlSession.close();
    }

    @Test
    public void testSelectByExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Example example = new Example(Country.class);
            example.createCriteria().andGreaterThan("id", 100).andLessThan("id", 151);
            example.or().andLessThan("id", 41);
            List<Country> countries = mapper.selectByExample(example);
            //查询总数
            Assert.assertEquals(90, countries.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByExampleForUpdate() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Example example = new Example(Country.class);
            example.setForUpdate(true);
            example.createCriteria().andGreaterThan("id", 100).andLessThan("id", 151);
            example.or().andLessThan("id", 41);
            List<Country> countries = mapper.selectByExample(example);
            //查询总数
            Assert.assertEquals(90, countries.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAndExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Example example = new Example(Country.class);
            example.createCriteria()
                    .andCondition("countryname like 'C%' and id < 100")
                    .andCondition("length(countryname) = ", 5);
            List<Country> countries = mapper.selectByExample(example);
            //查询总数
            Assert.assertEquals(3, countries.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByExampleInNotIn() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Example example = new Example(Country.class);
            Set<Integer> set = new HashSet<Integer>();
            set.addAll(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}));
            example.createCriteria().andIn("id", set)
                    .andNotIn("id", Arrays.asList(new Object[]{11}));
            List<Country> countries = mapper.selectByExample(example);
            //查询总数
            Assert.assertEquals(10, countries.size());
        } finally {
            sqlSession.close();
        }
    }
}
