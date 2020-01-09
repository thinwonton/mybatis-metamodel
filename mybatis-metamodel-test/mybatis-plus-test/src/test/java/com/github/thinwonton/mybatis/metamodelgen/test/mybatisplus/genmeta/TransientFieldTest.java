package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.genmeta;

import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.MusicWithTransientField_;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 字段不生成测试
 */
public class TransientFieldTest {
    @Test
    public void testTransientField() {
        List<String> expectedFields = Arrays.asList(
                "name", "id"
        );

        List<String> notExpectedFields = Arrays.asList(
                "serialVersionUID", "desc", "log"
        );

        Field[] declaredFields = MusicWithTransientField_.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Assert.assertTrue(expectedFields.contains(declaredField.getName()));
            Assert.assertFalse(notExpectedFields.contains(declaredField.getName()));
        }
        Assert.assertEquals(2, declaredFields.length);
    }
}
