package com.dqcer.framework.base.enums;

/**
 * 删除枚举
 *
 * @author dongqin
 * @date 2022/07/26
 */
@SuppressWarnings("unused")
public enum DelFlayEnum {
    /**
     * 1 = 正常
     */
    NORMAL(1),

    /**
     * 2 = 已删除
     */
    DELETED(2);

    private final int value;

    public int getValue() {
        return value;
    }

    DelFlayEnum(int value) {
        this.value = value;
    }

    public static DelFlayEnum toEnum(int value) {
        switch (value) {
            case 1:
                return DelFlayEnum.NORMAL;
            case 2:
                return DelFlayEnum.DELETED;
            default:
                throw new IllegalArgumentException("invalid value , only [1, 2] is allowed");
        }
    }
}
