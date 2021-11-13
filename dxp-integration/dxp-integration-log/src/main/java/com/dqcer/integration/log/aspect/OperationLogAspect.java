package com.dqcer.integration.log.aspect;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.dqcer.framework.storage.UnifySession;
import com.dqcer.framework.storage.UserStorage;
import com.dqcer.integration.log.annotation.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongqin
 * @description 日志方面
 * @date 2021/09/15
 */
@Aspect
public class OperationLogAspect implements Ordered {

    @Pointcut("@annotation(com.dqcer.integration.log.annotation.OperationLog)")
    public void logPointCut() {
        // 日志织入点
    }

   // @Around("logPointCut()")
    public Object doAfterReturning(ProceedingJoinPoint joinPoint) throws Throwable {
        UnifySession unifySession = UserStorage.getSession();

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

//        SysLogContextHolder.LOG_REQUESTS_THREAD_LOCAL.set(request);
        Long accountId = unifySession.getAccountId();
        String requestUri = request.getRequestURI();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);


        Object[] args = joinPoint.getArgs();
        StringBuilder key = new StringBuilder();

        if (ObjectUtil.isNotEmpty(args)) {
            for (Object arg : args) {
                if (ObjectUtil.isNotEmpty(arg)) {
                    boolean execute = isExecute(arg);
                    if (execute) {

                        if (arg instanceof MultipartFile) {
                            MultipartFile multipartFile = (MultipartFile) arg;
                            Map<String, Object> map = new HashMap<>(8);
                            map.put("fileName", multipartFile.getOriginalFilename());
                            map.put("size", multipartFile.getSize());

                            JSON parse = JSONUtil.parse(map);
                            String stringPretty = parse.toString();
                            key.append(stringPretty);

                        } else {
                            JSON parse = JSONUtil.parse(arg);
                            String stringPretty = parse.toString();
                            key.append(stringPretty);

                        }
                    }
                }
            }
        }

        Object proceed = joinPoint.proceed();

//        SysLog sysLog = new SysLog();
//        sysLog.setCreatedBy(accountId);
//        sysLog.setCreatedTime(new Date());
//        sysLog.setRequestUrl(requestUri);
//        sysLog.setParam(key.toString());
//        if (proceed instanceof ResultApi) {
//            ResultApi result = (ResultApi) proceed;
//            sysLog.setResult(result.toString());
//        } else {
//            // TODO: 2021/11/13 IO流处理
//        }
//
//        sysLog.setIp(unifySession.getIp());
//        sysLog.setLanguage(unifySession.getLanguage());
//        sysLog.setModule(operationLog.module());
//
//        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
//        SpringContextHolder.publishEvent(new LogEvent(sysLog));

        return proceed;

    }

    /**
     * 是否执行
     *
     * @param object 对象
     * @return boolean
     */
    public boolean isExecute(Object object) {
        if (object instanceof HttpServletRequest) {
            return false;
        }
        return !(object instanceof HttpServletResponse);
    }


    @Override
    public int getOrder() {
        return -200;
    }
}
