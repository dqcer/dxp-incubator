package com.dqcer.tools.core;

import org.junit.jupiter.api.Test;

/**
 * @author dongqin
 * @description base64 util测试
 * @date 2021/10/08 20:10:59
 */
class Base64UtilTest {

    @Test
    void encoder() {
        String encoder = Base64Util.encoder("1!@#$%^");
        System.out.println(encoder);
    }

    @Test
    void decoder() {
    }
}