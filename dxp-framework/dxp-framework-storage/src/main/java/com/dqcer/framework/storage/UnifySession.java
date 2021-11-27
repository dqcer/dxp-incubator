package com.dqcer.framework.storage;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongqin
 * @description 统一的会话
 * @date 2021/11/13
 */
public class UnifySession implements Serializable {

    private static final long serialVersionUID = 3500705501444133486L;

    /**
     * 账号主键
     */
    private Long accountId;

    /**
     * 所属客户
     */
    private Long tenantId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 数据源名称
     */
    private String dsKey;

    /**
     * 语言
     */
    private String language;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 扩展
     */
    private ConcurrentHashMap<String, Object> extension;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UnifySession{");
        sb.append("accountId=").append(accountId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", roleId=").append(roleId);
        sb.append(", dsKey='").append(dsKey).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", extension=").append(extension);
        sb.append('}');
        return sb.toString();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDsKey() {
        return dsKey;
    }

    public void setDsKey(String dsKey) {
        this.dsKey = dsKey;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public ConcurrentHashMap<String, Object> getExtension() {
        return extension;
    }

    public void setExtension(ConcurrentHashMap<String, Object> extension) {
        this.extension = extension;
    }
}
