package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 请求参数存储
 * @date 2021/11/13
 */
public class QueryStorage {


    /**
     * 请求参数
     */
    static ThreadLocal<UnifyParameter> QUERY_PARAMETER = new InheritableThreadLocal();

    /**
     * 获取参数
     *
     * @return {@link Object}
     */
    public static UnifyParameter getParameter() {
        return QUERY_PARAMETER.get();
    }

    /**
     * 设置参数
     *
     * @param parameter 参数
     */
    public static void setParameter(UnifyParameter parameter) {
        QUERY_PARAMETER.set(parameter);
    }

    /**
     * 清除参数
     */
    public static void clearParameter() {
        QUERY_PARAMETER.remove();
    }

}
