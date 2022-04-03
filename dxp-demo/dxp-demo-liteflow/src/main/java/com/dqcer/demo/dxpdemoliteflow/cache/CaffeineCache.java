package com.dqcer.demo.dxpdemoliteflow.cache;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class CaffeineCache implements Level1Cache {

    private Cache<String, Object> cache;

    private long size ;
    private long expire ;

    public CaffeineCache(Cache<String, Object> cache, long size, long expire) {
        this.cache = cache;
        this.size = size;
        this.expire = expire;
    }

    @Override
    public long ttl() {
        return expire;
    }

    @Override
    public long size() { return size; }

    @Override
    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public Map<String, Object> get(Collection<String> keys) {
        return cache.getAllPresent(keys);
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void put(Map<String, Object> elements) {
        cache.putAll(elements);
    }

    @Override
    public Collection<String> keys() {
        return cache.asMap().keySet();
    }

    @Override
    public void evict(String...keys) {
        cache.invalidateAll(Arrays.asList(keys));
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }
}

