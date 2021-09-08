package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.dxpframework.api.ResultApi;
import com.dqcer.dxpprovider.sso.web.dto.AuthorizationDTO;
import com.dqcer.integration.slider.SliderCode;
import com.dqcer.integration.slider.model.SlideCodePlace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xnio.Result;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class AuthorizationController {

    @Resource
    SliderCode sliderCode;

    @GetMapping("/image")
    public ResultApi slideCode() throws IOException {
        /* 获取验证码 */
        SlideCodePlace slideCodePlace = sliderCode.slideCode();
        /* 缓存验证码 分布式建议采用Redis等方案 */
        /* 设置误差值 */
        slideCodePlace.setDeviation(5);
        return ResultApi.ok(slideCodePlace);
    }

    /**
     * 校验验证码
     *
     * @author LiuGangQiang Create in 2021/05/28
     * @param xposition X轴位置
     * @return {@link Result}
     */
    @GetMapping("/valid")
    public ResultApi<?> valid(Integer xposition) {
        /* 从缓存获取验证码的值 实际项目应该根据用户令牌等获取 */
//        SlideCodePlace slideCodePlace = redisService.getSlideCode();
//        boolean valid = slideCodePlace.valid();
//        if (!valid) {
//            return ResultApi.warn();
//        }
        return ResultApi.ok();
    }

    public ResultApi auth(AuthorizationDTO dto) {
        return null;
    }


}
