package com.dqcer.integration.datasource.aspect;

import com.dqcer.integration.datasource.annotation.DynamicDataSource;
import com.dqcer.integration.datasource.config.DynamicContextHolder;
import com.dqcer.integration.datasource.config.RoutingDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Deque;

/**
 * @author dongqin
 * @description 数据来源aop
 * @date 2021/10/09
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class DataSourceAspect {

    @Resource
    private RoutingDataSource routingDataSource;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.dqcer.integration.datasource.annotation.DynamicDataSource) ")
    public void dataSourcePointCut() {
        // 切点
    }

    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            log.debug("切换前数据源，当前数据源：{}", DynamicContextHolder.peek());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DynamicDataSource annotation = method.getAnnotation(DynamicDataSource.class);
        if (null != annotation) {
            String value = annotation.value();
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException("没有对应的数据源key");
            }
            Deque<String> deque = DynamicContextHolder.getAll();
            boolean contains = deque.contains(value);
            if (!contains) {
                log.info("动态添加数据源 key: {}", value);
                // TODO 加载数据库中的数据连接信息
                // TODO routingDataSource.createDataSource(null);
            }
            DynamicContextHolder.push(value);
        }
        if (log.isDebugEnabled()) {
            log.debug("切换后数据源，当前数据源：{}", DynamicContextHolder.peek());
        }
    }

    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint) {
        cleanDataSource(joinPoint);
    }

    @AfterThrowing("dataSourcePointCut()")
    public void afterThrowing(JoinPoint joinPoint) {
        cleanDataSource(joinPoint);
    }

    private void cleanDataSource(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            log.debug("清空当前线程数据源：{}", DynamicContextHolder.peek());
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DynamicDataSource annotation = method.getAnnotation(DynamicDataSource.class);
        if (null != annotation) {
            DynamicContextHolder.poll();
        }
    }

}
