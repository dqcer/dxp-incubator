package com.dqcer.integration.cache.operation;

import com.dqcer.integration.cache.ICache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author dqcer
 * @description Caffeine缓存
 * @DATE 22:21 2021/4/28
 */
public class CaffeineCache implements ICache {

    private int expire = 30;

    protected static final Logger log = LoggerFactory.getLogger(CaffeineCache.class);

    private Cache<String, Object> cache;

    public CaffeineCache() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        Caffeine<Object, Object> builder = caffeine.maximumSize(1000);
        builder.initialCapacity(3000);
        //  基于引用回收
        //  软引用：如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。
        //  弱引用：弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存
        builder.softValues();
        //  回收策略 每写入一次重新计算一次缓存的有效时间
        builder.expireAfterWrite(expire, TimeUnit.SECONDS);

        //  回收策略 每访问一次重新计算一次缓存的有效时间
        /** builder.expireAfterAccess(expire, TimeUnit.SECONDS);*/
        cache = caffeine.build();
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
        Object o = cache.getIfPresent(key);
        if (null != o) {
            if (type.isInstance(o)) {
                throw new IllegalArgumentException("缓存值的类型不能是" + type.getName());
            }
            if (log.isDebugEnabled()) {
                log.debug("caffeine缓存 key={} 缓存已命中", key);
            }
            return (T) o;
        }
        return null;
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
        if (log.isDebugEnabled()) {
            log.debug("caffeine缓存 key={} 缓存已存入", key);
        }
        cache.put(key, value);
    }

    /**
     * 驱逐
     *
     * @param keys 关键
     */
    @Override
    public <T> void evict(String... keys) {
        if (log.isDebugEnabled()) {
            for (String key : keys) {
                log.debug("caffeine缓存 key={} 缓存已删除", key);
            }
        }
        cache.invalidateAll(Arrays.asList(keys));
    }
}
