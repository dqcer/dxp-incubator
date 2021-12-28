package com.dqcer.integration.audit.annotation;


import com.dqcer.integration.audit.enums.TypeEnum;

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
     * 忽略键
     *
     * @return {@link String[]}
     */
    String[] ignore() default {"id"};

    /**
     * 关键
     *
     * @return {@link String[]}
     */
    String[] key() default {};

    /**
     * 忽略后缀
     *
     * @return {@link String[]}
     */
    String[] suffix() default {"Id"};


    /**
     * 类型
     *
     * @return {@link TypeEnum}
     */
    TypeEnum type() default TypeEnum.UPDATE;

    /**
     * 服务
     *
     * @return {@link Class<? extends BaseService>}
     */
    Class<? extends BaseService> service() default BaseService.class;
}
