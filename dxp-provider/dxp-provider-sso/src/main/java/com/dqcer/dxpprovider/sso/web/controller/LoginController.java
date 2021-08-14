package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpframework.dto.annontation.ValidDTO;
import com.dqcer.dxpprovider.sso.web.dto.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
public class LoginController {


    @ValidDTO
    @PostMapping("login")
    public ResultApi<String> login(@RequestBody LoginDTO loginDTO) {
        return ResultApi.ok();
    }

    @ValidDTO
    @PostMapping("")
    public ResultApi<?> demo(@RequestBody LoginDTO loginDTO) {
        System.out.println(loginDTO.getPd());
        HashMap<String, Object> map = new HashMap<>(8);
        map.put("startTime", new Date());
        map.put("endTime", "2021-08-08");
        map.put("fack", loginDTO);
        return ResultApi.ok(map);
    }

    public static void main(String[] args) {
        long start = System.nanoTime();

        // String 20582900 ns
        String txt = String.format("Test %s", " Hello");
        System.out.println("String: " + (System.nanoTime() - start) + " ns");

        // MessageFormat 1160900 ns
        start = System.nanoTime();
        txt = MessageFormat.format("Test {0}"," Hello");
        System.out.println("MessageFormat: " + (System.nanoTime() - start) + " ns");



        // StringBuffer 62100 ns
        start = System.nanoTime();
        txt = new StringBuffer("Test ").append("Hello").toString();
        System.out.println("StringBuffer: " + (System.nanoTime() - start) + " ns");

        // StringBuilder 8800 ns
        start = System.nanoTime();
        txt = new StringBuilder("Test ").append("Hello").toString();
        System.out.println("StringBuilder: " + (System.nanoTime() - start) + " ns");
        
    }
}

