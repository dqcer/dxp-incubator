package com.dqcer.demo.dxpdemoliteflow.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CaffeineCacheTest {

    @Test
    public void testGet() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        Caffeine<Object, Object> objectObjectCaffeine = caffeine.maximumSize(1000);
        objectObjectCaffeine.removalListener(new RemovalListener<Object, Object>() {
            /*
             * 程序删除的缓存不做通知处理，因为上层已经做了处理
             * 当缓存数据不是因为手工删除和超出容量限制而被删除的情况，就需要通知上层侦听器
             */
            @Override
            public void onRemoval(@Nullable Object o, @Nullable Object o2, @Nonnull RemovalCause removalCause) {
                if (removalCause != RemovalCause.EXPLICIT && removalCause != RemovalCause.REPLACED && removalCause != RemovalCause.SIZE) {
//                    CacheExpiredListener.notifyElementExpired(o, o2);
                }
            }
        });
        int expire = 5;
        if (expire > 0) {
            caffeine = caffeine.expireAfterWrite(expire, TimeUnit.SECONDS);
        }
        com.github.benmanes.caffeine.cache.Cache<String, Object> loadingCache = caffeine.build();
        Cache cache = new CaffeineCache(loadingCache, 500, expire);


        cache.put("name", 1232432);
        System.out.println(cache.get("name"));

    }

}