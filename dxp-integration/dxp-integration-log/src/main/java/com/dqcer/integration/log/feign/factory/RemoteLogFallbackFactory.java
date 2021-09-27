package com.dqcer.integration.log.feign.factory;

import com.dqcer.integration.log.feign.RemoteLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author dongqin
 * @description 远程日志
 * @date 2021/09/15
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error(throwable.getMessage());
        return log -> { };
    }
}
