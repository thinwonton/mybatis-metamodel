package com.github.thinwonton.mybatis.metamodel.core;

import com.github.thinwonton.mybatis.metamodel.core.util.Style;

/**
 * TKMapper 的全局配置 Config
 */
public class TKMapperConfig {
    private boolean usePrimitiveType;

    private boolean useSimpleType = true;

    private boolean enumAsSimpleType;

    /**
     * 数据库的catalog
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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public boolean isUsePrimitiveType() {
        return usePrimitiveType;
    }

    public void setUsePrimitiveType(boolean usePrimitiveType) {
        this.usePrimitiveType = usePrimitiveType;
    }

    public boolean isEnumAsSimpleType() {
        return enumAsSimpleType;
    }

    public void setEnumAsSimpleType(boolean enumAsSimpleType) {
        this.enumAsSimpleType = enumAsSimpleType;
    }

    public boolean isUseSimpleType() {
        return useSimpleType;
    }

    public void setUseSimpleType(boolean useSimpleType) {
        this.useSimpleType = useSimpleType;
    }

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
}
