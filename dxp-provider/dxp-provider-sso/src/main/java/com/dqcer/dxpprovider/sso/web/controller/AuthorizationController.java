package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.constant.CacheConstant;
import com.dqcer.dxpprovider.sso.web.dto.LoginDTO;
import com.dqcer.dxptools.core.IpAddressUtil;
import com.dqcer.dxptools.core.ObjUtil;
import com.dqcer.integration.log.annotation.OperationLog;
import com.dqcer.integration.operation.RedissonObject;
import com.dqcer.integration.slider.SliderCode;
import com.dqcer.integration.slider.model.SlideCodePlace;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author dongqin
 * @description 授权控制器
 * @date 2021/09/10
 */
@RestController
public class AuthorizationController {

    @Resource
    SliderCode sliderCode;

    @Resource
    RedissonObject redissonObject;

    /**
     * 获取滑块图片
     *
     * @param username 用户名
     * @param request  请求
     * @return {@link ResultApi<SlideCodePlace>}
     * @throws IOException ioexception
     */
    @GetMapping("{username}/image")
    public ResultApi<SlideCodePlace> slideCode(@PathVariable String username, HttpServletRequest request) throws IOException {

        String hostIp = IpAddressUtil.getHostIp(request);
        String key = MessageFormat.format(CacheConstant.SLIDE_CODE_IP_USERNAME, hostIp, username);

        SlideCodePlace slideCodePlace = sliderCode.slideCode();
        redissonObject.setValue(key, slideCodePlace, 1_000L * 60 * 60);
        //  偏差值
        slideCodePlace.setDeviation(5);

        return ResultApi.ok(slideCodePlace);
    }

    /**
     * 身份验证
     *
     * @param loginDTO 登录dto
     * @param request  请求
     * @return {@link ResultApi<String>}
     */
    @OperationLog(module = "auth.login")
    @PostMapping("/auth")
    public ResultApi<String> auth(@RequestBody @Validated LoginDTO loginDTO, HttpServletRequest request) {

        String hostIp = IpAddressUtil.getHostIp(request);
        String key = MessageFormat.format(CacheConstant.SLIDE_CODE_IP_USERNAME, hostIp, loginDTO.getUe());

        /* 从缓存获取验证码的值 实际项目应该根据用户令牌等获取 */
        SlideCodePlace slideCodePlace = (SlideCodePlace) redissonObject.getValue(key);
        if (ObjUtil.isNotNull(slideCodePlace)) {
            slideCodePlace.setDeviation(loginDTO.getDeviation());
            boolean valid = slideCodePlace.valid();
            if (valid) {
                // TODO: 2021/9/10
                return ResultApi.ok("auth success");
            }
        }
        return ResultApi.warn("auth error");
    }
}
