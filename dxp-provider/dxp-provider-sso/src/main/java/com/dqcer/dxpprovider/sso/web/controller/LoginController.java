package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.web.dto.LoginDTO;
import com.dqcer.integration.log.annotation.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Log
    @PostMapping("login")
    public ResultApi login(@RequestBody @Validated LoginDTO loginDTO) {
        return ResultApi.ok(loginDTO);
    }
}

