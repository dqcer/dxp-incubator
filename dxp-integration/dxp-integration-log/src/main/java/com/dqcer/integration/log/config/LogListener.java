package com.dqcer.integration.log.config;

import com.dqcer.integration.log.feign.RemoteLogService;
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

    private static final Logger log = LoggerFactory.getLogger(LogListener.class);

    private final RemoteLogService logService;


    public LogListener(RemoteLogService logService) {
        this.logService = logService;
    }

    @Async
    @Order
    @EventListener(LogEvent.class)
    public void listenLog(LogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        if (log.isDebugEnabled()) {
            log.debug("Log listener: {}", sysLog);
        }
        //logService.insertLog(sysLog);
    }

}