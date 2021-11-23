package com.dqcer.provider.uac;

import com.dqcer.integration.ds.EnableDynamicDataSource;
import com.dqcer.integration.log.EnableSysLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dongqin
 * @description 用户认证中心应用程序
 * @date 2021/08/31
 */
@EnableSysLog
@EnableDynamicDataSource
@SpringBootApplication
public class DxpProviderUacApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxpProviderUacApplication.class, args);
    }

}
