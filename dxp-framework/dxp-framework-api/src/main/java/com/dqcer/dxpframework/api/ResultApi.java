package com.dqcer.dxpframework.api;

import com.dqcer.dxpframework.enums.CodeEnum;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author dqcer
 * @description 统一返回前端包装类
 * @date 22:21 2021/4/28
 */
public class ResultApi<T> implements Serializable {

    private static final long serialVersionUID = 4778158632512046981L;

    private T data;

    private String message;

    private Integer code;

    private LocalDate now = LocalDate.now();

    /**
     * 私有化
     */
    private ResultApi() {
    }

    /**
     * 有参构造器，不对外开放
     *
     * @param result  data
     * @param message 消息
     * @param code    状态码
     */
    protected ResultApi(T result, String message, Integer code) {
        setData(result);
        setMessage(message);
        setCode(code);
    }


    /**
     * success
     *
     * @return ResultApi<T>
     */
    public static ResultApi ok() {
        return ok(null);
    }

    /**
     * success
     *
     * @param result
     * @param <T>
     * @return ResultApi<T>
     */
    public static <T> ResultApi<T> ok(T result) {
        return new ResultApi(result, CodeEnum.GL99900000.getMessage(), CodeEnum.GL99900000.getCode());
    }


    /**
     * success
     * <p>
     * 需要处理提示信息的标识
     *
     * @param info
     * @return ResultApi<String>
     */
    public static ResultApi<String> info(String info) {
        return new ResultApi(info, CodeEnum.GL99900999.getMessage(), CodeEnum.GL99900999.getCode());
    }

    /**
     * 无参异常
     *
     * @return ResultApi<String>
     */
    public static ResultApi<String> error() {
        return new ResultApi("error", CodeEnum.GL99900500.getMessage(), CodeEnum.GL99900999.getCode());
    }

    /**
     * 有参异常
     *
     * @param msg 错误信息
     * @return ResultApi<String>
     */
    public static ResultApi<String> error(String msg) {
        return new ResultApi(msg, CodeEnum.GL99900500.getMessage(), CodeEnum.GL99900999.getCode());
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public LocalDate getNow() {
        return now;
    }

    public void setNow(LocalDate now) {
        this.now = now;
    }
}
