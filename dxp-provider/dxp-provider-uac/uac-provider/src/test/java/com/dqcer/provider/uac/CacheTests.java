package com.dqcer.provider.uac;

import com.dqcer.integration.cache.operation.CacheChannel;
import com.dqcer.integration.cache.operation.CaffeineCache;
import com.dqcer.integration.cache.operation.RedissonCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author dqcer
 * @description 缓存测试，单元测试过程中看日志
 * @DATE 22:21 2021/4/28
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CacheTests {

    @Resource
    private CacheChannel cacheChannel;

    @Resource
    private CaffeineCache caffeineCache;

    @Resource
    private RedissonCache redissonCache;

    /**
     * 测试caffeine缓存
     */
    @Test
    public void testCaffeineCache() {
        // 这里的过期时间无效，全局默认5s
        caffeineCache.put("name", "good job", 1);
        System.out.println(caffeineCache.get("name", String.class));
    }

    /**
     * 测试redis缓存
     */
    @Test
    public void testRedisCache() {
        // 这里的过期时间无效，全局默认5s
        redissonCache.put("name", "good job", 1);
        System.out.println(redissonCache.get("name", String.class));
    }

    /**
     * 测试一级缓存
     */
    @Test
    public void testLevel1Cache() {
        // 这里的过期时间无效，全局默认5s
        caffeineCache.put("name", "good job", 1);
        System.out.println(cacheChannel.get("name", String.class));
    }

    /**
     * 测试二级缓存
     */
    @Test
    public void testLevel2Cache() {
        // 这里的过期时间无效，全局默认5s
        redissonCache.put("name", "good job", 1);
        System.out.println(cacheChannel.get("name", String.class));
    }



    /**
     * 测试多级缓存，期望：优先命中caffeine缓存，再命中redis缓存
     *
     * @throws InterruptedException 中断异常
     */
    @Test
    public void testMultistageCache() throws InterruptedException {
        cacheChannel.put("name", "good job", 20);
        String name = null;
        do {
            Thread.sleep(5000L);
             name = cacheChannel.get("name", String.class);
            System.out.println(name);
        } while (name != null);
    }
}
