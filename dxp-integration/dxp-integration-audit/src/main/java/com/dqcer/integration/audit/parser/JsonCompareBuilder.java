package com.dqcer.integration.audit.parser;

import com.alibaba.fastjson.JSONObject;

import java.util.Set;

/**
 * @author dongqin
 * @description json比较建设者
 * @date 2021/12/02
 */
public final class JsonCompareBuilder {

    /**
     * 修改之前json
     */
    private JSONObject oldJson;

    /**
     * 修改之后json
     */
    private JSONObject newJson;

    /**
     * 要忽略key
     */
    private Set<String> ignoreKeys;

    /**
     * 指定比较的某几个key
     */
    private Set<String> targetKeys;

    private JsonCompareBuilder(JSONObject oldJson, JSONObject newJson) {
        this.oldJson = oldJson;
        this.newJson = newJson;
    }

    public static JsonCompareBuilder of(JSONObject oldJson, JSONObject newJson) {
        return new JsonCompareBuilder(oldJson, newJson);
    }

    public JsonCompareBuilder withIgnoreKeys(Set<String> ignoreKeys) {
        this.ignoreKeys = ignoreKeys;
        return this;
    }

    public JsonCompareBuilder withTargetKeys(Set<String> targetKeys) {
        this.targetKeys = targetKeys;
        return this;
    }

    public JsonCompareParser build() {
        JsonCompareParser jsonDiffParser = new JsonCompareParser();
        jsonDiffParser.setOldJson(oldJson);
        jsonDiffParser.setNewJson(newJson);
        jsonDiffParser.setIgnoreKeys(ignoreKeys);
        jsonDiffParser.setTargetKeys(targetKeys);
        return jsonDiffParser;
    }

}
