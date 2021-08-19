package com.dqcer.integration.idempotent.configuration;

import com.dqcer.integration.idempotent.aspect.ApiIdempotentAspect;
import com.dqcer.integration.idempotent.aspect.ExpressionResolver;
import com.dqcer.integration.idempotent.aspect.KeyResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class IdempotentAutoConfiguration {

	/**
	 * api幂等方面
	 * @return Aspect
	 */
	@Bean
	public ApiIdempotentAspect apiIdempotentAspect() {
		return new ApiIdempotentAspect();
	}

	/**
	 * key 解析器
	 * @return KeyResolver
	 */
	@Bean
	@ConditionalOnMissingBean(KeyResolver.class)
	public KeyResolver keyResolver() {
		return new ExpressionResolver();
	}

}