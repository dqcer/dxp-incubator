package com.dqcer.integration.idempotent.aspect;

import com.dqcer.integration.idempotent.annotation.ApiIdempotent;
import org.aspectj.lang.JoinPoint;

/**
 * @author dongqin
 * @description key的解析器
 * @date 2021/08/19
 */
@FunctionalInterface
public interface KeyResolver {

	/**
	 * 解析器
	 * @param idempotent 接口注解标识
	 * @param point 接口切点信息
	 * @return 处理结果
	 */
	String resolver(ApiIdempotent idempotent, JoinPoint point);

}
