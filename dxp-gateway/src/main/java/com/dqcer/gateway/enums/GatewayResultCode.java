package com.dqcer.gateway.enums;


import com.dqcer.framework.base.bean.IResultCode;

/**
 * @author dongqin
 * @description 网关CODE
 * @date 2022/01/13
 */
public enum GatewayResultCode implements IResultCode {

    /**
     * 租户参数缺失
     */
    PARAM_TENANT_NULL(999499, "租户参数缺失"),
    ;

    /**
     * 状态码
     */
    final int code;
    final String message;


    GatewayResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回码
     *
     * @return int
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * message
     *
     * @return {@link String}
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
