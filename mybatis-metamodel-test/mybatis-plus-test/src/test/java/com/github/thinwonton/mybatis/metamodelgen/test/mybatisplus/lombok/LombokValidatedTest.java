package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.lombok;

import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.MusicWithLombok;
import org.junit.Test;

import java.util.Date;

/**
 * 测试lombok插件是否可以共存
 */
public class LombokValidatedTest {
    @Test
    public void testLombokValidated() {
        MusicWithLombok musicWithLombok = new MusicWithLombok();
        musicWithLombok.setId(10L);
        musicWithLombok.setName("hugo");
        musicWithLombok.setCreateDate(new Date());
        musicWithLombok.setUpdateDate(new Date());
    }
}
