package com.dqcer.dxpframework.dto.aspect;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpframework.dto.support.Validation;
import com.dqcer.dxpframework.dto.util.BeanValidationResult;
import com.dqcer.dxpframework.dto.util.ValidationUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongqin
 * @description valid aspect
 * @date 0:38 2021/5/25
 */
@Deprecated
@Aspect
@Order(1)
@Component
public class ValidAspect {

    @Pointcut("within(@com.dqcer.dxpframework.dto.annontation.ValidDTO *) ")
    public void validDTOMethod() {
    }



    @Pointcut("execution(* *(@com.dqcer.dxpframework.dto.annontation.ValidDTO (*) ))")
    public void validDTO1() {
    }

    @Around("validDTO1() || validDTOMethod()")
    public Object valid(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Validation) {
                BeanValidationResult beanValidationResult = ValidationUtil.warpValidate(arg);
                List<BeanValidationResult.ErrorMessage> errorMessages = beanValidationResult.getErrorMessages();
                if (errorMessages.size() > 0) {
                    String msg = "";
                    for (BeanValidationResult.ErrorMessage errorMessage : errorMessages) {
                        msg += String.format("%s : %s ==> [ %s ]", errorMessage.getPropertyName(), errorMessage.getValue(), errorMessage.getMessage());
                    }
                    return ResultApi.error(msg);
                }
            }
        }
        return joinPoint.proceed();
    }

}
