package com.dqcer.dxpframework.dto.annontation;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description valid DTO
 * @date 0:41 2021/5/25
 */
@Deprecated
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidDTO {

}
