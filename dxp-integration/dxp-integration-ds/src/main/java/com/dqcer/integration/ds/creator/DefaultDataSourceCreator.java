package com.dqcer.integration.ds.creator;

import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;

import javax.sql.DataSource;

/**
 * @author dongqin
 * @description 默认数据源的创造者
 * @date 2021/08/21 01:08:14
 */
public class DefaultDataSourceCreator {


    private HikariDataSourceCreator creators;


    public HikariDataSourceCreator getCreators() {
        return creators;
    }

    public void setCreators(HikariDataSourceCreator creators) {
        this.creators = creators;
    }

    public DataSource createDataSource(DynamicDataSourceProperties dataSourceProperty) {
        return creators.doCreateDataSource(dataSourceProperty);
    }
}
