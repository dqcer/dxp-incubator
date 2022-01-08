package com.dqcer.integration.datasource.config;

import com.dqcer.integration.datasource.properties.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.Map;

/**
 * @author dongqin
 * @description 路由数据来源
 * @date 2021/10/09
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> targetDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.peek();
    }

    public void createDataSource(String key, String driveClass, String url, String username, String password) {
        try {
            Class.forName(driveClass);
            DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new IllegalArgumentException("连接异常");
        }
        DataSourceProperties properties = new DataSourceProperties();
        properties.setDriverClassName(driveClass);
        properties.setDsName(key);
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setUrl(url);
        DataSource dataSource = DataSourceBuilder.builder(properties);
        Map<Object, Object> dynamicTargetDataSources2 = this.targetDataSources;
        dynamicTargetDataSources2.put(key, dataSource);
        setTargetDataSources(dynamicTargetDataSources2);
        //  刷新处理
        super.afterPropertiesSet();

    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.targetDataSources = targetDataSources;
    }

    public Map<Object, Object> getTargetDataSources() {
        return targetDataSources;
    }
}
