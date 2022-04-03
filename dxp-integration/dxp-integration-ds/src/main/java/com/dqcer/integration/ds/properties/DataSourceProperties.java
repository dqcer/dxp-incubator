package com.dqcer.integration.ds.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author dongqin
 * @description 动态数据源属性
 * @date 2021/08/31
 */
@ConfigurationProperties(prefix = "spring.ds")
public class DataSourceProperties {


    /**
     * 默认的key
     */
    private String defaultName = "master";

    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

    /**
     * 租户sql
     */
    private String tenantSql;

    public String getTenantSql() {
        return tenantSql;
    }

    public void setTenantSql(String tenantSql) {
        this.tenantSql = tenantSql;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
