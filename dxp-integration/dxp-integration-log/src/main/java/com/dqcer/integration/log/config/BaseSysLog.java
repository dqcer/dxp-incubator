package com.dqcer.integration.log.config;

import java.util.Date;

/**
 * @author dongqin
 * @description 基础系统日志
 * @date 2021/09/15
 */
public class BaseSysLog {


    /**
     * 主键
     */
    private Long id;

    /**
     * 模块
     */
    private String module;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求ip
     */
    private String ip;


    /**
     * 当前访问语言
     */
    private String language;


    /**
     * 请求参数 json
     */
    private String param;

    /**
     * 响应消息
     */
    private String resultMsg;

    /**
     * 响应code
     */
    private Integer resultCode;

    /**
     * 操作人
     */
    private Long createdBy;

    /**
     * 操作时间
     */
    private Date createdTime;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "BaseSysLog{" +
                "id=" + id +
                ", requestUrl='" + requestUrl + '\'' +
                ", ip='" + ip + '\'' +
                ", language='" + language + '\'' +
                ", param='" + param + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", resultCode=" + resultCode +
                ", createdBy=" + createdBy +
                ", createdTime=" + createdTime +
                '}';
    }
}
