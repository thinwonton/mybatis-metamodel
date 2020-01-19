package com.github.thinwonton.mybatis.metamodel.core.register;

import com.github.thinwonton.mybatis.metamodel.core.util.Style;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalConfig 全局配置
 */
public final class GlobalConfig {
    /**
     * 数据库的catalog，mysql不支持
     */
    private String catalog;

    /**
     * 数据库的schema
     */
    private String schema;

    /**
     * 字段的转换方式
     */
    private Style style;

    /**
     * mybatis的 MappedStatement
     */
    private final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public Collection<String> getMappedStatementNames() {
        return mappedStatements.keySet();
    }

    public Collection<MappedStatement> getMappedStatements() {
        return mappedStatements.values();
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
