package com.dqcer.dxpframework.dto.exception;

/**
 * @author dongqin
 * @description valid exception
 * @date 0:52 2021/5/25
 */
public class ValidException extends RuntimeException {

    public ValidException() {
        super();
    }

    public ValidException(String message) {
        super(message);
    }

    public ValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidException(Throwable cause) {
        super(cause);
    }

    protected ValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
