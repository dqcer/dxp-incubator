package com.dqcer.integration.log.config;

public class SysLogContextHolder {

    public static ThreadLocal<Object> LOG_REQUESTS_THREAD_LOCAL = new InheritableThreadLocal<>();

}
