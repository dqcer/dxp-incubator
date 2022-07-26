package com.dqcer.framework.base.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {

    /**
     * 模块权限效验
     *
     * @return {@link String}
     */
    String code() default "";

}
