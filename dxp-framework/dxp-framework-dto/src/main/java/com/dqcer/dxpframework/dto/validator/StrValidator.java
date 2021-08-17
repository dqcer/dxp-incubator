package com.dqcer.dxpframework.dto.validator;

import com.dqcer.dxpframework.dto.annontation.StrValid;
import com.dqcer.dxptools.core.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author dongqin
 * @description str验证器
 * @date 2021/07/17 01:07:83
 */
public class StrValidator implements ConstraintValidator<StrValid, String> {

    private int min;

    private int max;

    @Override
    public void initialize(StrValid annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        int length = value.trim().length();
        if (length >= min && length <= max) {
            return true;
        }
        return false;
    }
}
