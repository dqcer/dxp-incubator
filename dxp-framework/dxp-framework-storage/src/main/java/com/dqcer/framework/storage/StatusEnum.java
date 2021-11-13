package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 状态枚举
 * @date 2021/11/13
 */
public enum  StatusEnum {

    /**
     * 启用
     */
    ENABLE(1),

    /**
     * 禁用
     */
    DISABLE(2);

    private int status;

    StatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
