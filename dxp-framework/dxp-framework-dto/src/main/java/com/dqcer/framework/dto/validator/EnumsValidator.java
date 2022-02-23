package com.dqcer.framework.dto.validator;

import com.dqcer.framework.dto.annontaion.EnumsValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * @author dongqin
 * @description 枚举字符串验证器
 * @date 2022/01/11
 */
public class EnumsValidator implements ConstraintValidator<EnumsValid, Integer> {

    private Class<? extends Enum> enumClass;

    private static final String METHOD_NAME = "toEnum";

    @Override
    public void initialize(EnumsValid annotation) {
        enumClass = annotation.value();
        try {
            enumClass.getDeclaredMethod(METHOD_NAME, int.class);
        } catch (NoSuchMethodException e){
            throw new IllegalArgumentException("the enum class has not toEnum() method", e);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        Method declareMethod;
        try {
            declareMethod = enumClass.getDeclaredMethod(METHOD_NAME, int.class);
        }catch (NoSuchMethodException e){
            return false;
        }
        try {
            declareMethod.invoke(null, value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
