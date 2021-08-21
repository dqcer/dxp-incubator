package com.dqcer.integration.ds.configuration;


import com.dqcer.integration.ds.DynamicRoutingDataSource;
import com.dqcer.integration.ds.aop.DataSourceAspect;
import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;
import com.dqcer.integration.ds.provider.DataSourceProvider;
import com.dqcer.integration.ds.provider.PropertiesDataSourceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongqin
 * @description 自动配置
 * @date 2021/08/21 01:08:55
 */
@Configuration
@Import(CreatorAutoConfiguration.class)
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class AutoConfiguration {

    private final DynamicDataSourceProperties properties;

    public AutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }


    /**
     * 属性数据来源提供者
     *
     * @return {@link DataSourceProvider}
     */
    @Bean
    public DataSourceProvider propertiesDataSourceProvider() {
        Map<String, DynamicDataSourceProperties> map = new HashMap<>(2);
        map.put("master", properties);
        return new PropertiesDataSourceProvider(map);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        // TODO: 2021/8/20 负载均衡
        return new DynamicRoutingDataSource();
    }

    @Bean
    public DataSourceAspect dataSourceAspect() {
        return new DataSourceAspect();
    }

}
