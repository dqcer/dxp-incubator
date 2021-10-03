package com.dqcer.dxpprovider.sso;

import com.dqcer.integration.EnableCache;
import com.dqcer.integration.ds.EnableDynamicDataSource;
import com.dqcer.integration.idempotent.annotation.EnableIdempotent;
import com.dqcer.integration.log.EnableSysLog;
import com.dqcer.integration.log.feign.factory.RemoteLogFallbackFactory;
import com.dqcer.integration.slider.EnableSliderValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author dongqin
 * @description sso应用程序
 * @date 2021/09/04
 */
@EnableCache
@EnableSliderValid
@EnableSysLog
@EnableDynamicDataSource
@EnableIdempotent
@SpringBootApplication
public class DxpProviderSsoApplication {

    private static final Logger log = LoggerFactory.getLogger(DxpProviderSsoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DxpProviderSsoApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String port = environment.getProperty("port");
        log.info(port);
    }

}
