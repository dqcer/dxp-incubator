package com.dqcer.dxpprovider.sso;

import com.dqcer.integration.ds.EnableDynamicDataSource;
import com.dqcer.integration.idempotent.annotation.EnableIdempotent;
import com.dqcer.integration.log.EnableLogListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableLogListener
@EnableDynamicDataSource
@EnableIdempotent
@SpringBootApplication
public class DxpProviderSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpProviderSsoApplication.class, args);
    }

}
