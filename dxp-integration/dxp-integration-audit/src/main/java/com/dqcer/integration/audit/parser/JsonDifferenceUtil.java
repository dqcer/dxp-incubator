package com.dqcer.integration.audit.parser;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.dqcer.integration.audit.annotation.AuditDTO;
import com.dqcer.integration.audit.enums.TypeEnum;

import java.util.*;

/**
 * @author dongqin
 * @description json区别工具
 * @date 2021/12/01
 */
public final class JsonDifferenceUtil {

    public static final String AUDIT = "audit";

    public static final String SUFFIX = "Str";

    private JsonDifferenceUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }


    public static void main(String[] args) {
        String str1= "{\n" +
                "    \"audit\":{\n" +
                "        \"title\":\"router.organiztion\",\n" +
                "        \"old\":{\n" +
                "        \"name\":\"华西\",\n" +
                "    \"city\":\"2\"\n" +
                "\n" +
                "        },\n" +
                "        \"field\":{\n" +
                "        \"name\":\"组织名称（建议前端映射，这里就是一个key）\",\n" +
                "        \"cityStr\":\"地址（建议前端映射，这里就是一个key）\"\n" +
                "\n" +
                "        },\n" +
                "        \"indexName\":\"组织管理（导出）\",\n" +
                "        \"newStr\":{\n" +
                "               \"cityStr\":\"北京\"\n" +
                "\n" +
                "        },\n" +
                "        \"oldStr\":{\n" +
                "               \"cityStr\":\"四川成都\"\n" +
                "\n" +
                "        }\n" +
                "    },\n" +
                "        \"name\":\"协和\",\n" +
                "    \"city\":\"1\"\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(str1);
        Set<String> keys = new HashSet<>();
        String [] ignoreKeys = {"city"};
        Collection<? super DifferentDataDTO> list = difference(jsonObject, TypeEnum.UPDATE, keys, ignoreKeys);
        System.out.println(">>>>>>>>>>" + list);

    }

    /**
     * 区别
     *
     * @param ignoreKeys 忽略键
     * @param param      参数
     * @param typeEnum   枚举类型
     * @param targetKeys 目标的钥匙
     * @return {@link Collection<DifferentDataDTO>}
     */
    public static Collection<DifferentDataDTO> difference(JSONObject param, TypeEnum typeEnum, Set<String> targetKeys, String... ignoreKeys) {

        if (typeEnum.equals(TypeEnum.DOWNLOAD)) {
            return Collections.emptySet();
        }

        Set<DifferentDataDTO> list = new HashSet<>();

        AuditDTO audit = param.getObject(AUDIT, AuditDTO.class);

        if (null != audit) {
            JSONObject oldVal = JSONObject.parseObject(JSONUtil.toJsonStr(audit.getOld()));
            JSONObject field = JSONObject.parseObject(JSONUtil.toJsonStr(audit.getField()));
            JSONObject oldStr = JSONObject.parseObject(JSONUtil.toJsonStr(audit.getOldStr()));
            JSONObject newStr = JSONObject.parseObject(JSONUtil.toJsonStr(audit.getNewStr()));

            param.remove(AUDIT);

            JSONObject newVal = param;

            Set<DifferentDataDTO> set = JsonCompareBuilder.of(oldVal, newVal)
                    .withTargetKeys(targetKeys)
                    .withIgnoreKeys(new HashSet<>(Arrays.asList(ignoreKeys)))
                    .build()
                    .compareJson();
            list.addAll(set);

            Set<DifferentDataDTO> set1 = JsonCompareBuilder.of(oldStr, newStr)
                    .withTargetKeys(targetKeys)
                    .withIgnoreKeys(new HashSet<>(Arrays.asList(ignoreKeys)))
                    .build()
                    .compareJson();
            list.addAll(set1);

            // field 替换
            for (DifferentDataDTO dto : list) {
                String fieldName = dto.getFieldName();
                boolean length = fieldName.length() > SUFFIX.length();
                String string = field.getString(fieldName);
                if (null == string) {
                    if (fieldName.endsWith(SUFFIX) && length) {
                        string = field.getString(fieldName.substring(0, fieldName.length() - 3));
                        if (null != string) {
                            dto.setFieldName(string);
                        }
                    }
                    continue;
                }
                dto.setFieldName(string);
            }
        }
        return list;
    }
}
