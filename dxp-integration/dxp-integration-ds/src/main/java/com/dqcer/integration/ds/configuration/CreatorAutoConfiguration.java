package com.dqcer.integration.ds.configuration;


import com.dqcer.integration.ds.creator.DefaultDataSourceCreator;
import com.dqcer.integration.ds.creator.HikariDataSourceCreator;
import com.dqcer.integration.ds.properties.DynamicDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class CreatorAutoConfiguration {

    private final DynamicDataSourceProperties properties;

    public CreatorAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }


    @Bean
    @Order(3000)
    @ConditionalOnMissingBean
    public HikariDataSourceCreator hikariDataSourceCreator() {
        return new HikariDataSourceCreator(properties);
    }

    @Primary
    @Bean
    @ConditionalOnMissingBean
    public DefaultDataSourceCreator defaultDataSourceCreator(HikariDataSourceCreator hikariDataSourceCreator) {
        DefaultDataSourceCreator defaultDataSourceCreator = new DefaultDataSourceCreator();
        defaultDataSourceCreator.setCreators(hikariDataSourceCreator);
        return defaultDataSourceCreator;
    }


}
