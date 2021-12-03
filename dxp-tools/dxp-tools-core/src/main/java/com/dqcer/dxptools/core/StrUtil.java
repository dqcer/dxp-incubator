package com.dqcer.dxptools.core;

/**
 * @author dongqin
 * @description str工具
 * @date 2021/08/17
 */
public enum  StrUtil {

    INSTANCE;

    /**
     * 禁止实例化
     */
//    private StrUtil() {
//        throw new AssertionError();
//    }

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

    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return
     */
    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs= str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static void main(String[] args) {
        boolean blank = StrUtil.isBlank(" ");
        System.out.println(blank);
        System.out.println(StrUtil.INSTANCE.equals(StrUtil.INSTANCE));
    }
}
