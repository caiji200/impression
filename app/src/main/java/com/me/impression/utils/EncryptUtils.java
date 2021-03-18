package com.me.impression.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class EncryptUtils {

    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @return the hex string of MD5 encryption
     */
    public static String encryptMD5ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @param salt The salt.
     * @return the hex string of MD5 encryption
     */
    public static String encryptMD5ToString(final String data, final String salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return ConvertUtils.bytes2HexString(encryptMD5(data.getBytes()));
        if (data == null) return ConvertUtils.bytes2HexString(encryptMD5(salt.getBytes()));
        return ConvertUtils.bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @return the hex string of MD5 encryption
     */
    public static String encryptMD5ToString(final byte[] data) {
        return ConvertUtils.bytes2HexString(encryptMD5(data));
    }

    /**
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @param salt The salt.
     * @return the hex string of MD5 encryption
     */
    public static String encryptMD5ToString(final byte[] data, final byte[] salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return ConvertUtils.bytes2HexString(encryptMD5(data));
        if (data == null) return ConvertUtils.bytes2HexString(encryptMD5(salt));
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return ConvertUtils.bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * Return the bytes of MD5 encryption.
     *
     * @param data The data.
     * @return the bytes of MD5 encryption
     */
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * Return the bytes of hash encryption.
     *
     * @param data      The data.
     * @param algorithm The name of hash encryption.
     * @return the bytes of hash encryption
     */
    static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
