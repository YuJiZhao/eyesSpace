package com.eyes.eyesspace.common.service.utils;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
    /*
     *****************************************************************************************
     *                                      单向散列加密
     *****************************************************************************************
     */

    private static final String DEFAULT_MD_ALGORITHM = "SHA1";
    private static final Integer HASH_NUM = ConfigContext.getInt("HASH_NUM");

    /**
     * 单向加密，采用加密方式：SHA1
     * @param str
     */
    public static String OneWayHash(String str) throws CustomException {
        return OneWayHash(str, DEFAULT_MD_ALGORITHM);
    }

    /**
     * 单向加密，自定义加密方式
     * @param str
     * @param algorithm 可选值为MD5，SHA-1等9种算法，详情见下面链接
     * @link: https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest
     */
    public static String OneWayHash(String str, String algorithm) throws CustomException {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] srcBytes = str.getBytes();
            for (int i = 0; i < HASH_NUM; i++) {
                md.update(srcBytes);
                srcBytes = md.digest();
            }
            return Base64.encodeBase64String(srcBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException("不存在该散列算法！");
        }
    }

    /*
     *****************************************************************************************
     *                                      双向加密
     *****************************************************************************************
     */

    // 对称加密算法
    private static final String SYMMETRIC_ALGORITHM = ConfigContext.getString("SYMMETRIC_ALGORITHM");
    // 对称加密密钥
    private static final String SYMMETRIC_SECRET_KEY = ConfigContext.getString("SYMMETRIC_SECRET_KEY");

    /**
     * 对称加密字符串
     * @return
     * @throws Exception
     */
    public static String symmetricEncrypt(String str) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SYMMETRIC_SECRET_KEY.getBytes(), SYMMETRIC_ALGORITHM);
        Cipher cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encipherByte = cipher.doFinal(str.getBytes());

        // 防止乱码，使用Base64编码
        return Base64.encodeBase64String(encipherByte);
    }

    /**
     * 对称解密
     * @return
     */
    public static String symmetricDecrypt(String str) throws Exception{
        SecretKeySpec key = new SecretKeySpec(SYMMETRIC_SECRET_KEY.getBytes(), SYMMETRIC_ALGORITHM);
        Cipher cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode = Base64.decodeBase64(str);
        byte[] decipherByte = cipher.doFinal(decode);
        return new String(decipherByte);
    }

    /*
     *****************************************************************************************
     *                                     其他业务工具
     *****************************************************************************************
     */

    private static final String KEY = ConfigContext.getString("AES_KEY");
    private static final String IV =  ConfigContext.getString("AES_IV");

    // AES解密前端数据
    public static String decryptFront(String cipherStr) throws CustomException {
        // 对于前端的密文要先base64解密
        BASE64Decoder decoder = new BASE64Decoder();
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return new String(cipher.doFinal(decoder.decodeBuffer(cipherStr)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CustomException("邮箱格式错误");
        }
    }
}
