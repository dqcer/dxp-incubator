package com.dqcer.dxptools.core;

/**
 * @author dongqin
 * @description obj工具
 * @date 2021/09/10
 */
public class ObjUtil {

    /**
     * 判断是否为空
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return null == obj || obj.equals(null);
    }

    /**
     * 判断是否不为空
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }
}
