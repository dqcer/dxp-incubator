package com.dqcer.integration.jwt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author dongqin
 * @description aes util AES-128-ECB
 * @date 2021/09/28 12:09:92
 */
public class AESUtil {

    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 字符集名称
     */
    private static final String CHARSET_NAME = "utf-8";

    /**
     * key
     */
    private static final String KEY = "^`*()_+{}|:\"\\<>?";

    /**
     * 算法
     */
    private static final String ALGORITHM = "AES";

    /**
     *  算法/模式/补码方式
     */
    private static final String TRANSFORMATION_GIVEN = "AES/ECB/PKCS5Padding";


    /**
     * 加密
     *
     * @param data 明文
     * @return {@link String}
     */
    public static String encrypt(String data) {
        try {
            byte[] raw = KEY.getBytes(CHARSET_NAME);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_GIVEN);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes(CHARSET_NAME));
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param data 密文
     * @return {@link String}
     */
    public static String decrypt(String data) {
        try {
            byte[] raw = KEY.getBytes(CHARSET_NAME);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_GIVEN);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original,CHARSET_NAME);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args)  {
        String cSrc = "1111111111111111111";
        System.out.println(cSrc);
        String enString = AESUtil.encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString);
        String DeString = AESUtil.decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
    }
}
