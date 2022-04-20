package com.dqcer.provider.uac.web.controller;

import com.dqcer.framework.base.bean.Result;
import com.dqcer.provider.uac.web.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dongqin
 * @description 用户控制器
 * @date 2022/04/20
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 流程引擎 demo
     *
     * @return {@link Result<Object>}
     */
    @GetMapping("flow/engine")
    public Result<Object> flowEngine() {
        return userService.getOne();
    }
}
