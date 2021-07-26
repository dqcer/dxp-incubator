package com.dqcer.dxptools.sync.bean;

import java.util.List;

/**
 * @author dongqin
 * @description 工具同步配置bean
 * @date 2021/07/26
 */
public class ToolsSyncConfigBean {

    /**
     * 源bean
     */
    private DataSourceBean sourceBean;

    /**
     * 目标bean
     */
    private DataSourceBean targetBean;


    /**
     * 过滤的表
     */
    private List<String> filterTables;

    /**
     * 基本的字段
     */
    private List<String> baseColumns;


    public DataSourceBean getSourceBean() {
        return sourceBean;
    }

    public void setSourceBean(DataSourceBean sourceBean) {
        this.sourceBean = sourceBean;
    }

    public DataSourceBean getTargetBean() {
        return targetBean;
    }

    public void setTargetBean(DataSourceBean targetBean) {
        this.targetBean = targetBean;
    }

    public List<String> getFilterTables() {
        return filterTables;
    }

    public void setFilterTables(List<String> filterTables) {
        this.filterTables = filterTables;
    }

    public List<String> getBaseColumns() {
        return baseColumns;
    }

    public void setBaseColumns(List<String> baseColumns) {
        this.baseColumns = baseColumns;
    }


    public static final class BeanBuilder {
        private ToolsSyncConfigBean toolsSyncConfigBean;

        private BeanBuilder() {
            toolsSyncConfigBean = new ToolsSyncConfigBean();
        }

        public static BeanBuilder configBean() {
            return new BeanBuilder();
        }

        public BeanBuilder sourceBean(DataSourceBean sourceBean) {
            toolsSyncConfigBean.setSourceBean(sourceBean);
            return this;
        }

        public BeanBuilder targetBean(DataSourceBean targetBean) {
            toolsSyncConfigBean.setTargetBean(targetBean);
            return this;
        }

        public BeanBuilder filterTables(List<String> filterTables) {
            toolsSyncConfigBean.setFilterTables(filterTables);
            return this;
        }

        public BeanBuilder baseColumns(List<String> baseColumns) {
            toolsSyncConfigBean.setBaseColumns(baseColumns);
            return this;
        }

        public ToolsSyncConfigBean build() {
            return toolsSyncConfigBean;
        }
    }
}
