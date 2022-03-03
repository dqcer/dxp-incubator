package com.dqcer.dxpprovider.sso.config;

import com.dqcer.integration.interceptor.BaseInfoInterceptor;
import com.dqcer.integration.cache.operation.RedissonObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description web mvc配置
 * @date 2021/08/17 23:08:07
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Resource
    private RedissonObject redissonObject;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseInfoInterceptor(redissonObject));
    }

}
