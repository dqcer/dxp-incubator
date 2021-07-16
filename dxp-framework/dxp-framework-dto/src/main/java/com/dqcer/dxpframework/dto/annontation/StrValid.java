package com.dqcer.dxpframework.dto.annontation;

import com.dqcer.dxpframework.dto.validator.StrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author dongqin
 * @description str验证
 * @date 2021/07/17 01:07:88
 */
@Documented
@Constraint(validatedBy = StrValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrValid {

    int min() default 0;

    int max() default 2147483647;

    String message() default "{org.hibernate.validator.constraints.Length.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
