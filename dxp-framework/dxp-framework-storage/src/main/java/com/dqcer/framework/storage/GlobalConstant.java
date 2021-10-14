package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 全局常数
 * @date 2021/10/04 18:10:56
 */
public class GlobalConstant {

    public enum Status {

        /**
         * 其他
         */
        OTHER,

        /**
         * 启用
         */
        ENABLE,

        /**
         * 禁用
         */
        DISABLE
    }

    public static void main(String[] args) {
        String str =  "com.dqcer";
        System.out.println(str.replaceAll("\\.", "\\/"));
    }
}
