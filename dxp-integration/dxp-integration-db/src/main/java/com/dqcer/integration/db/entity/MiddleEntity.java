package com.dqcer.integration.db.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

/**
 * @author dongqin
 * @description 针对中间1对多、多对多关联表
 * @date 2021/03/21 20:08:09
 */
public abstract class MiddleEntity<ID> extends Entity<ID> {

    /**
     * 创建时间，方便增量同步
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
