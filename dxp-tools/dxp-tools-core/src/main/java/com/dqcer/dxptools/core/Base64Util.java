package com.dqcer.dxptools.core;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author dongqin
 * @description base64工具类
 * @date 2021/10/08 20:10:17
 */
public class Base64Util {

    private Base64Util(){}


    /**
     * 编码
     *
     * @param source 源
     * @return {@link String}
     */
    public static String encoder(String source)  {
        return encoderByte(source.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 编码
     *
     * @param bytes 字节
     * @return {@link String}
     */
    public static String encoderByte(byte[] bytes)  {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解码
     *
     * @param base64encodedString 字符串
     * @return {@link String} 解码后的值
     */
    public static String decoder(String base64encodedString) {
        return new String(decoderByte(Base64.getDecoder().decode(base64encodedString)), StandardCharsets.UTF_8);
    }


    /**
     * 解码器字节
     * 解码
     *
     * @param bytes 字节
     * @return {@link byte[]}
     */
    public static byte[] decoderByte(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }


}
