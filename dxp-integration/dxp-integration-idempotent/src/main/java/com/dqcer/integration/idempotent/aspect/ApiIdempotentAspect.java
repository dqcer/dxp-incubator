package com.dqcer.integration.idempotent.aspect;

import com.dqcer.integration.idempotent.annotation.ApiIdempotent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author dongqin
 * @description
 * @date 20:24 2021/5/13
 */
@Aspect
@Component
public class ApiIdempotentAspect {


    private static final Logger LOGGER = LoggerFactory.getLogger(ApiIdempotentAspect.class);

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();

    private static final String API_IDEMPOTENT = "api_idempotent";

    private static final String KEY = "key";

    private static final String DEL_KEY = "delKey";

    @Autowired
    private Redisson redisson;

    @Autowired
    private KeyResolver keyResolver;

    @Pointcut("@annotation(com.dqcer.integration.idempotent.annotation.ApiIdempotent)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void beforePointCut(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(ApiIdempotent.class)) {
            return;
        }

        ApiIdempotent idempotent = method.getAnnotation(ApiIdempotent.class);

        String key = getKey(idempotent, request, joinPoint);

        long expireTime = idempotent.expires();
        boolean delKey = idempotent.delKey();

        RMapCache<String, Object> rMapCache = redisson.getMapCache(API_IDEMPOTENT);
        String value = LocalDateTime.now().toString().replace("T", " ");
        Object object;
        if (null != rMapCache.get(key)) {
            throw new IdempotentException();
        }

        synchronized (this) {
            object = rMapCache.putIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            if (null != object) {
                throw new IdempotentException();
            } else {
                LOGGER.info("[idempotent]:has stored key={},value={},expireTime={}{},now={}", key, value, expireTime, TimeUnit.SECONDS, LocalDateTime.now().toString());
            }
        }

        Map<String, Object> map = CollectionUtils.isEmpty(threadLocal.get()) ? new HashMap<>(4) : threadLocal.get();
        map.put(KEY, key);
        map.put(DEL_KEY, delKey);
        threadLocal.set(map);

    }

    private String getKey(ApiIdempotent idempotent, HttpServletRequest request, JoinPoint joinPoint) {
        String key = idempotent.key();

        //  url + 参数
        if (key != null && key.trim().length() > 0) {
            String url = request.getRequestURL().toString();
            String argString = Arrays.asList(joinPoint.getArgs()).toString();
            key = url + argString;
        } else {
            key = keyResolver.resolver(idempotent, joinPoint);
        }

        String aid = request.getHeader("aid");
        if (key != null && key.length() > 0) {
            key = aid + key;
        }

        return key;
    }

    @After("pointCut()")
    public void afterPointCut(JoinPoint joinPoint) {
        Map<String, Object> map = threadLocal.get();
        if (CollectionUtils.isEmpty(map)) {
            return;
        }

        RMapCache<Object, Object> mapCache = redisson.getMapCache(API_IDEMPOTENT);
        if (mapCache.size() == 0) {
            return;
        }

        String key = map.get(KEY).toString();
        boolean delKey = (boolean) map.get(DEL_KEY);

        if (delKey) {
            mapCache.fastRemove(key);
            LOGGER.info("[idempotent]:has removed key={}", key);
        }
        threadLocal.remove();
    }



}
