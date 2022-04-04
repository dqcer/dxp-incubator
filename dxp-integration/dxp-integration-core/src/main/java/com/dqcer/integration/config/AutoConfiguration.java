package com.dqcer.integration.config;

import com.dqcer.integration.filter.HttpTraceLogFilter;
import com.dqcer.integration.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dqcer
 * @description 配置
 * @date  22:21 2021/4/28
 */
@Configuration
public class AutoConfiguration {

    /**
     * xss过滤器bean注册
     *
     * @return {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>(8);
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    /**
     * 跟踪日志过滤器bean注册
     *
     * @return {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean<HttpTraceLogFilter> traceLogFilterRegistrationBean() {
        FilterRegistrationBean<HttpTraceLogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new HttpTraceLogFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
