package com.dqcer.gateway.config;

/**
 * @author dongqin
 * @description 安全规则的属性
 * @date 2022/01/13
 */
public class SafeRuleProperties {

    /**
     * 启用黑白名单
     */
    private Boolean enable = false;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
