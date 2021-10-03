package com.dqcer.dxpprovider.sso;

import com.dqcer.integration.EnableCache;
import com.dqcer.integration.ds.EnableDynamicDataSource;
import com.dqcer.integration.idempotent.annotation.EnableIdempotent;
import com.dqcer.integration.log.EnableSysLog;
import com.dqcer.integration.slider.EnableSliderValid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author dongqin
 * @description sso应用程序
 * @date 2021/09/04
 */
@EnableFeignClients(basePackages = "com")
@EnableCache
@EnableSliderValid
@EnableSysLog
@EnableDynamicDataSource
@EnableIdempotent
@SpringBootApplication
public class DxpProviderSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpProviderSsoApplication.class, args);
    }

}
