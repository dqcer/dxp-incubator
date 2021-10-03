package com.dqcer.integration.log;


import com.dqcer.integration.log.config.LogAutoConfiguration;
import com.dqcer.integration.log.feign.RemoteLogService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dongqin
 * @description 启用日志监听器
 * @date 2021/09/15
 */
@EnableFeignClients
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({LogAutoConfiguration.class, RemoteLogService.class})
public @interface EnableSysLog {

    String[] basePackages() default {"com.dqcer.integration.log.feign"};
}
