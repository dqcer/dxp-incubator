package com.dqcer.integration.ds.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.dqcer.integration.ds.aspect.DataSourceAspect;
import com.dqcer.integration.ds.properties.DataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongqin
 * @description 自动配置
 * @date 2021/10/09
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class AutoConfiguration {


    /**
     * 调整 SqlSessionFactory 为 MyBatis-Plus 的 SqlSessionFactory
     *
     * @param dynamicDataSource 动态数据来源
     * @return {@link MybatisSqlSessionFactoryBean}
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(RoutingDataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(patternResolver
                .getResources("classpath*:mapper/**/*.xml"));
        mybatisSqlSessionFactoryBean.setDataSource(dynamicDataSource);
        GlobalConfig config = new GlobalConfig();
        config.setMetaObjectHandler(new MybatisMetaObjectHandlerConfig());
        mybatisSqlSessionFactoryBean.setGlobalConfig(config);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.addInterceptor(interceptor);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);

        return mybatisSqlSessionFactoryBean.getObject();
    }

    /**
     * 动态数据源切面处理
     *
     * @return {@link DataSourceAspect}
     */
    @Bean
    public DataSourceAspect dataSourceAspect() {
        return new DataSourceAspect();
    }

    /**
     * 通过getConnection()根据查找lookup key键对不同目标数据源的调用的实现
     *
     * @param dataSourceProperties 数据源属性
     * @return {@link RoutingDataSource}
     */
    @Bean
    public RoutingDataSource routingDataSource(DataSourceProperties dataSourceProperties) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        //  默认数据源
        DataSource dataSource = DataSourceBuilder.builder(dataSourceProperties);
        routingDataSource.setDefaultTargetDataSource(wrapDataSource(dataSource));
        //  其它数据源集
        routingDataSource.setTargetDataSources(multipleDataSources());
        return routingDataSource;
    }

    /**
     * 包装数据源
     * 将DataSource对象包装成第三方代理,处理分布式事务，比如用到seata，DataSource 包装成 DataSourceProxy
     *
     * @param dataSource 数据源
     * @return {@link DataSource}
     */
    private DataSource wrapDataSource(DataSource dataSource) {
        // TODO: 2021/10/9
        return dataSource;
    }

    /**
     * 多个数据源 DataSourceBuilder.builder(从配置文件/数据库加载其它数据源)
     *
     * @return {@link Map}
     */
    private Map<Object, Object> multipleDataSources() {
        // TODO: 2021/10/9
        return new HashMap<>(2);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(RoutingDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
