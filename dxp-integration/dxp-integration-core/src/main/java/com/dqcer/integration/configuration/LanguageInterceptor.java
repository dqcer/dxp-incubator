package com.dqcer.integration.configuration;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongqin
 * @description 语言拦截器
 * @date 2021/08/18 00:08:96
 */
public class LanguageInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String locale = request.getParameter(getParamName());
        if (locale != null) {
            return super.preHandle(request, response, handler);
        }

        locale = request.getHeader("language");
        if (locale != null) {
            locale = "zh_CN";
        }
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver == null) {
            throw new IllegalStateException("language not find");
        }

        try {
            assert locale != null;
            localeResolver.setLocale(request, response, parseLocaleValue(locale));
        } catch (IllegalArgumentException ex) {
            if (isIgnoreInvalidLocale()) {
                this.logger.error("Ignoring invalid locale value [" + locale + "]: " + ex.getMessage());
            } else {
                throw ex;
            }
        }

        return true;
    }

}
