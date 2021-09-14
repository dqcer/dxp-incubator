package com.dqcer.gateway;

import com.dqcer.gateway.filter.AuthFilter;
import com.dqcer.gateway.filter.OpenApiFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebExceptionHandler;

/**
 * @author dongqin
 * @description 自动配置的配置
 * @date 2021/09/13
 */
@Configuration
public class AutoConfiguration {

    @Bean
    public GlobalFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public GlobalFilter openApiFilter() {
        return new OpenApiFilter();
    }

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new ExceptionHandler();
    }
}
