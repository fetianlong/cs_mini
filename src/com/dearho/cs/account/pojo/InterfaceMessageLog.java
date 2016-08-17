package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;

public class InterfaceMessageLog implements Serializable{
	
	private static final long serialVersionUID = -6928956549941091511L;
	
	
	public static final Integer IS_SEND_TRUE=1;
	public static final Integer IS_SEND_FALSE=0;
	
	
	private String id;
	private Integer type;   //充值，订单
	private Integer channel;//渠道
    private String bizId;  //业务关联id
   // private Integer isSend;//
//	private String content;//报文内容
    private String respCode;
	private String requestMessage;
	private String responseMessage;
	private Date ts;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	
	
	
}
