package com.dqcer.integration.audit.bean;

public class AuditLogDetailBean {

    private Long id;
    private Long auditId;
    private String field;
    private String oldValueStr;
    private String newValueStr;
    private String oldValue;
    private String newValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOldValueStr() {
        return oldValueStr;
    }

    public void setOldValueStr(String oldValueStr) {
        this.oldValueStr = oldValueStr;
    }

    public String getNewValueStr() {
        return newValueStr;
    }

    public void setNewValueStr(String newValueStr) {
        this.newValueStr = newValueStr;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
