package com.dqcer.framework.dto.annontaion;

import com.dqcer.framework.dto.validator.EnumsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author dongqin
 * @description 状态效验注解
 * @date 2021/12/20
 */
@Documented
@Constraint(validatedBy = EnumsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumsValid {

    String message() default "值不在枚举值中";

    Class<?>[] groups() default {};

    Class<? extends Enum> value();

    Class<? extends Payload>[] payload() default {};
}
