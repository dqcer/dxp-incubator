package com.dqcer.integration.log;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class LogEvent extends ApplicationEvent {

    public LogEvent(Map<String, String> source) {
        super(source);
    }
}
