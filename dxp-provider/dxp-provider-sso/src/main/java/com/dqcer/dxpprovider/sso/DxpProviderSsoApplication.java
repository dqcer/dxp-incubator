package com.dqcer.dxpprovider.sso;

import com.dqcer.integration.ds.EnableDynamicDataSource;
import com.dqcer.integration.idempotent.annotation.EnableIdempotent;
import com.dqcer.integration.log.EnableLogListener;
import com.dqcer.integration.slider.EnableSliderValid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dongqin
 * @description sso应用程序
 * @date 2021/09/04
 */
@EnableSliderValid
@EnableLogListener
@EnableDynamicDataSource
@EnableIdempotent
@SpringBootApplication
public class DxpProviderSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpProviderSsoApplication.class, args);
    }

}
