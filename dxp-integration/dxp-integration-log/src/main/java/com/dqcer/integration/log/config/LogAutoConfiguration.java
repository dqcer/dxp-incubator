package com.dqcer.integration.log.config;

import com.dqcer.integration.log.aspect.OperationLogAspect;
import com.dqcer.integration.log.feign.RemoteLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * @author dongqin
 * @description 日志自动配置
 * @date 2021/08/19
 */
@ConditionalOnWebApplication
@EnableAsync
@Configuration
public class LogAutoConfiguration {

    private final RemoteLogService remoteLogService;

    public LogAutoConfiguration(RemoteLogService remoteLogService) {
        this.remoteLogService = remoteLogService;
    }


    @Bean
    public OperationLogAspect logAspect() {
        return new OperationLogAspect();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public LogListener logListener() {
        return new LogListener(remoteLogService);
    }

    @Bean(name = "remoteRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}