package com.dqcer.integration.log;

import com.dqcer.integration.log.aspect.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author dongqin
 * @description 日志自动配置
 * @date 2021/08/19
 */
@EnableAsync
@Configuration
public class LogAutoConfiguration {

    @Bean
    public LogListener logListener() {
        return new LogListener();
    }

    @Order(1)
    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}