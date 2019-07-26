package com.cn.miao.common.toolkit;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

/**
 * @title: CryptUtil
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 14:07
 **/
public class CryptUtil {

    /**
     * BASE64的加密解密是双向的，可以求反解。
     * MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。
     * HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。
     * DES DES-Data Encryption Standard,即数据加密算法。 DES算法的入口参数有三个:Key、Data、Mode。
     *      Key:8个字节共64位,是DES算法的工作密钥;
     *      Data:8个字节64位,是要被加密或被解密的数据;
     *      Mode:DES的工作方式,有两种:加密或解密。
     */

    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA";
    /**
     * MAC算法可选以下多种算法
     * <p>
     * <pre>
     *
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    private static final String KEY_MAC = "HmacMD5";

    private static String KEY_VALUE = "";

    static {
        try {
            KEY_VALUE = initKey("v7Pg5fiYwek");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * BASE64解密
     */
    private static byte[] decryptBASE64(String key) throws Exception {
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Decoder decoder = Base64.getDecoder();
        // 1.9之后的替代方案
        return decoder.decode(key);
        //return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64 加密
     */
    private static String encryptBASE64(byte[] key) throws Exception {
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key);
        //return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     */
    private static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * MD5加密
     */
    public static String encryptMD5(String data) throws Exception {
        //加密后的字符串
        String result = new String(DigestUtil.md5Hex(data));
        return result;
    }

    /**
     * SHA加密
     */
    private static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * 初始化HMAC密钥
     */
    private static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC 加密
     */
    private static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * DES 算法 <br> 可替换为以下任意一种算法，同时key值的size相应改变。 <p>
     * <pre>
     * DES                  key size must be equal to 56
     * DESede(TripleDES)    key size must be equal to 112 or 168
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be
     * available
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448
     * (inclusive)
     * RC2                  key size must be between 40 and 1024 bits
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
     * </pre>
     */
    private static final String ALGORITHM = "DES";

    /**
     * DES 算法转换密钥<br>
     */
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = null;
        if (ALGORITHM.equals("DES") || ALGORITHM.equals("DESede")) {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            secretKey = keyFactory.generateSecret(dks);
        } else {
            // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
            secretKey = new SecretKeySpec(key, ALGORITHM);
        }
        return secretKey;
    }

    /**
     * DES 算法解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        // 执行解密操作
        return new String(decrypt(Base64.getDecoder().decode(data), key), "UTF-8");
        //return new String(decrypt(Base64.decodeBase64(data), key), "UTF-8");
    }

    /**
     * DES 算法加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(data.getBytes("UTF-8"), key));
        //return Base64.encodeBase64String(encrypt(data.getBytes("UTF-8"), key));
    }

    /**
     * DES 算法生成密钥
     */
    private static String initKey() throws Exception {
        return initKey(null);
    }

    /**
     * DES 算法生成密钥
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;
        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    public static String encryptHMAC(String password) {
        String result = null;
        if (!StrUtil.isEmpty(password)) {
            try {
                byte[] c = encryptHMAC(password.getBytes(), initMacKey());
                result = new BigInteger(c).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String encrypt(String password) {
        String result = null;
        try {
            byte[] c = encrypt(password.getBytes(), KEY_VALUE);
            result = new BigInteger(c).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将图像文件base64编码为字符串
     * @方法名称: handleImage
     * @方法描述:
     * @param filePath 文件路径
     * @return
     */
    public static String handleImage(String filePath){
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {

        }
        Encoder encoder = Base64.getEncoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encodeToString(data);
    }

    /**
     * base64字符串转化为图片
     * @方法名称: handleString
     * @方法描述:
     * @param imgStr
     * @param savePath
     * @return
     */
    public static boolean handleString(String imgStr,String savePath){
        if (imgStr == null) {
            return false;
        }
        Decoder decoder = Base64.getDecoder();
        try {
            byte[] b = decoder.decode(imgStr);
            for(int i=0;i<b.length;++i){
                //调整异常数据
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(savePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            String s = "{}";
            String b = CryptUtil.encryptBASE64(s.getBytes("UTF-8"));
            //log.info("BASE64加密后:" + b);
            byte[] c = CryptUtil.decryptBASE64(b);
            //log.info("BASE64解密后:" + new String(c, "UTF-8"));

            //c = encryptMD5(s.getBytes());
            //log.info("MD5   加密后:" + new BigInteger(c).toString(16));
            //
            //c = encryptSHA(s.getBytes());
            //log.info("SHA   加密后:" + new BigInteger(c).toString(16));
            //
            String key = initMacKey();
            //log.info("HMAC密匙:" + key);
            //c = encryptHMAC(s.getBytes(), key);
            //log.info("HMAC  加密后:" + new BigInteger(c).toString(16));
            //
            //log.info("111111111111HMAC  加密后:" + encryptHMAC("123"));

            key = initKey();
            System.out.println(ALGORITHM + "密钥:\t" + key);
            String en = encrypt(s, key);
            System.out.println(ALGORITHM + " String   加密后:" + en);

            String de = decrypt(en, key);
            System.out.println(ALGORITHM + " String  解密后:" + de);
            //log.info(ALGORITHM + "密钥:\t" + key);
            c = encrypt(s.getBytes("UTF-8"), key);
            System.out.println(ALGORITHM + "   加密后:" + new BigInteger(c).toString(16));
            //log.info(ALGORITHM + "   加密后:" + new BigInteger(c).toString(16));
            c = decrypt(c, key);
            System.out.println(ALGORITHM + "   解密后:" + new String(c, "UTF-8"));
            //log.info(ALGORITHM + "   解密后:" + new String(c, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(handleImage("E:\\qrCode\\comic.jpg"));
        String base64 =
            "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";
        handleString(base64, "E://qrCode/new.jpg");
    }
}
