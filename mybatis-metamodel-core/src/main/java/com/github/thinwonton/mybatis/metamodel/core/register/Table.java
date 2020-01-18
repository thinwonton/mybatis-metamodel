package com.github.thinwonton.mybatis.metamodel.core.register;

import com.github.thinwonton.mybatis.metamodel.core.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Table
 */
public class Table {

    // 对应的实体名
    private Class<?> entityClass;

    // 表名
    private String simpleTableName;

    private CatalogSchemaInfo catalogSchemaInfo;

    private Collection<TableField> tableFields = new HashSet<>();

    public Table() {
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getSimpleTableName() {
        return simpleTableName;
    }

    public void setSimpleTableName(String simpleTableName) {
        this.simpleTableName = simpleTableName;
    }

    public void addTableFields(Collection<TableField> tableFields) {
        if (tableFields != null) {
            this.tableFields.addAll(tableFields);
        }
    }

    public Collection<TableField> getTableFields() {
        return tableFields;
    }

    public CatalogSchemaInfo getCatalogSchemaInfo() {
        return catalogSchemaInfo;
    }

    public void setCatalogSchemaInfo(CatalogSchemaInfo catalogSchemaInfo) {
        this.catalogSchemaInfo = catalogSchemaInfo;
    }

    /**
     * 获取表名前缀
     *
     * @return
     */
    public String getTableNamePrefix() {
        // catalog 优于 schema
        if (Objects.nonNull(catalogSchemaInfo)
                && (StringUtils.isNotEmpty(catalogSchemaInfo.getCatalog()) || StringUtils.isNotEmpty(catalogSchemaInfo.getSchema()))) {
            if (StringUtils.isNotEmpty(catalogSchemaInfo.getCatalog())) {
                return catalogSchemaInfo.getCatalog();
            }

            if (StringUtils.isNotEmpty(catalogSchemaInfo.getSchema())) {
                return catalogSchemaInfo.getSchema();
            }
        }

        if (Objects.nonNull(catalogSchemaInfo)
                && (StringUtils.isNotEmpty(catalogSchemaInfo.getGlobalCatalog()) || StringUtils.isNotEmpty(catalogSchemaInfo.getGlobalSchema()))) {
            if (StringUtils.isNotEmpty(catalogSchemaInfo.getGlobalCatalog())) {
                return catalogSchemaInfo.getGlobalCatalog();
            }

            if (StringUtils.isNotEmpty(catalogSchemaInfo.getGlobalSchema())) {
                return catalogSchemaInfo.getGlobalSchema();
            }
        }

        return null;
    }

    /**
     * 获取带前缀的表名(加上catalog或者schema)
     */
    public String getTableName() {
        return makeTableName(getTableNamePrefix(), getSimpleTableName());
    }

    public static String makeTableName(String prefix, String simpleTableName) {
        if (StringUtils.isNotEmpty(prefix)) {
            return prefix + StringUtils.DOT + simpleTableName;
        }
        return simpleTableName;
    }

    public static class CatalogSchemaInfo {

        // 数据库的catalog
        private String catalog;

        // 数据库的schema
        private String schema;

        // 全局配置指定的数据库的catalog
        private String globalCatalog;

        // 全局配置指定的数据库的schema
        private String globalSchema;

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

        public String getGlobalCatalog() {
            return globalCatalog;
        }

        public void setGlobalCatalog(String globalCatalog) {
            this.globalCatalog = globalCatalog;
        }

        public String getGlobalSchema() {
            return globalSchema;
        }

        public void setGlobalSchema(String globalSchema) {
            this.globalSchema = globalSchema;
        }
    }
}
