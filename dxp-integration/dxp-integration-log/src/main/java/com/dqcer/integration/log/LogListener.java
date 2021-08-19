package com.dqcer.integration.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author dongqin
 * @description 异步监听日志事件
 * @date 2021/08/19
 */
public class LogListener {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Async
    @Order
    @EventListener(LogEvent.class)
    public void listenLog(LogEvent map) {
        Object source = map.getSource();
        if (log.isInfoEnabled()) {
            log.info("log event : {}", source);
        }
    }

}