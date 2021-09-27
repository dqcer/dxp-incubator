package com.dqcer.integration.log.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * @author dongqin
 * @description spring上下文持有人
 * @date 2021/09/15
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;


    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }


    public static void clearHolder() {
        applicationContext = null;
    }


    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext == null) {
            return;
        }
        applicationContext.publishEvent(event);
    }

    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }
}