package com.dqcer.framework.storage;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author dongqin
 * @description 统一参数
 * @date 2021/11/13
 */
public class UnifyParameter {

    /**
     * 参数 toString
     */
    private String parameter;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 请求头
     */
    private Map<String,Object> header;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UnifyParameter{");
        sb.append("parameter='").append(parameter).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", header=").append(header);
        sb.append('}');
        return sb.toString();
    }

    public String getParameter() {
        return parameter;
    }

    public UnifyParameter setParameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public UnifyParameter setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public UnifyParameter setHeader(Map<String, Object> header) {
        this.header = header;
        return this;
    }
}
