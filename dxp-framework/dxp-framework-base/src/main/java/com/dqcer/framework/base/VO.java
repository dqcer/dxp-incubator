package com.dqcer.framework.base;

import java.io.Serializable;

/**
 * @author dqcer
 * @description 统一返回VO定义
 * @date  22:21 2021/4/28
 */
public abstract class VO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子类需实现
     *
     * @return {@link String}
     */
    @Override
    public abstract String toString();
}
