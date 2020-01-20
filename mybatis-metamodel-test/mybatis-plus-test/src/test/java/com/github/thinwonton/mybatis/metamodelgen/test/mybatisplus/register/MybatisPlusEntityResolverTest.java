package com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.register;

import com.github.thinwonton.mybatis.metamodel.mybatisplus.register.MybatisPlusEntityResolver;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.entity.Music;
import com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.mapper.MusicMapper;
import org.apache.ibatis.mapping.MappedStatement;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;

/**
 * MybatisPlusEntityResolverTest
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MappedStatement.class})
@PowerMockIgnore("javax.management.*")
public class MybatisPlusEntityResolverTest {
    /**
     * 测试通过MappedStatement获取 entityClass
     */
    @Test
    public void testGetMappedEntityClass() {
        MybatisPlusEntityResolver mybatisPlusEntityResolver = new MybatisPlusEntityResolver();
        MappedStatement mockedMappedStatement = PowerMockito.mock(MappedStatement.class);
        String mappedStatementId = MusicMapper.class.getName() + "." + "selectById";
        PowerMockito.when(mockedMappedStatement.getId())
                .thenReturn(mappedStatementId);

        Class<?> mappedEntityClass = mybatisPlusEntityResolver.getMappedEntityClass(mockedMappedStatement);
        Assert.assertNotNull(mappedEntityClass);
        Assert.assertEquals(Music.class, mappedEntityClass);
    }

}
