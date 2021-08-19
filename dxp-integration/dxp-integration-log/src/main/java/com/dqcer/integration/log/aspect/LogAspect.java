package com.dqcer.integration.log.aspect;


import com.dqcer.dxptools.core.IpAddressUtil;
import com.dqcer.framework.storage.UserStorage;
import com.dqcer.integration.log.LogEvent;
import com.dqcer.integration.log.SpringContextHolder;
import com.dqcer.integration.log.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongqin
 * @description 日志 aop
 * @date 2021/08/19
 */
@Aspect
public class LogAspect {

    /**
     * 日志织入点
     */
    @Pointcut("@annotation(com.dqcer.integration.log.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        boolean isExecute = isExecute(joinPoint);
        if (isExecute) {
            HttpServletRequest request = getHttpServletRequest();
            String ipAddr = IpAddressUtil.getIpAddr(request);
            String requestURI = request.getRequestURI();
            String language = request.getLocale().toString();
            Map<String, String> map = new HashMap<>(8);
            map.put("ipAddr", ipAddr);
            map.put("requestURI", requestURI);
            map.put("language", language);
            map.put("username", UserStorage.getBox().getUsername());
            SpringContextHolder.publishEvent(new LogEvent(map));
        }


    }

    /**
     * 是否执行
     *
     * @param joinPoint 连接点
     * @return boolean
     */
    protected boolean isExecute(final JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (null != method) {
            Log annotation = method.getAnnotation(Log.class);
            if (null != annotation) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取HttpServletRequest
     *
     * @return {@link HttpServletRequest}
     */
    public HttpServletRequest getHttpServletRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        return requestAttributes.getRequest();
    }
}
