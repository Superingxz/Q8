package com.xologood.q8pad.utils;

import android.util.Base64;

import com.mview.medittext.utils.QpadJudgeUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class EncroptionUtils {
    public static final String KEY_SHA = "SHA-1";
    public static final String KEY_MD5 = "MD5";
    /**
     * MAC算法可选以下多种算法
     * 
     * <pre>
     * HmacMD5 
     * HmacSHA1 
     * HmacSHA256 
     * HmacSHA384 
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";
	
	/**
     * BASE64解密
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.encode(key.getBytes(), Base64.DEFAULT);
    }
 
    /**
     * BASE64加密
     */
    public static String encryptBASE64(byte[] key) throws Exception {
    	return Base64.encodeToString(key, Base64.DEFAULT);
    }
 
    /**
     * MD5加密
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
 
    }
    
    /**
     * MD5加密
     */
    public static String encryptMD5ToString(String data) throws RuntimeException {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("md5");
        }

        byte[] results = md5.digest(data.getBytes());
        return toHex(results);
    }

    private static String toHex(byte[] results) {
        if (results == null)
            return null;
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < results.length; i++) {
            int hi = (results[i] >> 4) & 0x0f;
            int lo = results[i] & 0x0f;
            hexString.append(Character.forDigit(hi, 16)).append(
                    Character.forDigit(lo, 16));
        }
        return hexString.toString();
    }
    
    /**
     * SHA加密
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
 
    }
    
    /**
     * 自定义 SHA加密
     */
    public static String EncryptSHA(String strPwd) {
		String strEncrypted = "";
		if (QpadJudgeUtils.isEmpty(strPwd)) {
			return strEncrypted;
		} else {
			try {
				byte shaKeyBytes[] = { -93, 53, 72, 42, 33, 36, -125, -26, -65,
						-77, 114, -8, 65, -33, 12, -53 };
				MessageDigest alga = MessageDigest.getInstance(KEY_SHA);
				alga.update(shaKeyBytes);
				alga.update(strPwd.getBytes());
				alga.update(shaKeyBytes);
				byte digesta[] = alga.digest();
				strEncrypted = QpadConfigUtils.byte2hex(digesta);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return strEncrypted;
		}
	}
    
    /**
     * 初始化HMAC密钥
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }
 
    /**
     * HMAC加密
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
 
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
 
        return mac.doFinal(data);
    }

}
