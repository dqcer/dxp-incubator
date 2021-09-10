package com.dqcer.integration.cache;

import com.dqcer.integration.operation.RedissonObject;
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
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject getRedissonObject() {
        return new RedissonObject();
    }

}
