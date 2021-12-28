package com.dqcer.integration.audit.enums;

/**
 * @author dongqin
 * @description 类型枚举
 * @date 2021/11/18
 */
public enum TypeEnum {

    /**
     * 插入
     */
    INSERT(1),

    /**
     * 更新
     */
    UPDATE(2),

    /**
     * 删除
     */
    DELETE(3),

    /**
     * 状态
     */
    STATUS(4),

    /**
     * 下载
     */
    DOWNLOAD(5),
    ;

    private int value;

    public int getValue() {
        return value;
    }

    TypeEnum(int value) {
        this.value = value;
    }

}
