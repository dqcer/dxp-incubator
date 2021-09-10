package com.dqcer.integration.cache;

import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class DxpIntegrationCacheApplicationTests {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonConnectionFactory redissonConnectionFactory;

    @Resource
    private RedissonReactiveClient redissonReactive;

    @Resource
    private RedissonRxClient redissonRxJava;


    @Resource
    private RedissonClient redisson;
}
