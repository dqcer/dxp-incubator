package com.dqcer.dxpframework.enums;

/**
 * @author dongqin
 * @description 枚举代码
 * @date 2021/05/29 21:05:47
 */
public enum CodeEnum {

    GL99900000("0", "操作成功"),
    GL99900301("301", "业务异常警告"),
    GL99900500("500", "系统异常"),
    GL99900999("999", "待定"),
    ;

    private String code;
    private String message;


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static CodeEnum getEnum(String code) {
        for (CodeEnum ele : CodeEnum.values()) {
            if (ele.getCode().equals(code)) {
                return ele;
            }
        }
        return null;
    }
}
