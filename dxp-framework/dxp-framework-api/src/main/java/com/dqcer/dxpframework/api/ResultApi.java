package com.dqcer.dxpframework.api;

import com.dqcer.dxpframework.enums.CodeEnum;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author dqcer
 * @description 统一返回前端包装类
 * @date 22:21 2021/4/28
 */
public class ResultApi implements Serializable {

    private static final long serialVersionUID = 4778158632512046981L;

    public static final boolean IS_OK_TRUE = true;
    public static final boolean IS_OK_FALSE = false;

    /**
     * 请求是否成功
     */
    private boolean success;

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
    private String code;


    private transient HashMap map;


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
     * @param isOk    是可以的
     */
    protected ResultApi(boolean isOk, Object result, String message, String code) {
        setSuccess(isOk);
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
        setData(map);
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
        return new ResultApi(IS_OK_TRUE, result, null, CodeEnum.GL99900000.getCode());
    }

    /**
     * 异常
     *
     * @param code 结果
     * @return {@link ResultApi}
     */
    public static  ResultApi error(String code) {
        return new ResultApi(IS_OK_TRUE, null, null, code);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultApi{");
        sb.append("success=").append(success);
        sb.append(", data=").append(data);
        sb.append(", message='").append(message).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", map=").append(map);
        sb.append('}');
        return sb.toString();
    }
}
