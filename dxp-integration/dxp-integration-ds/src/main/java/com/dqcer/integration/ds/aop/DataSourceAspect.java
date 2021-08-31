package com.dqcer.integration.ds.aop;


import com.dqcer.integration.ds.DS;
import com.dqcer.integration.ds.DynamicDataSourceContextHolder;
import com.dqcer.integration.ds.DynamicRoutingDataSource;
import com.dqcer.integration.ds.creator.DefaultDataSourceCreator;
import com.dqcer.integration.ds.provider.DataSourceProvider;
import com.dqcer.integration.ds.provider.JdbcDataSourceProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author dongqin
 * @description 数据来源切面处理
 * @date 2021/08/31
 */
@Aspect
@Order(1)
public class DataSourceAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    DynamicRoutingDataSource dynamicRoutingDataSource;

    @Resource
    DataSourceProvider dataSourceProvider;

    @Resource
    DefaultDataSourceCreator defaultDataSourceCreator;

    @Pointcut("@annotation(com.dqcer.integration.ds.DS)")
    public void dataSourcePointcut() {
    }

    @After("dataSourcePointcut()")
    public void closeDataSource(JoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            log.info("关闭数据源");
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(DS.class)) {
            DS idempotent = method.getAnnotation(DS.class);
            String resolver = resolver(idempotent, joinPoint);

            if (!"master".equals(resolver)) {
                Map<String, DataSource> dataSources = dynamicRoutingDataSource.getDataSources();
                DataSource dataSource = dataSources.get(resolver);
                dynamicRoutingDataSource.closeDataSource(resolver, dataSource);
            }
            DynamicDataSourceContextHolder.poll();
        }
    }

    @Before("dataSourcePointcut()")
    public void switchDataSource(JoinPoint joinPoint) {

        if (log.isInfoEnabled()) {
            log.info("切换数据源，当前数据源 {}", DynamicDataSourceContextHolder.peek());
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(DS.class)) {
            Map<String, javax.sql.DataSource> map = dataSourceProvider.loadDataSources();
            DynamicDataSourceContextHolder.push("master");
        } else {
            DS idempotent = method.getAnnotation(DS.class);
            String resolver = resolver(idempotent, joinPoint);

            JdbcDataSourceProvider jdbcDataSourceProvider = new JdbcDataSourceProvider(dynamicRoutingDataSource, defaultDataSourceCreator);
            Map<String, DataSource> map = jdbcDataSourceProvider.loadDataSources();
            DataSource dataSource = map.get(resolver);

            if (null == dataSource) {
                throw new RuntimeException(MessageFormat.format("未找到对应的数据源,请检查. key: {0}", resolver));
            }
            DynamicDataSourceContextHolder.push(resolver);
            dynamicRoutingDataSource.addDataSource(resolver, dataSource);
        }

    }



    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    public String resolver(DS idempotent, JoinPoint point) {
        Object[] arguments = point.getArgs();
        String[] params = DISCOVERER.getParameterNames(getMethod(point));
        StandardEvaluationContext context = new StandardEvaluationContext();

        assert params != null;
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }

        Expression expression = PARSER.parseExpression(idempotent.value());
        return expression.getValue(context, String.class);
    }

    /**
     * 根据切点解析方法信息
     * @param joinPoint 切点信息
     * @return Method 原信息
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
                        method.getParameterTypes());
            }
            catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }

}
