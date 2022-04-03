package com.dqcer.dxpprovider.sso;

import com.dqcer.integration.EnableWebCore;
import com.dqcer.integration.cache.EnableCache;
import com.dqcer.integration.idempotent.annotation.EnableIdempotent;
import com.dqcer.integration.log.EnableSysLog;
import com.dqcer.integration.slider.EnableSliderValid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dongqin
 * @description sso应用程序 http://localhost:8080/druid/index.html
 * @date 2021/09/04
 */
@EnableWebCore
@EnableCache
@EnableSliderValid
@EnableSysLog
@EnableIdempotent
@SpringBootApplication
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

}
