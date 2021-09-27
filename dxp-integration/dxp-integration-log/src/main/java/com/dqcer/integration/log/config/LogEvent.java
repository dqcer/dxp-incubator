package com.dqcer.integration.log.config;

import org.springframework.context.ApplicationEvent;

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
    public LogEvent(BaseSysLog source) {
        super(source);
    }
}
