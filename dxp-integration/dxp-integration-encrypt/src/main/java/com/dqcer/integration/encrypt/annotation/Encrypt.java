package com.dqcer.integration.encrypt.annotation;


import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 加密
 * @date 2021/10/08 20:10:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
}
