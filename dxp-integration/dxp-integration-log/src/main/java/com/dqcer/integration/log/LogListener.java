package com.dqcer.integration.log;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听日志事件
 */
public class LogListener {

    @Async
    @Order
    @EventListener(LogEvent.class)
    public void listenLog(LogEvent map) {
        Object source = map.getSource();
        System.out.println(source.toString());
    }

}