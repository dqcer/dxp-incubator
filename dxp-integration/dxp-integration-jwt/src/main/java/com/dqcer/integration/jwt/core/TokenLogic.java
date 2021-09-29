package com.dqcer.integration.jwt.core;

import com.dqcer.integration.jwt.bean.SessionBean;
import com.dqcer.integration.jwt.util.AESUtil;
import com.dqcer.integration.operation.RedissonObject;

import javax.annotation.Resource;

public class TokenLogic {

    @Resource
    RedissonObject redissonObject;

    public String login(Long id) {
        String token = createToken(id);
        redissonObject.setValue(id.toString(), new SessionBean(id, token), 60 * 30);
        return token;
    }

    /**
     * 创建令牌
     *
     * @param id id
     * @return {@link String}
     */
    private String createToken(Long id) {
        return AESUtil.encrypt(id.toString());
    }



}
