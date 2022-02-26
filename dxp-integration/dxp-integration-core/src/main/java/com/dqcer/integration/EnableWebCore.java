package com.dqcer.integration;


import com.dqcer.integration.advice.ExceptionAdvice;
import com.dqcer.integration.advice.LogAdvice;
import com.dqcer.integration.config.AutoConfiguration;
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
@Import({ExceptionAdvice.class, LogAdvice.class, AutoConfiguration.class})
public @interface EnableWebCore {
}
