package com.dqcer.framework.base.api;

import java.io.Serializable;

/**
 * @author dongqin
 * @description http实体
 * @date 2022/03/11
 */
public interface HttpEntity extends Serializable {

    /**
     * 获取代码
     *
     * @return int
     */
    int getCode();

    /**
     * 获取消息
     *
     * @return {@link String}
     */
    String getMessage();

}
