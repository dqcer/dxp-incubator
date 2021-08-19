package com.dqcer.integration.idempotent.aspect;

/**
 * @author lengleng
 * @date 2020/9/25
 */

import com.dqcer.integration.idempotent.annotation.ApiIdempotent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author dongqin
 * @description 表达式解析器 默认key 抽取， 优先根据 spel 处理
 * @date 2021/08/19
 */
public class ExpressionResolver implements KeyResolver {

	private static final SpelExpressionParser PARSER = new SpelExpressionParser();

	private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

	@Override
	public String resolver(ApiIdempotent idempotent, JoinPoint point) {
		Object[] arguments = point.getArgs();
		String[] params = DISCOVERER.getParameterNames(getMethod(point));
		StandardEvaluationContext context = new StandardEvaluationContext();

		for (int len = 0; len < params.length; len++) {
			context.setVariable(params[len], arguments[len]);
		}

		Expression expression = PARSER.parseExpression(idempotent.key());
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