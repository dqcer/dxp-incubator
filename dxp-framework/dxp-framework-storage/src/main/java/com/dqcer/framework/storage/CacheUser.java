package com.dqcer.framework.storage;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dongqin
 * @description 缓存的用户
 * @date 2021/11/14
 */
public class CacheUser implements Serializable {

    /**
     * 在线
     */
    public static final Integer ONLINE = 1;

    /**
     * 挤下线
     */
    public static final Integer OFFLINE = 2;

    private static final long serialVersionUID = 5099451915233476755L;

    /**
     * 帐户id
     */
    private Long accountId;

    /**
     * 1/在线 2/挤下线
     */
    private Integer onlineStatus;

    /**
     * 最后活跃的时间
     */
    private LocalDateTime lastActiveTime;

    public Long getAccountId() {
        return accountId;
    }

    public CacheUser setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public CacheUser setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
        return this;
    }

    public LocalDateTime getLastActiveTime() {
        return lastActiveTime;
    }

    public CacheUser setLastActiveTime(LocalDateTime lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
        return this;
    }
}
