package com.dqcer.dxpframework.api;

/**
 * @author dongqin
 * @description 返回码接口，业务系统自定义实现，翻译统一由客户端进行维护
 * @date 2022/01/22
 */
public interface IResultCode {

	/**
	 * 返回码
	 *
	 * @return int
	 */
	int code();

	/**
	 * message
	 *
	 * @return {@link String}
	 */
	String msg();
}