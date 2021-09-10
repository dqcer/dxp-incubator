package com.dqcer.integration;


import com.dqcer.integration.cache.RedissonAutoConfiguration;
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
@Import({RedissonAutoConfiguration.class})
public @interface EnableCache {
}
