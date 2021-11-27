package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.web.service.AccountService;
import com.dqcer.framework.storage.UnifySession;
import com.dqcer.framework.storage.UserStorage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 帐户控制器
 * @date 2021/11/22
 */
@RestController
@RequestMapping("user")
public class AccountController {

    @Resource
    private AccountService userService;

    @PostMapping("base/detail")
    public ResultApi baseDetail() {
        return ResultApi.ok(userService.baseDetail());
    }


}

