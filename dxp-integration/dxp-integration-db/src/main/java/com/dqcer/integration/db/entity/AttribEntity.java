package com.dqcer.integration.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

/**
 * @author dongqin
 * @description 针对单个的实体表
 * @date 2021/03/21 20:08:09
 */
public abstract class AttribEntity<ID> extends Entity<ID> {

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    /**
     * 创建人
     */
    protected Long createdBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected LocalDateTime updatedTime;

    /**
     * 更新人
     */
    protected Long updatedBy;

    /**
     * 状态
     */
    protected Integer status;


    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }


    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



}
