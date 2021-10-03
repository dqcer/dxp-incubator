package com.dqcer.integration.idempotent.service;


/**
 * @author dongqin
 * @description api幂等服务
 * @date 2021/10/03 11:10:94
 */
public interface ApiIdempotentService {
    /**
     * API幂等的Token
     */
    String API_IDEMPOTENT_TOKEN = "api-token";

    /**
     * 创建Api的Token
     *
     * @return String
     */
    String createToken();

    /**
     * 是否存在的令牌
     *
     * @param token 令牌
     * @return boolean
     */
    boolean existByToken(String token);


    /**
     * 删除令牌
     *
     * @param token 令牌
     */
    void removeToken(String token);

}
