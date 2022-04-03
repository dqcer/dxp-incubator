package com.dqcer.framework.base.annotation;

import java.lang.annotation.*;

/**
 * @author dqcer
 * @description 授权注解
 * @DATE 22:21 2021/4/28
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {

}
