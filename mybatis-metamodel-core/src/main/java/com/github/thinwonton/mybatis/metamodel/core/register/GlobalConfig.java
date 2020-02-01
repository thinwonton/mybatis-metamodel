package com.github.thinwonton.mybatis.metamodel.core.register;

import com.github.thinwonton.mybatis.metamodel.core.MybatisPlusConfig;
import com.github.thinwonton.mybatis.metamodel.core.TKMapperConfig;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalConfig 全局配置
 */
public final class GlobalConfig {

    /**
     * tk mapper的配置
     */
    private final TKMapperConfig tkMapperConfig = new TKMapperConfig();

    /**
     * mybatis plus的配置
     */
    private final MybatisPlusConfig mybatisPlusConfig = new MybatisPlusConfig();

    /**
     * mybatis的 MappedStatement
     */
    private final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public Collection<String> getMappedStatementNames() {
        return mappedStatements.keySet();
    }

    public Collection<MappedStatement> getMappedStatements() {
        return mappedStatements.values();
    }

    public TKMapperConfig getTkMapperConfig() {
        return tkMapperConfig;
    }

    public MybatisPlusConfig getMybatisPlusConfig() {
        return mybatisPlusConfig;
    }
}
