package com.dearho.cs.util;

import java.security.MessageDigest;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class Md5Util
{
  public static String MD5Encode(String sourceString)
  {
    String resultString = null;
    try {
      resultString = new String(sourceString);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byte2hexString(md.digest(resultString.getBytes()));
    } catch (Exception localException) {
    }
    return resultString;
  }
  public static final String byte2hexString(byte[] bytes) {
    StringBuffer buf = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++) {
      if ((bytes[i] & 0xFF) < 16) {
        buf.append("0");
      }
      buf.append(Long.toString(bytes[i] & 0xFF, 16));
    }
    return buf.toString();
  }
}