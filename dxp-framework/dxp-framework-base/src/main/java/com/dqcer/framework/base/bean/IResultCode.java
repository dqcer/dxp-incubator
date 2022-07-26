package com.dqcer.framework.base.bean;

/**
 * 返回码接口，业务系统自定义实现，翻译统一由客户端进行维护
 *
 * @author dongqin
 * @date 2022/07/26
 */
public interface IResultCode {

	/**
	 * 返回码
	 *
	 * @return int
	 */
	int getCode();

	/**
	 * message
	 *
	 * @return {@link String}
	 */
	String getMessage();
}
