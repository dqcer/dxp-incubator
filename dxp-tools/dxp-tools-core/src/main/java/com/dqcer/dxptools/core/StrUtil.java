package com.dqcer.dxptools.core;

/**
 * @author dongqin
 * @description str工具
 * @date 2021/08/17
 */
public class StrUtil {


    /**
     * 判断是否为空
     *
     * @param str str
     * @return boolean
     */
    public static boolean isBlank(String str) {
        if (null == str || str.length() == 0 || str.trim().length() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否不为空
     *
     * @param str str
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
