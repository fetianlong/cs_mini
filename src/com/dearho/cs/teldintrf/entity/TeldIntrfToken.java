package com.dearho.cs.teldintrf.entity;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.util.DateUtil;

/**
 * 特来电接口token
 * @author GaoYunpeng
 *
 */
public class TeldIntrfToken implements Serializable{

	private static final long serialVersionUID = 4447877050795171247L;
	
	private String accessToken;
	private String tokenType;
	private Integer expiresIn;
	private Date outTime;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@Override
	public String toString() {
		return "accessToken:"+this.accessToken+";tokenType:"+tokenType+";expiresIn:"+expiresIn+";outTime:"+DateUtil.formatDate(outTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	
}
