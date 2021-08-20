package com.dqcer.integration.ds;

import com.dqcer.integration.ds.provider.PropertiesDataSourceProvider;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    @Resource
    private PropertiesDataSourceProvider providers;

    @Override
    protected Object determineCurrentLookupKey() {
        // TODO: 2021/8/20 赖加载
        return determinePrimaryDataSource();
    }


    private DataSource determinePrimaryDataSource() {
        log.debug("dynamic-datasource switch to the primary datasource");
        DataSource dataSource = dataSourceMap.get("master");
        if (dataSource != null) {
            return dataSource;
        }
        throw new RuntimeException("dynamic-datasource can not find primary datasource");
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, DataSource> dataSource = providers.loadDataSources();


        for (Map.Entry<String, DataSource> dsItem : dataSource.entrySet()) {
            String key = dsItem.getKey();
            synchronized (this) {
                DataSource value = dsItem.getValue();
                DataSource oldDataSource = dataSourceMap.put(key, value);
                if (log.isInfoEnabled()) {
                        HikariDataSource hikariDataSource = (HikariDataSource) value;
                        log.info("Dynamic datasource add success key: {}  jdbc-url:{}", key, hikariDataSource.getJdbcUrl());
                }

                // 关闭老的数据源
                if (oldDataSource != null) {
                    closeDataSource(key, oldDataSource);
                }
            }
        }
    }


    /**
     * close db
     *
     * @param ds         dsName
     * @param dataSource db
     */
    private void closeDataSource(String ds, DataSource dataSource) {
        try {
            Class<? extends DataSource> clazz = dataSource.getClass();
            Method closeMethod = clazz.getDeclaredMethod("close");
            closeMethod.invoke(dataSource);

            if (log.isInfoEnabled()) {
                log.info("Dynamic datasource close: {}", ds);
            }

        } catch (Exception e) {
            log.error("Dynamic datasource closed failed :  {}", ds, e);
        }
    }

}
