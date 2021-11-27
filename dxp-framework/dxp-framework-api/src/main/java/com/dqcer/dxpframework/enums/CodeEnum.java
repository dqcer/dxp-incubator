package com.dqcer.dxpframework.enums;

/**
 * @author dongqin
 * @description 枚举代码
 * @date 2021/05/29 21:05:47
 */
public enum CodeEnum {

    /**
     * 操作成功
     */
    GL99900000("0"),

    GL99900999("999"),
    ;

    private String code;


    public String getCode() {
        return code;
    }


    CodeEnum(String code) {
        this.code = code;
    }
}
