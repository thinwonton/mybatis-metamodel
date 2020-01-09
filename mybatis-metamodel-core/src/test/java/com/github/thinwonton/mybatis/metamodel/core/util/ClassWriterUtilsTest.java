package com.github.thinwonton.mybatis.metamodel.core.util;

import com.github.thinwonton.mybatis.metamodel.core.test.Entity;
import com.github.thinwonton.mybatis.metamodel.core.test.Entity_;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ClassWriterUtilsTest {

    @Test
    public void testGetMappedMetaModelClassName() {
        String mappedMetaModelClassName = ClassWriterUtils.getMappedMetaModelClassName(Entity.class);
        Assert.assertEquals(Entity_.class.getName(), mappedMetaModelClassName);
    }

    @Test
    public void testGetMappedEntityClassName() {
        String mappedEntityClassName = ClassWriterUtils.getMappedEntityClassName(Entity_.class);
        Assert.assertEquals(Entity.class.getName(), mappedEntityClassName);
    }
}