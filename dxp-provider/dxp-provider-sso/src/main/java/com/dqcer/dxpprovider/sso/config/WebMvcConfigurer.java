package com.dqcer.dxpprovider.sso.config;

import com.dqcer.integration.context.BaseInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description web mvc配置
 * @date 2021/08/17 23:08:07
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Resource
    LocaleChangeInterceptor localeChangeInterceptor;

    @Resource
    Validator validator;

    @Resource
    LocaleResolver customLocaleResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(new BaseInfoInterceptor());
    }

    @Override
    public Validator getValidator()  {
        return validator;
    }

    @Override
    public LocaleResolver localeResolver() {
        return customLocaleResolver;
    }

}
