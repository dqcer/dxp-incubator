package com.dqcer.dxptools.core;

import java.util.UUID;

/**
 * @author dongqin
 * @description id工具
 * @date 2021/08/13
 */
public class IdUtil {

    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
