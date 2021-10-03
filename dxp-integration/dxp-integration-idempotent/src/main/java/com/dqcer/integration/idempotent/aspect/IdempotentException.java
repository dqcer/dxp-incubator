package com.dqcer.integration.idempotent.aspect;

/**
 * @author dongqin
 * @description 幂等异常
 * @date 2021/08/19
 */
public class IdempotentException extends Exception {

	private IdempotentExceptionTypeEnum typeEnum;

	public IdempotentException() {
		super();
	}
	public IdempotentException(IdempotentExceptionTypeEnum typeEnum) {
		super();
		this.typeEnum = typeEnum;
	}

	public IdempotentException(String message) {
		super(message);
	}

	public IdempotentException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdempotentException(Throwable cause) {
		super(cause);
	}

	protected IdempotentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public enum IdempotentExceptionTypeEnum {
		/**
		 * 其它
		 */
		PARAM,

		/**
		 * 新增
		 */
		INSERT,

		/**
		 * 修改
		 */
		UPDATE,

		/**
		 * 删除
		 */
		DELETE,

		/**
		 * 导出
		 */
		EXPORT,

		/**
		 * 导入
		 */
		IMPORT,

	}


}
