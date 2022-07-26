package com.dqcer.framework.base.enums;

/**
 * 状态枚举
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
public enum StatusEnum {

    /**
     * 启用
     */
    ENABLE(1),

    /**
     * 禁用
     */
    DISABLE(2);

    private final int value;

    public int getValue() {
        return value;
    }

    StatusEnum(int value) {
        this.value = value;
    }

    public static StatusEnum toEnum(int value) {
        switch (value) {
            case 1:
                return StatusEnum.ENABLE;
            case 2:
                return StatusEnum.DISABLE;
            default:
                throw new IllegalArgumentException("invalid value , only [1, 2] is allowed");
        }
    }
}
