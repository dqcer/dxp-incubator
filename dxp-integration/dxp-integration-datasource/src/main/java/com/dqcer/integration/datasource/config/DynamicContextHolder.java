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
     * 获取所有
     *
     * @return {@link Deque<String>}
     */
    public static Deque<String> getAll() {
        return DS_CONTEXT_HOLDER.get();
    }

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

    /**
     * 强制清空本地线程
     * 防止内存泄漏，如手动调用了push可调用此方法确保清除
     */
    public static void clear() {
        DS_CONTEXT_HOLDER.remove();
    }
}
