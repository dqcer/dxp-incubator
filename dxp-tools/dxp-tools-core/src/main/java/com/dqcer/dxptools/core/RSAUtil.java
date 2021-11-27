package com.dqcer.dxptools.core;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author dongqin
 * @description rsa util
 * @date 2021/10/08 21:10:34
 */
public class RSAUtil {

    private static final Logger log = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 禁止实例化
     */
    private RSAUtil() {
        throw new AssertionError();
    }


    /**
     * RSA加密算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 最大RSA加密明文的大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA解密密文最大size
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * 加密
     *
     * @param data      data
     * @param publicKey publicKey
     * @return byte
     */
    public static byte[] encrypt(byte[] data, String publicKey) {
        byte[] keyBytes = Base64Util.decoderByte(publicKey.getBytes(StandardCharsets.UTF_8));
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            return getBytes(data, cipher, MAX_ENCRYPT_BLOCK);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException
                | NoSuchPaddingException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException
                | IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param text       text
     * @param privateKey privateKey
     * @return byte
     */
    public static byte[] decrypt(byte[] text, String privateKey)  {
        byte[] keyBytes = Base64Util.decoderByte(privateKey.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            return getBytes(text, cipher, MAX_DECRYPT_BLOCK);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取字节
     *
     * @param data            数据
     * @param cipher          密码
     * @param maxEncryptBlock 加密块
     * @return {@link byte[]}
     * @throws BadPaddingException       security exception
     * @throws IllegalBlockSizeException security exception
     * @throws IOException               io exception
     */
    private static byte[] getBytes(byte[] data, Cipher cipher, int maxEncryptBlock) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // Sectional Encryption of Data
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}
