package com.dqcer.integration.cache.operation;

import com.dqcer.integration.cache.ICache;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dongqin
 * @description redisson对象
 * @date 2021/09/10
 */
@Component
public class RedissonCache implements ICache {

    protected static final Logger log = LoggerFactory.getLogger(RedissonCache.class);

    @Resource
    private RedissonClient redissonClient;

    @Override
    public <T> T get(String key, Class<T> type) {
        Object o = redissonClient.getBucket(key).get();
        if (null != o) {
            if (!type.isInstance(o)) {
                throw new IllegalArgumentException("缓存值的类型不能是" + type.getName());
            }
            if (log.isDebugEnabled()) {
                log.debug("redis缓存 key={} 缓存已命中", key);
            }
            return (T) o;
        }
        return null;
    }

    @Override
    public <T> void put(String key, T value, long expire) {
        if (log.isDebugEnabled()) {
            log.debug("redis缓存 key={} 缓存已存入", key);
        }
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, expire, TimeUnit.SECONDS);
    }

    @Override
    public <T> void evict(String... keys) {
        for (String key : keys) {
            if (log.isDebugEnabled()) {
                log.debug("redis缓存 key={} 缓存已删除", key);
            }

            RBucket<T> bucket = redissonClient.getBucket(key);
            bucket.delete();
        }
    }
}

