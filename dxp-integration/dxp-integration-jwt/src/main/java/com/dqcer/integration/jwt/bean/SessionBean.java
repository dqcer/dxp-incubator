package com.dqcer.integration.jwt.bean;

import java.io.Serializable;
import java.util.Date;

public class SessionBean implements Serializable {

    private static final long serialVersionUID = -4248500637703736978L;

    private Long id;

    private Date createdTime;

    /**
     * 令牌 针对挤下线
     */
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SessionBean(Long id, String token) {
        this.id = id;
        this.createdTime = new Date();
        this.token = token;
    }


}
