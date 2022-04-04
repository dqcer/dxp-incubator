package com.dqcer.demo.dxpdemoliteflow.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

public class CacheService {

    private Cache cache;

    {
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
                    // TODO: 2022/4/4 二级缓存清理
                }
            }
        });
        int expire = 5;
        if (expire > 0) {
            caffeine = caffeine.expireAfterWrite(expire, TimeUnit.SECONDS);
        }
        com.github.benmanes.caffeine.cache.Cache<String, Object> loadingCache = caffeine.build();
        cache = new CaffeineCache(loadingCache, 500, expire);
    }

    public Object get(String key) {
        Object o = cache.get(key);
        if (null != o) {
            return o;
        }
        return null;
    }

}
