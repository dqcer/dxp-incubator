package com.dqcer.integration.log.annotation;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 操作日志
 * @date 2021/09/15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {


    /**
     * 模块
     *
     * @return {@link String}
     */
    String module();

}
