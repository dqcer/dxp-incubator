package com.dqcer.integration.cache.config;

import com.dqcer.integration.cache.operation.CacheChannel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author dongqin
 * @description redisson汽车配置
 * @date 2021/09/10
 */
@Configuration
public class RedissonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CacheChannel.class)
    public CacheChannel getRedissonObject() {
        return new CacheChannel();
    }

}
