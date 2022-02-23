package com.dqcer.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author dongqin
 * @description 忽略属性
 * @date 2022/01/13
 */
@ConfigurationProperties(prefix = EccGatewayProperties.PREFIX)
public class EccGatewayProperties {

    public static final String PREFIX = "ecc";

    /**
     * 启用日志
     */
    private Boolean enableLog = Boolean.TRUE;

    /**
     * 忽略
     */
    private IgnorePathProperties ignore = new IgnorePathProperties();

    /**
     * 安全规则
     */
    private SafeRuleProperties safeRule = new SafeRuleProperties();

    public IgnorePathProperties getIgnore() {
        return ignore;
    }

    public void setIgnore(IgnorePathProperties ignore) {
        this.ignore = ignore;
    }

    public SafeRuleProperties getSafeRule() {
        return safeRule;
    }

    public void setSafeRule(SafeRuleProperties safeRule) {
        this.safeRule = safeRule;
    }

    public Boolean getEnableLog() {
        return enableLog;
    }

    public void setEnableLog(Boolean enableLog) {
        this.enableLog = enableLog;
    }
}
