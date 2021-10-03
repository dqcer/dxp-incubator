package com.dqcer.integration.idempotent.annotation;

import java.lang.annotation.*;

/**
 * @author dongqin
 * @description
 * @date 20:16 2021/5/13
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {

	/**
	 * 开启验证token
	 *
	 * @return boolean
	 */
	boolean validToken() default false;

	/**
	 * 参数 key
	 *
	 * @return {@link String}
	 */
	String key();

	/**
	 * 到期时间 默认 60
	 *
	 * @return int
	 */
	int expires() default 60;

	/**
	 * 是否删除 key 默认删除
	 *
	 * @return boolean
	 */
	boolean delKey() default true;

}
