package com.dqcer.gateway.properties;

/**
 * 安全规则属性
 *
 * @author dongqin
 * @date 2022/07/26
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
