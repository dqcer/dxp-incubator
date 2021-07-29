package com.dqcer.dxptools.sync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;



@MapperScan("com.dqcer.dxptools.sync")
@ImportResource(locations={"classpath:spring-groovy.xml"})
@SpringBootApplication
public class DxpToolsSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpToolsSyncApplication.class, args);

    }


}
