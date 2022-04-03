package com.dqcer.framework.entity;

import java.io.Serializable;

/**
 * @author dqcer
 * @description 实体
 * @date  22:21 2021/4/28
 */
@SuppressWarnings("unused")
public interface Entity extends Serializable {

    /**
     * 主键约束
     *
     * @return {@link Long}
     */
    Long getId();

}
