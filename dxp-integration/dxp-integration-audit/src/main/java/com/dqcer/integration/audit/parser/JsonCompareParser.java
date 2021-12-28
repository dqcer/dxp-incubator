package com.dqcer.integration.audit.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author dongqin
 * @description 2个json对比输出差异化
 * @date 2021/12/02
 */
public class JsonCompareParser {

    public static final String COMMA = ".";

    /**
     * 老json
     */
    private JSONObject oldJson;

    /**
     * 新json
     */
    private JSONObject newJson;

    /**
     * 忽略键
     */
    private Set<String> ignoreKeys;

    /**
     * 目标的key
     */
    private Set<String> targetKeys;


    public Set<DifferentDataDTO> compareJson() {
        if (null == oldJson) {
            oldJson = new JSONObject();
        }
        Map<String, Object> oldJsonMap = new HashMap<>(16);
        Map<String, Object> newJsonMap = new HashMap<>(16);
        flatFormat2Map(oldJson, "", oldJsonMap);
        flatFormat2Map(newJson, "", newJsonMap);
        Map<String, DiffNode> diffNodeMap = compare(oldJsonMap, newJsonMap);
        return toCompareResult(diffNodeMap);
    }

    private Map<String, DiffNode> compare(Map<String, Object> oldMap, Map<String, Object> newMap) {
        Map<String, DiffNode> diff = new HashMap<>(32);
        Set<String> keySet = getDiffKeys(oldMap, newMap);
        for (String k : keySet) {
            Object oldVal = oldMap.get(k);
            Object newVal = newMap.get(k);
            if (!(null == oldVal && null == newVal)) {
                if(null == oldVal) {
                    diff.put(k, new DiffNode(null, newVal));
                    continue;
                }
                if(!oldVal.equals(newVal)) {
                    diff.put(k, new DiffNode(oldVal, newVal));
                }
            }
        }
        return diff;
    }

    /**
     * 获取对比的key集
     *
     * @param oldMap 旧
     * @param newMap 新
     * @return {@link Set<String>}
     */
    private Set<String> getDiffKeys(Map<String, Object> oldMap, Map<String, Object> newMap) {
        if (!targetKeys.isEmpty()) {
            return targetKeys;
        }

        Set<String> keySet = new HashSet<>();
        keySet.addAll(oldMap.keySet());
        keySet.addAll(newMap.keySet());

        if (!ignoreKeys.isEmpty()) {
            keySet.removeAll(ignoreKeys);
        }
        return keySet;
    }

    /**
     * 比较的结果
     *
     * @param diffNodeMap diff节点图
     * @return {@link Set<DifferentDataDTO>}
     */
    private Set<DifferentDataDTO> toCompareResult(Map<String, DiffNode> diffNodeMap) {
        if(diffNodeMap.size() == 0) {
            return Collections.emptySet();
        }
        Set<DifferentDataDTO> dtoList = new LinkedHashSet<>();
        DifferentDataDTO dto;
        for (Map.Entry<String, DiffNode> nodes: diffNodeMap.entrySet()) {
            dto = new DifferentDataDTO();
            dto.setFieldName(nodes.getKey());
            dto.setOldFieldValue(nodes.getValue().getOldValue());
            dto.setNewFieldValue(nodes.getValue().getNewValue());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private static void flatFormat2Map(JSONObject object, String prefix, Map<String, Object> contain) {

        for (Map.Entry<String, Object> entry : object.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof JSONObject) {
                flatFormat2Map((JSONObject) value, prefix.concat(COMMA).concat(key), contain);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                JSONObject jsonObject = new JSONObject();
                jsonArray2Object(jsonArray, key, jsonObject);
                flatFormat2Map(jsonObject, prefix, contain);
            } else {
                if(prefix.length() == 0) {
                    contain.put(key, value);
                }else {
                    if(prefix.substring(0, 1).equals(COMMA)) {
                        String newPrefix = prefix.substring(1);
                        contain.put(newPrefix.concat(COMMA).concat(key), value);
                    }else {
                        contain.put(prefix.concat(COMMA).concat(key), value);
                    }
                }
            }
        }
    }

    private static void jsonArray2Object(JSONArray jsonArray, String prefix, JSONObject contain) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o = jsonArray.get(i);
            if(o instanceof JSONObject) {
                contain.put(prefix + "[" + i + "]", o);
            } else if (o instanceof JSONArray) {
                jsonArray2Object((JSONArray) o, prefix + "[" + i + "]", contain);
            } else {
                contain.put(prefix + "[" + i + "]", o);
            }
        }
    }

    public static void main(String[] args) {
        JSONObject oldStr = JSONObject.parseObject("{\"test\":3, \"haha\":4, \"tongzi\":[[[1,3],2,{\"hahaha\":33333333}],{\"22\":2},{\"33\":4}]}");
        JSONObject newStr = JSONObject.parseObject("{\"test\":1.5, \"test2\":666, \"tongzi\":[[[1,4],2,{\"hahaha\":33333333}],{\"22\":2},{\"33\":3}]}");

        Set<DifferentDataDTO> set = JsonCompareBuilder.of(oldStr, newStr)
                .withIgnoreKeys(Collections.emptySet())
                .withTargetKeys(Collections.emptySet())
                .build()
                .compareJson();
        System.out.println(set);
    }



    public JSONObject getOldJson() {
        return oldJson;
    }

    public void setOldJson(JSONObject oldJson) {
        this.oldJson = oldJson;
    }

    public JSONObject getNewJson() {
        return newJson;
    }

    public void setNewJson(JSONObject newJson) {
        this.newJson = newJson;
    }

    public Set<String> getIgnoreKeys() {
        return ignoreKeys;
    }

    public void setIgnoreKeys(Set<String> ignoreKeys) {
        this.ignoreKeys = ignoreKeys;
    }

    public Set<String> getTargetKeys() {
        return targetKeys;
    }

    public void setTargetKeys(Set<String> targetKeys) {
        this.targetKeys = targetKeys;
    }

    public static class DiffNode {
        private Object oldValue;
        private Object newValue;

        DiffNode(Object oldValue, Object newValue) {
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public Object getOldValue() {
            return oldValue;
        }

        public void setOldValue(Object oldValue) {
            this.oldValue = oldValue;
        }

        public Object getNewValue() {
            return newValue;
        }

        public void setNewValue(Object newValue) {
            this.newValue = newValue;
        }

        @Override
        public String toString() {
            return oldValue + " -> " + newValue;
        }
    }
}
