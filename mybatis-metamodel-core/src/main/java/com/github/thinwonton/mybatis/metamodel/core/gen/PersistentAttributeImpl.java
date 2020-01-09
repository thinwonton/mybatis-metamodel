package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.register.TableField;

public class PersistentAttributeImpl implements PersistentAttribute {

    private TableField tableField;

    public PersistentAttributeImpl(TableField tableField) {
        this.tableField = tableField;
    }

    @Override
    public String getColumn() {
        return tableField.getColumn();
    }

    @Override
    public Class<?> getJavaType() {
        return tableField.getJavaType();
    }

    @Override
    public boolean isId() {
        return tableField.isId();
    }

    @Override
    public String getProperty() {
        return tableField.getProperty();
    }
}
