package com.dearho.cs.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class DESEncryptHelper {

    private final static String DES = "DES";
    
    private String key = null;
   
    public DESEncryptHelper(String key) {
		this.key = key;
	}
     
     /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public String decrypt(String data) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
        return new String(bt);
    }
    /**
     * 对字符串加密 
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public String getEncryptStr(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        //获取密钥
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keyspec = new DESKeySpec(key.getBytes());
        SecretKey deskey = factory.generateSecret(keyspec);
        // Cipher负责完成加密或解密工作
        Cipher c = Cipher.getInstance("DES");
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式  
        c.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] src = str.getBytes();
        // 该字节数组负责保存加密的结果
        byte[] cipherByte = c.doFinal(src);
        String enstr = new String(Base64.encodeBase64(cipherByte));
        return enstr;
    }
     
     /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
  
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
  
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
  
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
  
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
  
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args) throws IOException, Exception {
    	
//    	System.out.println( getEncryptStr("fldjfddfdfgfgfgfgfdfdfd"));
//    	System.out.println(decrypt("nPA3+3xf1/c="));
		
	}


}
