package com.dqcer.tools.core;

import java.security.MessageDigest;

public class MD5Util {

    /**
     * 禁止实例化
     */
    private MD5Util() {
        throw new AssertionError();
    }

    /**
     * 获取md5
     *
     * @param message 消息
     * @return {@link String}
     */
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);
            md5 = bytesToHex(md5Byte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }


    /**
     * 二进制转十六进制
     *
     * @param bytes 字节
     * @return {@link String}
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
