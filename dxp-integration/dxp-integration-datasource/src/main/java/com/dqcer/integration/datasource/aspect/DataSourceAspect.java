package com.dqcer.integration.datasource.aspect;

import com.dqcer.integration.datasource.annotation.DynamicDataSource;
import com.dqcer.integration.datasource.config.DynamicContextHolder;
import com.dqcer.integration.datasource.config.RoutingDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

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
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Pointcut("@annotation(com.dqcer.integration.datasource.annotation.DynamicDataSource) ")
    public void dataSourcePointCut() {
        // 切点
    }

    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint) {
        log.debug("切换前数据源，当前数据源：{}", DynamicContextHolder.peek());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DynamicDataSource annotation = method.getAnnotation(DynamicDataSource.class);
        if (null != annotation) {
            String value = calculateValue(annotation.value(),method, joinPoint.getArgs());
            if (value == null || value.trim().length() == 0) {
                throw new IllegalArgumentException("没有对应的数据源key");
            }
            Deque<String> deque = DynamicContextHolder.getAll();
            boolean contains = deque.contains(value);
            if (!contains) {
                log.debug("动态添加数据源 key: {}", value);
            }
            DynamicContextHolder.push(value);
        }
        log.debug("切换后数据源，当前数据源：{}", DynamicContextHolder.peek());
    }

    /**
     * spel表达式通过方法入参计算数据源名称
     * @param annoValue
     * @param method
     * @param arguments
     * @return
     */
    private String calculateValue(String annoValue, Method method, Object[] arguments) {
        if(!annoValue.startsWith("#")) {
            return annoValue;
        }
        EvaluationContext context = new MethodBasedEvaluationContext(null, method, arguments, NAME_DISCOVERER);
        Object value = PARSER.parseExpression(annoValue).getValue(context);
        return value == null ? null : value.toString();
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
            DynamicContextHolder.clear();
        }
    }

}
