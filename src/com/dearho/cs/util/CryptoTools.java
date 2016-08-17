package com.dearho.cs.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class CryptoTools {

	   private AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
	   private Key key = null;

	   public CryptoTools(byte[] DESkey,byte[] DESIV) throws Exception {
	       DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
	       iv = new IvParameterSpec(DESIV);// 设置向量
	       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
	       key = keyFactory.generateSecret(keySpec);// 得到密钥对象

	   }

	   /**
	    * 加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encode(String data) throws Exception {
	       Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
	       enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
	       byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
//	       BASE64Encoder base64Encoder = new BASE64Encoder();
	       Base64Tool base64Encoder = new Base64Tool();
	       return base64Encoder.encode(pasByte);
	   }

	   /**
	    * 解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String decode(String data) throws Exception {
	       Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	       deCipher.init(Cipher.DECRYPT_MODE, key, iv);
//	       BASE64Decoder base64Decoder = new BASE64Decoder();
	       Base64Tool base64Decoder = new Base64Tool();
	       byte[] pasByte = deCipher.doFinal(base64Decoder.decode(data));

	       return new String(pasByte, "UTF-8");
	   }
	   public static void main(String[] args) {
	       try {
//	           String test = "{'token':'MGMxYmY5ZmZlZGViOGRmZGQ3YTUyNzIwMjM3MjMxOWJmOTA4MmFhYTk2OTAzM2Fi','oldpwd':'123321','newpwd':'123456'}";
//	           String test = "{'phoneNo':'18810289689','password':'123456'}";
//	    	   String test = "{'token':'ODFmNDFjNDcwMDNlNDIxZTAwMTFhN2JmMmRmYzVkMTgxZTNlZmUzYWJlY2QyNjhj','lat':'39.8056490','lng':'116.5015470'}";
	    	   String test = "{'phoneNo':'18810289689','type':'login'}";
//	    	   String test = "{'phoneNo':'18810289689','smsCode':'4879'}";
//	    	   String test = "{'token':'MGMxYmY5ZmZlZGViOGRmZGQ3YTUyNzIwMjM3MjMxOWJmOTA4MmFhYTk2OTAzM2Fi','oldphone':'18810289689','newCpyzm':'0473','newphone':'18810289680'}";
//	    	   String test = "{'token':'MGMxYmY5ZmZlZGViOGRmZGQ3YTUyNzIwMjM3MjMxOWJmOTA4MmFhYTk2OTAzM2Fi'}";
//	    	   String test = "{'token':'ODFmNDFjNDcwMDNlNDIxZTAwMTFhN2JmMmRmYzVkMTgxZTNlZmUzYWJlY2QyNjhj','name':'侯自伟'}";
	    	   CryptoTools des = new CryptoTools("HeGeA8G3".getBytes(),"6LA2EyQm".getBytes());//自定义密钥
	           System.out.println("加密前的字符："+test);
	           System.out.println("加密后的字符："+des.encode(test));
	           System.out.println("解密后的字符："+des.decode(des.encode(test)));
//	           System.out.println("GTvn6aEwC8sdNh0N1SrZqP6WGdGBdyiP".length());
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
}
