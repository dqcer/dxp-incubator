package com.dqcer.framework.entity.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @author dongqin
 * @description 基础的实体
 * @date 2022/01/12
 */
@SuppressWarnings("unused")
public abstract class BaseEntity extends SuperId {

    /**
     * 创建时间, 默认填充
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createdTime;

    /**
     * 创建人
     */
    protected Long createdBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Date updatedTime;

    /**
     * 更新人
     */
    protected Long updatedBy;

    /**
     * 状态, 默认填充
     */
    @TableField(fill = FieldFill.INSERT)
    protected Integer status;


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
