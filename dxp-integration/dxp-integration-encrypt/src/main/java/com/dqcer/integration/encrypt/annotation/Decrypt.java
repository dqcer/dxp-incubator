package com.dqcer.integration.encrypt.annotation;


import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 解密
 * @date 2021/10/08 20:10:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {
}
