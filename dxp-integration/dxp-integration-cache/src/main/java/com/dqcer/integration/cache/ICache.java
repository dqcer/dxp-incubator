package com.dqcer.integration.cache;


/**
 * @author dqcer
 * @description 缓存
 * @DATE 22:21 2021/4/28
 */
public interface ICache {


    /**
     * get
     *
     * @param key    关键
     * @param type 类型
     * @return {@link T}
     */
    <T> T get(String key,  Class<T> type);


    /**
     * set
     *
     * @param key    关键
     * @param value  价值
     * @param expire 到期
     */
    <T> void put(String key, T value, long expire);


    /**
     * 驱逐
     *
     * @param keys 关键
     */
    <T> void evict(String... keys);


}
