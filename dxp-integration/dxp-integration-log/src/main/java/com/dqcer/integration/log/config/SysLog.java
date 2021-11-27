package com.dqcer.integration.log.config;

import java.time.LocalDateTime;

/**
 * @author dongqin
 * @description 基础系统日志
 * @date 2021/09/15
 */
public class SysLog {


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
    private String result;

    /**
     * 响应code
     */
    private Integer resultCode;

    /**
     * 用户会话
     */
    private String userSessionStr;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysLog{");
        sb.append("id=").append(id);
        sb.append(", module='").append(module).append('\'');
        sb.append(", requestUrl='").append(requestUrl).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", param='").append(param).append('\'');
        sb.append(", result='").append(result).append('\'');
        sb.append(", resultCode=").append(resultCode);
        sb.append(", userSessionStr='").append(userSessionStr).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public SysLog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModule() {
        return module;
    }

    public SysLog setModule(String module) {
        this.module = module;
        return this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public SysLog setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public SysLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public SysLog setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getParam() {
        return param;
    }

    public SysLog setParam(String param) {
        this.param = param;
        return this;
    }

    public String getResult() {
        return result;
    }

    public SysLog setResult(String result) {
        this.result = result;
        return this;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public SysLog setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getUserSessionStr() {
        return userSessionStr;
    }

    public SysLog setUserSessionStr(String userSessionStr) {
        this.userSessionStr = userSessionStr;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public SysLog setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public SysLog setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }
}
