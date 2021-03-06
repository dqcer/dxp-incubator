package com.dqcer.integration.cache;


import com.dqcer.integration.cache.config.RedissonAutoConfiguration;
import com.dqcer.integration.cache.operation.CaffeineCache;
import com.dqcer.integration.cache.operation.RedissonCache;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description 启用缓存
 * @date 2021/09/10
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({RedissonAutoConfiguration.class, RedissonCache.class, CaffeineCache.class})
public @interface EnableCache {
}
