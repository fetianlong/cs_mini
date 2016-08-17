package com.dearho.cs.subscriber.pojo;

import java.io.Serializable;
import java.util.Date;

public class SubscriberPhoneChangeRecord implements Serializable {

	private static final long serialVersionUID = -4982401278905665213L;

	private String id;
	private String oldPhoneNo;
	private String newPhoneNo;
	private Integer changerType;
	private String changerId;
	private Date ts;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldPhoneNo() {
		return oldPhoneNo;
	}
	public void setOldPhoneNo(String oldPhoneNo) {
		this.oldPhoneNo = oldPhoneNo;
	}
	public String getNewPhoneNo() {
		return newPhoneNo;
	}
	public void setNewPhoneNo(String newPhoneNo) {
		this.newPhoneNo = newPhoneNo;
	}
	public Integer getChangerType() {
		return changerType;
	}
	public void setChangerType(Integer changerType) {
		this.changerType = changerType;
	}
	public String getChangerId() {
		return changerId;
	}
	public void setChangerId(String changerId) {
		this.changerId = changerId;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
	
	
}
