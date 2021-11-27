package com.dqcer.dxptools.core;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongqin
 * @description bean 工具类
 * @date 2021/08/13
 */
public class BeanUtil {

    /**
     * 禁止实例化
     */
    private BeanUtil() {
        throw new AssertionError();
    }

    /**
     * bean 转 map
     *
     * @param bean 豆
     * @return {@link Map<String, Object>}
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> keyValues = new HashMap(16);
        Method[] methods = bean.getClass().getMethods();
        try {
            for (Method method : methods) {
                method.setAccessible(true);
                String methodName = method.getName();
                if (methodName.contains("get") && !"getClass".equals(methodName)) {
                    Object value = method.invoke(bean);
                    String key = methodName.substring(methodName.indexOf("get") + 3);
                    Object temp = key.substring(0, 1).toString().toLowerCase();
                    key = key.substring(1);
                    key = temp + key;
                    keyValues.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValues;
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.setId("2324");
        Map<String, Object> map = beanToMap(demo);
        System.out.println(map);

    }

    static class Demo implements Serializable {

        private static final long serialVersionUID = -5990251749177713806L;
        private String id;

        private String username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}