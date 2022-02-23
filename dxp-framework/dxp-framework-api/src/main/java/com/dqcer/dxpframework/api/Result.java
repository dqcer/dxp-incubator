package com.dqcer.dxpframework.api;


import java.util.HashMap;

/**
 * @author dqcer
 * @description 统一返回前端包装类
 * @date 22:21 2021/4/28
 */
@SuppressWarnings("unused")
public class Result extends HashMap<String, Object> {

    /**
     * 状态码
     */
    public static final String CODE = "code";

    /**
     * 数据对象
     */
    public static final String DATA = "data";

    /**
     * message
     */
    public static final String MSG = "msg";

    /**
     * 初始化一个新创建的 Result 对象，使其表示一个空消息。
     */
    private Result() {
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param data 数据对象
     */
    private Result(int code, Object data, String msg) {
        super.put(CODE, code);
        super.put(DATA, data);
        super.put(MSG, msg);
    }

    private Result(IResultCode code, Object data) {
        super.put(CODE, code.code());
        super.put(DATA, data);
        super.put(MSG, code.msg());
    }

    /**
     * 方便链式调用
     *
     * @param key   关键
     * @param value 值
     * @return {@link Result}
     */
    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Result ok() {
        return new Result(ResultCode.SUCCESS,  null);
    }

    /**
     * 返回成功数据
     *
     * @param data 数据
     * @return 成功消息
     */
    public static Result ok(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }


    /**
     * 返回错误消息
     *
     * @param resultCode 结果代码
     * @return 警告消息
     */
    public static Result error(IResultCode resultCode) {
        return new Result(resultCode, null);
    }

    /**
     * 是否成功
     *
     * @return boolean
     */
    public boolean isOk() {
        return getCode() == ResultCode.SUCCESS.code;
    }

    /**
     * 获取code
     *
     * @return {@link Object}
     */
    public Object getData() {
        return this.get(DATA);
    }

    /**
     * 获取代码
     *
     * @return int
     */
    public int getCode() {
        return (int)this.get(CODE);
    }

    /**
     * 获取msg
     *
     * @return {@link String}
     */
    public String getMsg() {
        return (String) this.get(MSG);
    }

}

