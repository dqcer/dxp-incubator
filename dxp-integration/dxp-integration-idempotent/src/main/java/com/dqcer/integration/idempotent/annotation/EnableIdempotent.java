package com.dqcer.integration.idempotent.annotation;

import com.dqcer.integration.idempotent.configuration.IdempotentAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description 开启幂等功能
 * @date 2021/08/21 19:08:56
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({IdempotentAutoConfiguration.class})
public @interface EnableIdempotent {
}
