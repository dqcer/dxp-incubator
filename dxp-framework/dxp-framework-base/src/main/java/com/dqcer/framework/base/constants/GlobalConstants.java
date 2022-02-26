package com.dqcer.framework.base.constants;

/**
 * @author dongqin
 * @description 全局常量
 * @date 2022/01/25
 */
@SuppressWarnings("unused")
public final class GlobalConstants {


    /**
     * 跟踪id头
     * eg: http.addHeaders(TRACE_ID_HEADER, "uuid");
     */
    public static final String TRACE_ID_HEADER = "x-trace-header";

    /**
     * 日志跟踪id
     * eg: MDC.put(LOG_TRACE_ID, "uuid);
     */
    public static final String LOG_TRACE_ID = "trace";
}
