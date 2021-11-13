package com.dqcer.integration.handler;

import com.dqcer.framework.storage.*;
import com.dqcer.integration.log.config.LogEvent;
import com.dqcer.integration.log.config.SpringContextHolder;
import com.dqcer.integration.log.config.SysLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author dongqin
 * @description 全局响应处理
 * @date 2021/10/04 23:10:42
 */
@Order(AdviceOrderConstant.LOG_ORDER)
@RestControllerAdvice
public class LogAdvice implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (log.isDebugEnabled()) {
            log.debug("log 拦截", object, methodParameter);
        }


        UnifySession unifySession = UserStorage.getSession();
        UnifyParameter unifyParameter = QueryStorage.getParameter();

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        RequestStorage.REQUEST.set(request);
        String requestUri = request.getRequestURI();

        SysLog sysLog = new SysLog();
        sysLog.setRequestUrl(requestUri);
        sysLog.setUserSessionStr(unifySession.toString());
        sysLog.setIp(unifySession.getIp());
        sysLog.setLanguage(unifySession.getLanguage());
        sysLog.setStartTime(unifyParameter.getStartTime());
        sysLog.setParam(unifyParameter.getParameter());
        sysLog.setResult(object.toString());
        LocalDateTime now = LocalDateTime.now();
        sysLog.setEndTime(now);

        //  子线程共享父线程
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        SpringContextHolder.publishEvent(new LogEvent(sysLog));


        Duration between = Duration.between(unifyParameter.getStartTime(), now);
        log.debug("耗时：{}", between.toMillis());
        return object;

    }


}
