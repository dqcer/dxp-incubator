package com.dqcer.integration.ds.config;

import com.dqcer.integration.ds.properties.DataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author dongqin
 * @description 数据源建造
 * @date 2021/10/09
 */
public class DataSourceBuilder {

    private static final String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";

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

    /**
     * 构建器
     *
     * @param dataSourceProperty 数据源属性
     * @return {@link DataSource}
     */
    public static DataSource builder(DataSourceProperties dataSourceProperty) {
        HikariConfig config = new HikariConfig();
        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.setDriverClassName(dataSourceProperty.getDriverClassName());
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
