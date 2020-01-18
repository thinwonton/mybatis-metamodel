package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.register;

import com.github.thinwonton.mybatis.metamodel.core.register.EntityResolver;
import com.github.thinwonton.mybatis.metamodel.tkmapper.TKMapperEntityResolver;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.TKMapperTestBase;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity.Music;
import com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.mapper.MusicMapper;
import org.apache.ibatis.mapping.MappedStatement;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
        EntityResolver mybatisPlusEntityResolver = new TKMapperEntityResolver();
        MappedStatement mockedMappedStatement = PowerMockito.mock(MappedStatement.class);
        String mappedStatementId = MusicMapper.class.getName() + "." + "selectById";
        PowerMockito.when(mockedMappedStatement.getId())
                .thenReturn(mappedStatementId);

        Class<?> mappedEntityClass = mybatisPlusEntityResolver.getMappedEntityClass(mockedMappedStatement);
        Assert.assertNotNull(mappedEntityClass);
        Assert.assertEquals(Music.class, mappedEntityClass);
    }

}
