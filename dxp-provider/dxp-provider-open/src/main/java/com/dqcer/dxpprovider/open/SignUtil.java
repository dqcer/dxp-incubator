package com.dqcer.dxpprovider.open;

import com.dqcer.tools.core.MD5Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author dongqin
 * @description 签名工具类
 * @date 2021/08/20 00:08:47
 */
public class SignUtil {

    public static String sign(Map<String, Object> map, String appSecret) {
        //  除去数组中的空值和签名参数
        Map<String, Object> sPara = nullFilter(map);

        TreeMap<String, Object> stringMap = new TreeMap<>(sPara);

        Set<Map.Entry<String, Object>> entries = stringMap.entrySet();

        StringBuilder stringBuilder = new StringBuilder(512);
        for (Map.Entry<String, Object> m : entries) {
            String key = m.getKey();
            stringBuilder.append(key);
            stringBuilder.append(m.getValue());
        }
        stringBuilder.append(appSecret);
        return MD5Util.getMD5(stringBuilder.toString());

    }

    /**
     * 空滤器
     *
     * @param map map
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, Object> nullFilter(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        if (map == null || map.size() <= 0) {
            return result;
        }
        for (String key : map.keySet()) {
            Object o = map.get(key);
            if (null == o) {
                continue;
            }
            result.put(key, o);
        }
        return result;
    }

}
