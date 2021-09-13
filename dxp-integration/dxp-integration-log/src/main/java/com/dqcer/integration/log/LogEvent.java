package com.dqcer.integration.log;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author dongqin
 * @description 日志事件
 * @date 2021/09/13
 */
public class LogEvent extends ApplicationEvent {

    /**
     * 日志事件
     *
     * @param source 源
     */
    public LogEvent(Map<String, String> source) {
        super(source);
    }
}
