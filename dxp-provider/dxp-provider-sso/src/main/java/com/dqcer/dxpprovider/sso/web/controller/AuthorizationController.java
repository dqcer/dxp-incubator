package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.constant.CacheConstant;
import com.dqcer.dxpprovider.sso.web.model.dto.LoginDTO;
import com.dqcer.dxpprovider.sso.web.service.UserService;
import com.dqcer.dxptools.core.IpAddressUtil;
import com.dqcer.dxptools.core.ObjUtil;
import com.dqcer.integration.log.annotation.OperationLog;
import com.dqcer.integration.operation.RedissonObject;
import com.dqcer.integration.slider.model.SlideCodePlace;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * @author dongqin
 * @description 授权控制器
 * @date 2021/09/10
 */
@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Resource
    private RedissonObject redissonObject;

    @Resource
    private UserService userService;

    /**
     * 账号登录
     *
     * @param loginDTO 登录dto
     * @param request  请求
     * @return {@link ResultApi}
     */
    @OperationLog(module = "auth.account.login")
    @PostMapping("account/login")
    public ResultApi auth(@RequestBody @Validated(LoginDTO.Account.class) LoginDTO loginDTO, HttpServletRequest request) {

        String id = request.getSession().getId();
        String key = MessageFormat.format(CacheConstant.SLIDE_CODE_IP_USERNAME, IpAddressUtil.getHostIp(request), id);

        // 从缓存获取验证码的值 实际项目应该根据用户令牌等获取
        SlideCodePlace slideCodePlace = (SlideCodePlace) redissonObject.getValue(key);
        if (ObjUtil.isNotNull(slideCodePlace)) {
            boolean valid = slideCodePlace.valid(loginDTO.getNewXPosition());
            if (valid) {
                return userService.auth(loginDTO.getUe(), loginDTO.getPd());
            }
        }
        return ResultApi.warn("auth error");
    }
}
