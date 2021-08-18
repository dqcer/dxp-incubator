package com.dqcer.integration.log.annotation;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 日志记录注解
 * @date 2021/08/18 22:08:28
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
