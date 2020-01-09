package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Music;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper.MusicMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.Date;

public class MybatisPlusTest {
    @Test
    public void test() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = factory.openSession();
        Connection connection = sqlSession.getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.runScript(Resources.getResourceAsReader("db.sql"));

        MusicMapper mapper = sqlSession.getMapper(MusicMapper.class);
        Music music = new Music();
        music.setName("饿狼传说");
        Date now = new Date();
        music.setCreateDate(now);
        music.setUpdateDate(now);

        Assert.assertEquals(1, mapper.insert(music));

        Assert.assertNotNull(music.getId());
        Assert.assertEquals(6L, music.getId().longValue());

        Music musicFromDb = mapper.selectById(6L);
        Assert.assertNotNull(musicFromDb);
        Assert.assertEquals(now.getTime(), musicFromDb.getCreateDate().getTime());
        Assert.assertEquals(now.getTime(), musicFromDb.getUpdateDate().getTime());
    }
}
