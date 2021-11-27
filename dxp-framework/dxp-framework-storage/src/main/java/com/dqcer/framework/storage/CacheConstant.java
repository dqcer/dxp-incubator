package com.dqcer.framework.storage;

/**
 * @author dongqin
 * @description 缓存常量
 * @date 2021/09/10
 */
public class CacheConstant {


    /**
     * 滑块code
     */
    public static final String SLIDE_CODE_IP_USERNAME = "login:image:{0}:{1}";

    /**
     * sso令牌
     */
    public static final String SSO_TOKEN = "sso:token:{0}";

    /**
     * sso令牌名称空间超时
     */
    public static final long SSO_TOKEN_NAMESPACE_TIMEOUT = 60 * 60 * 24 * 7;

}
