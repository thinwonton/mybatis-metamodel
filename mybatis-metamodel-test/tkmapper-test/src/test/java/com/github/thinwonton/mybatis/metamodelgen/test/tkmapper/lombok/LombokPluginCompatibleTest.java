package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.lombok;

import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.UseLombokEntity;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 测试与lombok插件兼容性
 */
public class LombokPluginCompatibleTest {
    @Test
    public void testLombokValidated() throws NoSuchMethodException {
        //是否有getter setter
        Method getIdMethod = UseLombokEntity.class.getMethod("getId");
        Assert.assertNotNull(getIdMethod);
        Assert.assertEquals(Long.class, getIdMethod.getReturnType());
        Method setIdMethod = UseLombokEntity.class.getMethod("setId", Long.class);
        Assert.assertNotNull(setIdMethod);
        Assert.assertEquals(void.class, setIdMethod.getReturnType());
    }
}
