package com.dqcer.integration.ds.creator;

import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author dongqin
 * @description hikari数据源的创造者
 * @date 2021/08/31
 */
public class HikariDataSourceCreator {

    private final static String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";

    private static Method configCopyMethod = null;

    static {
        try {
            Class.forName(HIKARI_DATASOURCE);
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        }
        try {
            configCopyMethod = HikariConfig.class.getMethod("copyStateTo", HikariConfig.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
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
        HikariDataSource source = new HikariDataSource();
        try {
            configCopyMethod.invoke(config, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return source;
    }
}
