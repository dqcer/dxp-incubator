package com.dqcer.integration.datasource.config;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author dongqin
 * @description 动态数据源上下文
 * @date 2021/10/09
 */
public final class DynamicContextHolder {

    /**
     * ds上下文持有人
     */
    private static final ThreadLocal<Deque<String>> DS_CONTEXT_HOLDER = ThreadLocal.withInitial(ArrayDeque::new);

    private DynamicContextHolder() { }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称
     */
    public static String peek() {
        return DS_CONTEXT_HOLDER.get().peek();
    }

    /**
     * 设置当前线程数据源
     *
     * @param dataSourceName 数据源名称
     */
    public static void push(String dataSourceName) {
        DS_CONTEXT_HOLDER.get().push(dataSourceName);
    }

    /**
     * 清空当前线程数据源
     */
    public static void poll() {
        Deque<String> deque = DS_CONTEXT_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            DS_CONTEXT_HOLDER.remove();
        }
    }
}
