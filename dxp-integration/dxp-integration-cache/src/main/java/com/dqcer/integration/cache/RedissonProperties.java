package com.dqcer.integration.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dongqin
 * @description redisson配置
 * @date 2021/09/09
 */
@ConfigurationProperties(prefix = "spring.redis.redisson")
public class RedissonProperties {

    private String config;

    private String file;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
