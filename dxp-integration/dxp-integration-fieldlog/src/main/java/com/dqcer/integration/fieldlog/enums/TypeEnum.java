package com.dqcer.integration.fieldlog.enums;

/**
 * @author dongqin
 * @description 类型枚举
 * @date 2021/11/18
 */
public enum TypeEnum {

    INSERT(1),
    UPDATE(2),
    DELETE(3),
    STATUS(4),
    ;

    private int value;

    public int getValue() {
        return value;
    }

    TypeEnum(int value) {
        this.value = value;
    }

}
