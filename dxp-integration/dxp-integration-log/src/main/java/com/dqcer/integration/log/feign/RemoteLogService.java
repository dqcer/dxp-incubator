package com.dqcer.integration.log.feign;

import com.dqcer.integration.log.config.BaseSysLog;
import com.dqcer.integration.log.feign.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author dongqin
 * @description 日志Feign服务层
 * @date 2021/09/15
 */
@FeignClient(value = "log-server", url = "${feign.log.server.url}", fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 插入日志
     *
     * @param log 日志
     */
    @PostMapping("log/save")
    void insertLog(@RequestBody BaseSysLog log);


}
