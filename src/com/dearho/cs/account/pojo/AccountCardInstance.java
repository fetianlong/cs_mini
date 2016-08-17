package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author liusong
 *
 */
public class AccountCardInstance implements Serializable{

	private static final long serialVersionUID = 6957279636846002241L;
	public static Integer IS_VALID_TRUE=1;
	public static Integer IS_VALID_FALSE=0;
	
	public static Integer IS_SYSTEM_TRUE=1;
	public static Integer IS_SYSTEM_FALSE=0;
	
	
	private String id;
	private String subscriberId;
	private String rechargeCardId;
	private Integer isValid;
	private Double amount;
	private Double remainingAmount;
	
	private Integer isSystem;
	private String creatorId;
	private Date createTime;
	private Date ts;
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getRechargeCardId() {
		return rechargeCardId;
	}
	public void setRechargeCardId(String rechargeCardId) {
		this.rechargeCardId = rechargeCardId;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	
	
	
	
	

}
