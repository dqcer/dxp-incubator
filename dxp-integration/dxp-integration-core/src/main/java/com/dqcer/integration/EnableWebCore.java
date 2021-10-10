package com.dqcer.integration;


import com.dqcer.integration.configuration.AutoConfiguration;
import com.dqcer.integration.configuration.LanguageConfiguration;
import com.dqcer.integration.handler.GlobalExceptionHandler;
import com.dqcer.integration.handler.GlobalResponseAdvice;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description web core
 * @date 2021/10/05 00:10:22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GlobalExceptionHandler.class, GlobalResponseAdvice.class, LanguageConfiguration.class, AutoConfiguration.class})
public @interface EnableWebCore {
}
