package com.dqcer.dxpframework.api;

import com.dqcer.dxpframework.enums.CodeEnum;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author dqcer
 * @description 统一返回前端包装类
 * @date 22:21 2021/4/28
 */
public class ResultApi implements Serializable {

    private static final long serialVersionUID = 4778158632512046981L;

    private Object data;

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
    protected ResultApi(Object result, String message, Integer code) {
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
     * @param result 结果
     * @return ResultApi
     */
    public static  ResultApi ok(Object result) {
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
    public static ResultApi info(String info) {
        return new ResultApi(info, CodeEnum.GL99900999.getMessage(), CodeEnum.GL99900999.getCode());
    }

    /**
     * 无参异常
     *
     * @return ResultApi<String>
     */
    public static ResultApi error() {
        return new ResultApi("error", CodeEnum.GL99900500.getMessage(), CodeEnum.GL99900999.getCode());
    }

    /**
     * 有参异常
     *
     * @param msg 错误信息
     * @return ResultApi<String>
     */
    public static ResultApi error(String msg) {
        return new ResultApi(msg, CodeEnum.GL99900500.getMessage(), CodeEnum.GL99900999.getCode());
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
