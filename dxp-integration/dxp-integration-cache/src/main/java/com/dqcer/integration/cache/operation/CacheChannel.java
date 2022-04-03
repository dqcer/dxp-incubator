package com.dqcer.integration.cache.operation;

import com.dqcer.integration.cache.ICache;


/**
 * @author dqcer
 * @description 缓存通道
 * @DATE 22:21 2021/4/28
 */
public class CacheChannel implements ICache{

    private ICache redisCache;

    private ICache caffeineCache;

    public CacheChannel(ICache caffeineCache, ICache redisCache) {
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;
    }

    /**
     * get
     *
     * @param key  关键
     * @param type 类型
     * @return {@link T}
     */
    @Override
    public <T> T get(String key, Class<T> type) {
        T t = caffeineCache.get(key, type);
        if (null != t) {
            return t;
        }
        return redisCache.get(key, type);
    }

    /**
     * set
     *
     * @param key    关键
     * @param value  价值
     * @param expire 到期
     */
    @Override
    public <T> void put(String key, T value, long expire) {
        caffeineCache.put(key, value, expire);
        redisCache.put(key, value, expire);
    }

    /**
     * 驱逐
     *
     * @param keys 关键
     */
    @Override
    public <T> void evict(String... keys) {
        caffeineCache.evict(keys);
        redisCache.evict(keys);
    }
}
