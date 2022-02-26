package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.framework.base.api.Result;
import com.dqcer.integration.idempotent.service.ApiIdempotentService;
import com.dqcer.integration.log.annotation.OperationLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 常见的控制器
 * @date 2021/10/03 15:10:14
 */
@RestController
@RequestMapping("common")
public class CommonController {

    @Resource
    private ApiIdempotentService apiIdempotentService;

    /**
     * 获取幂等token
     *
     * @return {@link Result}
     */
    @OperationLog(module = "idempotent.token")
    @PostMapping("idempotent/token")
    public Result idempotentToken()  {
        return Result.ok(apiIdempotentService.createToken());
    }

}
