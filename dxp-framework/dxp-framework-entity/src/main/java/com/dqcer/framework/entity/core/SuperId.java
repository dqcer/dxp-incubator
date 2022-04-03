package com.dqcer.framework.entity.core;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dqcer.framework.entity.Entity;


/**
 * @author dongqin
 * @description 主键
 * @date 2022/01/12
 */
@SuppressWarnings("unused")
public abstract class SuperId implements Entity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 只有当插入对象ID 为空，才自动填充
     */
    @TableId(type= IdType.ASSIGN_ID)
    protected Long id;

    /**
     * 规约子类实现toString方法
     *
     * @return {@link String}
     */
    @Override
    public abstract String toString();


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
