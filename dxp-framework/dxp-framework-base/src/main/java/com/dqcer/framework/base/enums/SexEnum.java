package com.dqcer.framework.base.enums;

/**
 * 性枚举
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
public enum SexEnum {

    /**
     * 未知
     */
    UNKNOWN(0),
    /**
     * 男
     */
    MALE(1),

    /**
     * 女
     */
    FEMALE(2),
    /**
     * 未说明
     */
    UNSPECIFIED(9);

    private final int value;

    public int getValue() {
        return value;
    }

    SexEnum(int value) {
        this.value = value;
    }

    public static SexEnum toEnum(int value) {
        switch (value) {
            case 0:
                return SexEnum.UNKNOWN;
            case 1:
                return SexEnum.MALE;
            case 2:
                return SexEnum.FEMALE;
            case 9:
                return SexEnum.UNSPECIFIED;
            default:
                throw new IllegalArgumentException("invalid value , only [0, 1, 2, 9] is allowed");
        }
    }
}
