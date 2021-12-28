package com.dqcer.framework.base.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author dongqin
 * @description 稽查日志
 * @date 2021/11/19
 */
public class AuditDTO implements Serializable {

    /**
     * 原值json
     */
    private Map<String, Object> old;

    /**
     * 前端字段json<Map>
     */
    private Map<String, String> field;


    private Map<String, String> oldStr;

    private Map<String, String> newStr;

    /**
     * 标题json
     */
    private String title;

    /**
     * 索引名称
     */
    private String indexName;


    public Map<String, String> getOldStr() {
        return oldStr;
    }

    public AuditDTO setOldStr(Map<String, String> oldStr) {
        this.oldStr = oldStr;
        return this;
    }

    public Map<String, String> getNewStr() {
        return newStr;
    }

    public AuditDTO setNewStr(Map<String, String> newStr) {
        this.newStr = newStr;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuditDTO.class.getSimpleName() + "[", "]")
                .add("old=" + old)
                .add("field=" + field)
                .add("title='" + title + "'")
                .add("indexName='" + indexName + "'")
                .toString();
    }

    public Map<String, Object> getOld() {
        return old;
    }

    public AuditDTO setOld(Map<String, Object> old) {
        this.old = old;
        return this;
    }

    public Map<String, String> getField() {
        return field;
    }

    public AuditDTO setField(Map<String, String> field) {
        this.field = field;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AuditDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIndexName() {
        return indexName;
    }

    public AuditDTO setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }
}
