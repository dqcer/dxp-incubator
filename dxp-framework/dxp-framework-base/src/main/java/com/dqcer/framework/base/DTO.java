package com.dqcer.framework.base;

import java.io.Serializable;

/**
 * @author dqcer
 * @description 统一接收客户端参数定义
 * @date 22:21 2021/4/28
 */
public abstract class DTO implements Serializable {

    /**
     * 子类需实现
     *
     * @return {@link String}
     */
    @Override
    public abstract String toString();
}
