package com.dqcer.dxpframework.api;

/**
 * @author dongqin
 * @description 返回码实现
 * @date 2022/01/11
 */
public enum ResultCode implements IResultCode {


	/**
	 * 操作成功
	 */
	SUCCESS(0, "操作成功"),

	/**
	 * 服务异常
	 */
	ERROR_UNKNOWN(999500, "未知异常"),

	/**
	 * 无权限
	 */
	UN_AUTHORIZATION(999401, "无权限"),


	/**
	 * 参数异常
	 */
	ERROR_PARAMETERS(999450, "参数异常"),

	/**
	 * 错误类型
	 */
	ERROR_CONTENT_TYPE(999303, "请求头Content-Type异常"),

	/**
	 * 没有找到对应属性
	 */
	NOT_FIND_PROPERTIES_KEY(999302, "无法找到对应properties文件中对应的key"),

	/**
	 * 错误的转换
	 */
	ERROR_CONVERSION(999300, "参数接收时，类型转换异常"),


	;
	/**
	 * 状态码
	 */
	final int code;
	final String msg;

	@Override
	public int code() {
		return code;
	}

	/**
	 * message
	 *
	 * @return {@link String}
	 */
	@Override
	public String msg() {
		return this.msg;
	}

	ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
