package com.dqcer.dxptools.sync.groovy.demo;

import java.security.MessageDigest;

public class MD5Utils {

    private static final String SLAT = "!@#$%^&*()_+";

    public static final String ALGORITHM = "MD5";


    /**
     * md5 hash
     *
     * @param str str
     * @return {@link String}
     */
    public static String hashMd5(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            str = str + SLAT;
            MessageDigest m = MessageDigest.getInstance(ALGORITHM);
            m.update(str.getBytes("UTF8"));
            byte [] bytes = m.digest();
            int length = bytes.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(Integer.toHexString((0x000000FF & bytes[i]) | 0xFFFFFF00).substring(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
