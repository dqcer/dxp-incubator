package com.dqcer.integration.operation;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dongqin
 * @description redisson对象
 * @date 2021/09/10
 */
public class RedissonObject {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 设置值
     *
     * @param key        key
     * @param value      值
     * @param timeToLive 生存时间 单位（秒）
     */
    public <T> void setValue(String key, T value, long timeToLive) {
        RBucket<T> bucket = redissonClient.getBucket(key);

        bucket.set(value, timeToLive, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置值（默认不过期）
     *
     * @param key   关键
     * @param value 值
     */
    public <T> void setValue(String key, T value) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 获取值
     *
     * @param key 关键
     * @return {@link Object}
     */
    public Object getValue(String key) {
        return redissonClient.getBucket(key).get();
    }

    /**
     * 删除值
     *
     * @param key 关键
     * @return boolean
     */
    public <T> boolean delValue(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.delete();
    }



}

