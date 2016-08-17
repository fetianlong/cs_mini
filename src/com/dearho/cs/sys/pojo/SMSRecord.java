package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

public class SMSRecord implements Serializable{
	
	
	public static final Integer TYPE_REGISTER=1;//注册
	public static final Integer TYPE_CONFIRM=2;//审核
	public static final Integer TYPE_ORDER=3;//订单
	public static final Integer TYPE_NOTICE=4;//违章通知
	public static final Integer TYPE_CODE=5;//验证码
	public static final Integer TYPE_REFUND=6;//退款
	public static final Integer TYPE_PWD=7;//修改密码
	
	
	public static String getType(Integer type) {
		if(type==null){
			return null;
		}else if(TYPE_REGISTER.equals(type)){
			return "注册";
		}else if(TYPE_CONFIRM.equals(type)){
			return "会员审核";
		}else if(TYPE_ORDER.equals(type)){
			return "订单";
		}else if(TYPE_NOTICE.equals(type)){
			return "违章通知";
		}else if(TYPE_CODE.equals(type)){
			return "验证码";
		}else if(TYPE_REFUND.equals(type)){
			return "退款";
		}else if(TYPE_PWD.equals(type)){
			return "修改密码";
		}else{
			return "";
		}
		
	}
	
	private String id;
	private String phoneNo;
	private Integer type;
	private String content;
	private String userId;
	private String userName;
	private String result;
	//private String status;
	private Date ts;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
}
