package com.dqcer.demo.dxpdemoliteflow.cache;


import java.util.Collection;
import java.util.Map;

public interface Cache {
    /**
     * Get an item from the cache, nontransactionally
     *
     * @param key cache key
     * @return the cached object or null
     */
    Object get(String key) ;

    /**
     * 批量获取缓存对象
     * @param keys cache keys
     * @return return key-value objects
     */
    Map<String, Object> get(Collection<String> keys);

    /**
     * 判断缓存是否存在
     * @param key cache key
     * @return true if key exists
     */
    default boolean exists(String key) {
        return get(key) != null;
    }

    /**
     * Add an item to the cache, nontransactionally, with
     * failfast semantics
     *
     * @param key cache key
     * @param value cache value
     */
    void put(String key, Object value);

    /**
     * 批量插入数据
     * @param elements objects to be put in cache
     */
    void put(Map<String, Object> elements);

    /**
     * Return all keys
     *
     * @return 返回键的集合
     */
    Collection<String> keys() ;

    /**
     * Remove items from the cache
     *
     * @param keys Cache key
     */
    void evict(String...keys);

    /**
     * Clear the cache
     */
    void clear();

}
