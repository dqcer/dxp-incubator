package com.dqcer.integration.idempotent.annotation;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description
 * @date 20:16 2021/5/13
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {

    /**
     * 参数 key
     * @return
     */
    String key() default "";

    int expires() default 60;

    boolean delKey() default true;

}
