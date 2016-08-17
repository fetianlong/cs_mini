package com.dearho.cs.teldintrf.entity;

import java.io.Serializable;
import java.util.Map;

public class RequestMsgHeader implements Serializable{

	private static final long serialVersionUID = -2592416349732418639L;

	private Map<String, String> requestMsg;
	private String sign;
	private String encrypt;
	public Map<String, String> getRequestMsg() {
		return requestMsg;
	}
	public void setRequestMsg(Map<String, String> requestMsg) {
		this.requestMsg = requestMsg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	
	
	
}
