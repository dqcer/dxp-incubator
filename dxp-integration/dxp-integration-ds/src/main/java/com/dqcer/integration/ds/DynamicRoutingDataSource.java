package com.dqcer.integration.ds;

import com.dqcer.integration.ds.provider.PropertiesDataSourceProvider;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongqin
 * @description 动态数据源
 * @date 2021/08/31
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    @Resource
    private PropertiesDataSourceProvider providers;

    public Map<String, DataSource> getDataSources() {
        return dataSourceMap;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (log.isInfoEnabled()) {
            log.info("获取数据源...");
        }
        String peek = DynamicDataSourceContextHolder.peek();
        if (null == peek || peek.trim().length() == 0) {
            return determinePrimaryDataSource();
        }
        return dataSourceMap.get(peek);
    }


    private DataSource determinePrimaryDataSource() {
        DataSource dataSource = dataSourceMap.get("master");
        if (dataSource != null) {
            DynamicDataSourceContextHolder.push("master");
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
                DataSource oldDataSource = addDataSource(key, value);

                // 关闭老的数据源
                if (oldDataSource != null) {
                    closeDataSource(key, oldDataSource);
                }
            }
        }
    }

    @Override
    public void destroy() {
        log.info("dynamic-datasource start closing ....");
        for (Map.Entry<String, DataSource> item : dataSourceMap.entrySet()) {
            closeDataSource(item.getKey(), item.getValue());
        }
        log.info("dynamic-datasource all closed success,bye");
    }

    public DataSource addDataSource(String key, DataSource value) {
        DataSource oldDataSource = dataSourceMap.put(key, value);
        if (log.isInfoEnabled()) {
                HikariDataSource hikariDataSource = (HikariDataSource) value;
                log.info("Add datasource : {}  jdbc-url:{}", key, hikariDataSource.getJdbcUrl());
        }
        return oldDataSource;
    }


    /**
     * close db
     *
     * @param ds         dsName
     * @param dataSource db
     */
    public void closeDataSource(String ds, DataSource dataSource) {
        try {
            Class<? extends DataSource> clazz = dataSource.getClass();
            Method closeMethod = clazz.getDeclaredMethod("close");
            closeMethod.invoke(dataSource);

            if (log.isInfoEnabled()) {
                log.info("Close datasource : {}", ds);
            }

        } catch (Exception e) {
            log.error("Dynamic datasource closed failed :  {}", ds, e);
        }
    }

}
