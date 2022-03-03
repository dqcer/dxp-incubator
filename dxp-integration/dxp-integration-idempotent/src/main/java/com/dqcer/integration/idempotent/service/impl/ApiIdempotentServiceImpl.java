package com.dqcer.integration.idempotent.service.impl;

import com.dqcer.tools.core.StrUtil;
import com.dqcer.integration.idempotent.service.ApiIdempotentService;
import com.dqcer.integration.idempotent.util.SpringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;

/**
 * @author dongqin
 * @description api幂等服务
 * @date 2021/10/03 12:10:84
 */
public class ApiIdempotentServiceImpl implements ApiIdempotentService {


    /**
     * 创建Api的Token
     *
     * @return String
     */
    @Override
    public String createToken() {
        String token = UUID.randomUUID().toString();
        final ValueOperations<String, String> redis = SpringUtil.getBean(StringRedisTemplate.class).opsForValue();
        redis.set(API_IDEMPOTENT_TOKEN + token, "exist", 10000L);
        return token;
    }

    /**
     * 是否存在的令牌
     *
     * @param token 令牌
     * @return boolean
     */
    @Override
    public boolean existByToken(String token) {
        final ValueOperations<String, String> redis = SpringUtil.getBean(StringRedisTemplate.class).opsForValue();
        String s = redis.get(API_IDEMPOTENT_TOKEN + token);
        return !StrUtil.isBlank(s);
    }

    /**
     * 删除令牌
     *
     * @param token 令牌
     */
    @Override
    public void removeToken(String token) {
        final ValueOperations<String, String> redis = SpringUtil.getBean(StringRedisTemplate.class).opsForValue();
        redis.set(API_IDEMPOTENT_TOKEN + token, null);
    }
}
