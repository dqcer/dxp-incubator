package com.dqcer.integration.ds.creator;

import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariDataSourceCreator {

    private final static String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";

    static {
        try {
            Class.forName(HIKARI_DATASOURCE);
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        }
    }

     final DynamicDataSourceProperties dynamicDataSourceProperties;

    public HikariDataSourceCreator(DynamicDataSourceProperties properties) {
        this.dynamicDataSourceProperties = properties;
    }


    public DataSource doCreateDataSource(DynamicDataSourceProperties dataSourceProperty) {
        HikariConfig config = new HikariConfig();
        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.validate();
        return new HikariDataSource(config);
    }
}
