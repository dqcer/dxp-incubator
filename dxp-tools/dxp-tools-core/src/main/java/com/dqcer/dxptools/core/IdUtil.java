package com.dqcer.dxptools.core;

import java.util.UUID;

/**
 * @author dongqin
 * @description id工具
 * @date 2021/08/13
 */
public class IdUtil {

    /**
     * 禁止实例化
     */
    private IdUtil() {
        throw new AssertionError();
    }

    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
