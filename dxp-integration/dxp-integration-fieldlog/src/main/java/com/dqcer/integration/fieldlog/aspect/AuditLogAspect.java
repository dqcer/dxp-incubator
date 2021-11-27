package com.dqcer.integration.fieldlog.aspect;


import com.dqcer.integration.fieldlog.annotation.AuditLog;
import com.dqcer.integration.fieldlog.enums.TypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author dongqin
 * @description 审计日志方面
 * @date 2021/11/18
 */
@Aspect
@Component
public class AuditLogAspect {

    @Around("@annotation(com.dqcer.integration.fieldlog.annotation.AuditLog)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        try {
            Object[] args = point.getArgs();
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            final AuditLog annotation = method.getAnnotation(AuditLog.class);
            TypeEnum type = annotation.type();
            result = point.proceed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
