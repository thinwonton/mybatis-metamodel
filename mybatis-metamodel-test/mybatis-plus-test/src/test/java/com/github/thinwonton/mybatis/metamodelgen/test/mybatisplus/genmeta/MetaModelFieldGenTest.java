package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.genmeta;

import com.github.thinwonton.mybatis.metamodel.core.util.ClassWriterUtils;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Entity;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Music_;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * meta model的成员变量生成测试
 */
public class MetaModelFieldGenTest {
    /**
     * 不生成不是 meta model的字段
     */
    @Test
    public void testNotGenTransientField() {
        List<String> expectedFields = Arrays.asList(
                "name", "id"
        );

        List<String> notExpectedFields = Arrays.asList(
                "serialVersionUID", "desc", "log"
        );

        Field[] declaredFields = Music_.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Assert.assertTrue(expectedFields.contains(declaredField.getName()));
            Assert.assertFalse(notExpectedFields.contains(declaredField.getName()));
        }
        Assert.assertEquals(2, declaredFields.length);
    }

    /**
     * 不生成不是 meta model的字段
     */
    @Test
    public void testGenSuperMetaModel() throws ClassNotFoundException {
        List<String> expectedFields = Arrays.asList(
                "createDate", "updateDate"
        );

        String mappedMetaModelClassName = ClassWriterUtils.getMappedMetaModelClassName(Entity.class);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> entityMetaModelClass = classLoader.loadClass(mappedMetaModelClassName);

        Assert.assertNotNull(entityMetaModelClass);

        Field[] declaredFields = entityMetaModelClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Assert.assertTrue(expectedFields.contains(declaredField.getName()));
        }
        Assert.assertEquals(2, declaredFields.length);
    }
}
