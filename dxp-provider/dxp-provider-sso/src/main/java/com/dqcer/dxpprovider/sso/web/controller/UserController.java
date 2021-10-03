package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.web.dto.LoginDTO;
import com.dqcer.integration.ds.DS;
import com.dqcer.integration.idempotent.annotation.ApiIdempotent;
import com.dqcer.integration.log.annotation.OperationLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @DS("#loginDTO.ue")
    @ApiIdempotent(key = "#loginDTO.toString()")
    @OperationLog(module = "auth.login")
    @PostMapping("login")
    public ResultApi<LoginDTO> login(@RequestBody @Validated LoginDTO loginDTO)  {
        return ResultApi.ok(loginDTO);
    }

    @OperationLog(module = "auth.login")
    @PostMapping("base/detail")
    public ResultApi baseDetail() {
        return ResultApi.ok();
    }


}

