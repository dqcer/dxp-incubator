package com.dqcer.provider.uac.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongqin
 * @description 用于参数上下文传递
 * @date 2022/04/20
 */
public class Context {

    private final Map<Class<?>, Object> CONTEXT = new ConcurrentHashMap<>();

    public <T> T get(Class<T> clazz) {
        return (T) CONTEXT.get(clazz);
    }

    public void put(Object obj) {
        if (null == obj) {
            return;
        }
        CONTEXT.put(obj.getClass(), obj);
    }
}
