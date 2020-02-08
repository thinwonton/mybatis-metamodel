package com.github.thinwonton.mybatis.metamodel.core;

import com.github.thinwonton.mybatis.metamodel.core.util.Style;

/**
 * MybatisPlus 的全局配置 Config
 */
public class MybatisPlusConfig {

    /**
     * 数据库的schema
     */
    private String schema;

    /**
     * 字段的转换方式
     */
    private Style style;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
