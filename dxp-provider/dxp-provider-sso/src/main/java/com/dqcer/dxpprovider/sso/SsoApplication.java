package com.dqcer.dxpprovider.sso;

import com.dqcer.integration.EnableCache;
import com.dqcer.integration.EnableWebCore;
import com.dqcer.integration.db.EnableMapper;
import com.dqcer.integration.idempotent.annotation.EnableIdempotent;
import com.dqcer.integration.log.EnableSysLog;
import com.dqcer.integration.slider.EnableSliderValid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dongqin
 * @description sso应用程序
 * @date 2021/09/04
 */
@EnableWebCore
@EnableMapper
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
