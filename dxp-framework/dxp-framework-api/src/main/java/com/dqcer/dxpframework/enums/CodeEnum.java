package com.dqcer.dxpframework.enums;

/**
 * @author dongqin
 * @description 枚举代码
 * @date 2021/05/29 21:05:47
 */
public enum CodeEnum {

    GL99900000(0, "操作成功"),
    GL99900400(400, "参数异常"),
    GL99900999(999, "处理信息"),
    GL99900500(500, "系统异常"),
    ;

    private int code;
    private String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static CodeEnum getEnum(int code) {
        for (CodeEnum ele : CodeEnum.values()) {
            if (ele.getCode() == code) {
                return ele;
            }
        }
        return null;
    }
}
