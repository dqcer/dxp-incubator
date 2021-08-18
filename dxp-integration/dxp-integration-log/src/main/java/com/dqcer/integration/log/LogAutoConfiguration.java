package com.dqcer.integration.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class LogAutoConfiguration {

    @Bean
    public LogListener sysOperLogListener()
    {
        return new LogListener();
    }

}