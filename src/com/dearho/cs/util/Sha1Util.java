/**
 * Copyright (c) CarSharing Team
 * All rights reserved.
 *
 * This file Sha1Util.java creation date: [2015-5-11 上午10:17:50] by wangyx
 * http://www.dearho.com
 */
package com.dearho.cs.util;

import java.security.MessageDigest;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-5-11
 */
public class Sha1Util {
	public static String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	public static void main(String[] args) throws Exception {
		System.out.println(SHA1Encode("123456"));
	}
}