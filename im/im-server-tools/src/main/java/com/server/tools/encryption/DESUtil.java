package com.server.tools.encryption;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 用来加密、解密数据库配置信息properties
 * @author shiql
 *
 */
public class DESUtil {
	public static final String KEY_ALGORITHM = "ilonw_des";
	public static final String KEY = "A1B2C3D4E5F60708";
    //算法名称/加密模式/填充方式 
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

    /**
     *   
     * 生成密钥key对象
     * @param KeyStr 密钥字符串 
     * @return 密钥对象 
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     * @throws InvalidKeySpecException   
     * @throws Exception 
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = new DESKeySpec(input);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换 
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /** 
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密后的数据 
     */
    public static String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecureRandom random = new SecureRandom();
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
        byte[] results = cipher.doFinal(data.getBytes());
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
        return Base64.encodeBase64String(results);
    }

    /** 
     * 解密数据 
     * @param data 待解密数据 
     * @param key 密钥 
     * @return 解密后的数据 
     */
    public static String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 执行解密操作
        return new String(cipher.doFinal(Base64.decodeBase64(data)));
    }

    /**
     * 
     * @param DES需要字符串长度为8的倍数，所以这里填充空格，保证为8的整数倍
     * @throws Exception
     */
    public static String fillingVal(String value){
    	int length = value.getBytes().length;
    	if(length%8!=0){
    		int addNum = 8-length%8;
    		for(int i=0;i<addNum;i++){
    			value = value+" ";
    		}
    		return value;
    	}else{
    		return value;
    	}
    }
    
    /**
     * 加密入口
     * @return 加密后的值
     */
    public static String encryptVal(String value) throws Exception{
    	String result = "";
    	try{
    		String val = fillingVal(value);
        	result = encrypt(val, KEY);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    
    /**
     * 解密入口
     * @param args
     * @throws Exception
     */
    public static String decipherVal(String value){
    	String result = "";
    	try{
    		result = decrypt(value, KEY).trim();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return result;
    }
    

    
    public static void main(String[] args) throws Exception {
        String source = "root";
        System.out.println("原文: " + source);
        String encryptData = encryptVal(source);
        System.out.println("加密后: " + encryptData);
        String decryptData = decipherVal(encryptData);
        System.out.println("解密后: " + decryptData);
    }
}