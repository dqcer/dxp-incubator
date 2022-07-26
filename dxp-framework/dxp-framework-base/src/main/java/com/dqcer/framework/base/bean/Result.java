package com.dqcer.framework.base.bean;


import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 统一返回前端包装类
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4671443827915132861L;

    /**
     * 状态码
     */
    private int code;

    /**
     * message
     */
    private String message;

    /**
     * 数据对象
     */
    private T data;


    /**
     * 初始化一个新创建的 Result 对象，使其表示一个空消息。
     */
    private Result() {
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> Result<T> ok() {
        return Result.<T>builder()
                .withCode(ResultCode.SUCCESS.code)
                .withMessage(ResultCode.SUCCESS.message)
                .build();
    }

    /**
     * 返回成功数据
     *
     * @param data 数据
     * @return 成功消息
     */
    public static <T> Result<T> ok(T data) {
        return Result.<T>builder()
                .withCode(ResultCode.SUCCESS.code)
                .withMessage(ResultCode.SUCCESS.message)
                .withData(data)
                .build();
    }


    /**
     * 返回错误消息
     *
     * @param resultCode 结果代码
     * @return 警告消息
     */
    public static <T> Result<T> error(IResultCode resultCode) {
        return Result.<T>builder()
                .withCode(resultCode.getCode())
                .withMessage(resultCode.getMessage())
                .build();
    }

    /**
     * 返回错误消息
     *
     * @param resultCode 结果代码
     * @return 警告消息
     */
    public static <T> Result<T> error(int resultCode,String msg) {
        return Result.<T>builder()
                .withCode(resultCode)
                .withMessage(msg)
                .build();
    }

    /**
     * 是否成功
     *
     * @return boolean
     */
    public boolean isOk() {
        return code == ResultCode.SUCCESS.code;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Result.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("data=" + data)
                .toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultBuilder<T> builder() {
        return new ResultBuilder<>();
    }

    public static final class ResultBuilder<T> {

        private int code;

        private String message;

        private T data;

        private ResultBuilder() {
        }

        public ResultBuilder<T> withCode(int code) {
            this.code = code;
            return this;
        }

        public ResultBuilder<T> withMessage(String errMsg) {
            this.message = errMsg;
            return this;
        }

        public ResultBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        /**
         * Build result.
         *
         * @return result
         */
        public Result<T> build() {
            Result<T> result = new Result<>();
            result.setCode(code);
            result.setMessage(message);
            result.setData(data);
            return result;
        }
    }

}

