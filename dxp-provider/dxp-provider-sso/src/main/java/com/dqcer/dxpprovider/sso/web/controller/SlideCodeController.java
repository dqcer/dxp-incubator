package com.dqcer.dxpprovider.sso.web.controller;

import com.dqcer.framework.base.bean.Result;
import com.dqcer.framework.storage.CacheConstant;
import com.dqcer.tools.core.IpAddressUtil;
import com.dqcer.integration.cache.operation.RedissonObject;
import com.dqcer.integration.slider.SliderCode;
import com.dqcer.integration.slider.model.SlideCodePlace;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("slider")
public class SlideCodeController {

    @Resource
    private SliderCode sliderCode;

    @Resource
    private RedissonObject redissonObject;

    /**
     * 获取滑块图片
     *
     * @param request 请求
     * @return {@link Result}
     * @throws IOException IOException
     */
    @PostMapping("account/image")
    public Result slideCode(HttpServletRequest request) throws IOException {

        String hostIp = IpAddressUtil.getHostIp(request);
        String key = MessageFormat.format(CacheConstant.SLIDE_CODE_IP_USERNAME, hostIp, request.getSession().getId());

        SlideCodePlace slideCodePlace = sliderCode.slideCode();
        //  偏差值
        slideCodePlace.setDeviation(5);
        redissonObject.setValue(key, slideCodePlace, 1_000L * 60 * 60);

        return Result.ok(slideCodePlace);
    }
}
