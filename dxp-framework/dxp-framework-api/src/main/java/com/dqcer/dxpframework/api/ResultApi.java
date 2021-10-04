package com.dqcer.dxpframework.api;

import com.dqcer.dxpframework.enums.CodeEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dqcer
 * @description 统一返回前端包装类
 * @date 22:21 2021/4/28
 */
public class ResultApi implements Serializable {

    private static final long serialVersionUID = 4778158632512046981L;

    /**
     * 数据
     */
    private Object data;

    /**
     * 消息
     */
    private String message;

    /**
     * code
     */
    private Integer code;

    /**
     * 时间戳
     */
    private String now = LocalDate.now().toString();

    private transient Map<String, Object> map;


    /**
     * 私有化
     */
    protected ResultApi() { }

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
        this.map = new HashMap(8);
    }

    /**
     * 无参success
     *
     * @return {@link ResultApi}
     */
    public static ResultApi ok() {
        return ok(null);
    }

    /**
     * 多个result
     *
     * @param key    key
     * @param result 结果
     * @return {@link ResultApi}
     */
    public ResultApi put(String key, Object result) {
        map.put(key, result);
        setData((Object) map);
        setMessage(message);
        setCode(code);
        return this;
    }

    /**
     * 有参success
     *
     * @param result 结果
     * @return {@link ResultApi}
     */
    public static  ResultApi ok(Object result) {
        return new ResultApi(result, CodeEnum.GL99900000.getMessage(), CodeEnum.GL99900000.getCode());
    }

    /**
     * 无参警告
     *
     * @return {@link ResultApi}
     */
    public static  ResultApi warn() {
        return new ResultApi(null, CodeEnum.GL99900301.getMessage(), CodeEnum.GL99900301.getCode());
    }

    /**
     * 警告
     *
     * @param result 结果
     * @return {@link ResultApi}
     */
    public static  ResultApi warn(Object result) {
        return new ResultApi(result, CodeEnum.GL99900301.getMessage(), CodeEnum.GL99900301.getCode());
    }

    /**
     * 无参异常
     *
     * @return {@link ResultApi}
     */
    public static  ResultApi error() {
        return new ResultApi(null, CodeEnum.GL99900500.getMessage(), CodeEnum.GL99900999.getCode());
    }

    /**
     * 有参异常
     *
     * @param result 结果
     * @return {@link ResultApi}
     */
    public static  ResultApi error(Object result) {
        return new ResultApi(result, CodeEnum.GL99900500.getMessage(), CodeEnum.GL99900999.getCode());
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

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

}
