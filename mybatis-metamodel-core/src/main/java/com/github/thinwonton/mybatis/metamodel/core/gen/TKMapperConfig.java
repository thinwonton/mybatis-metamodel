package com.github.thinwonton.mybatis.metamodel.core.gen;

/**
 * TKMapperConfig
 */
public class TKMapperConfig {
    private boolean usePrimitiveType;

    private boolean useSimpleType = true;

    private boolean enumAsSimpleType;

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
}
