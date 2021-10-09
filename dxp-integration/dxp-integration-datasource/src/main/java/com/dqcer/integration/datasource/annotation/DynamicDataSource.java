package com.dqcer.integration.datasource.annotation;


import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 动态数据源 todo 继承自定义处理 @Inherited
 * @date 2021/10/09
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicDataSource {

    /**
     * 指定的数据源，默认可不用加该注解
     *
     * @return {@link String}
     */
    String value();
}
