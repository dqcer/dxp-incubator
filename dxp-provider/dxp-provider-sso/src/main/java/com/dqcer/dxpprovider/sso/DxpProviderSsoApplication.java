package com.dqcer.dxpprovider.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dqcer")
public class DxpProviderSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpProviderSsoApplication.class, args);
    }

}
