package com.dqcer.integration.ds;


import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 多数据源注解
 * @date 2021/08/31
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    String value();
}
