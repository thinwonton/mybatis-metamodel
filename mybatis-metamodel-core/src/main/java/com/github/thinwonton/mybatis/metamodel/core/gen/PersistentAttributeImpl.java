package com.github.thinwonton.mybatis.metamodel.core.gen;

import com.github.thinwonton.mybatis.metamodel.core.register.TableField;
import org.apache.ibatis.type.JdbcType;

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

    @Override
    public JdbcType getJdbcType() {
        return tableField.getJdbcType();
    }
}
