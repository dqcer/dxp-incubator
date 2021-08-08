package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpframework.dto.annontation.ValidDTO;
import com.dqcer.dxpprovider.sso.web.dto.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @ValidDTO
    @PostMapping("login")
    public Mono<ResultApi<String>> login(@RequestBody LoginDTO loginDTO) {
        return ResultApi.ok();
    }
}
