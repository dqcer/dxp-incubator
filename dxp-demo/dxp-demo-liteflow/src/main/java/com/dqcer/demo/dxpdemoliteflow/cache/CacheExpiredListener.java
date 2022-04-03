package com.dqcer.demo.dxpdemoliteflow.cache;

public interface CacheExpiredListener {


    /**
     * 缓存因为超时失效后触发的通知
     * @param region 缓存 region
     * @param key 缓存 key
     */
    void notifyElementExpired(String region, String key) ;
}
