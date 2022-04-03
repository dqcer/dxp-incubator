package com.dqcer.provider.uac;

import com.dqcer.framework.base.bean.Result;
import com.dqcer.integration.EnableWebCore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongqin
 * @description 用户认证中心应用程序
 * @date 2021/08/31
 */
@EnableWebCore
@RestController
@SpringBootApplication
public class UacApplication {

    public static void main(String[] args) {
        SpringApplication.run(UacApplication.class, args);
    }

    @GetMapping
    public Result<String> demo() {
        return Result.ok("good job");
    }

}
