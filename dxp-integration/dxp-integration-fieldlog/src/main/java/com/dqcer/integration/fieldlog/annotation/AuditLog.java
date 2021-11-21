package com.dqcer.integration.fieldlog.annotation;

import com.dqcer.integration.fieldlog.enums.TypeEnum;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 审计日志
 * @date 2021/11/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 索引
     *
     * @return {@link String}
     */
    String index();

    /**
     * 操作类型
     *
     * @return {@link TypeEnum}
     */
    TypeEnum type() default TypeEnum.UPDATE;

    Class<? extends BaseService> service() default BaseService.class;
}
