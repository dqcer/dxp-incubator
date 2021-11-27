package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 请求存储
 * @date 2021/11/13
 */
public class RequestStorage {

    public static ThreadLocal<Object> REQUEST = new InheritableThreadLocal<>();

    /**
     * 获取会话
     *
     * @return {@link UnifySession}
     */
    public static Object getSession() {
        return REQUEST.get();
    }

    /**
     * 设置会话
     *
     * @param box 盒子
     */
    public static void setSession(Object box) {
        REQUEST.set(box);
    }

    /**
     * 清除会话
     */
    public static void clearSession() {
        REQUEST.remove();
    }
}
