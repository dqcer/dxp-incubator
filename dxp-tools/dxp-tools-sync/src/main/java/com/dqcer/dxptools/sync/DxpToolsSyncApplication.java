package com.dqcer.dxptools.sync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.dqcer.dxptools.dynamic")
@SpringBootApplication(scanBasePackages = "com.dqcer")
public class DxpToolsSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpToolsSyncApplication.class, args);

    }

}
