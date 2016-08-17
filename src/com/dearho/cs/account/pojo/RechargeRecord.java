package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.sys.pojo.FieldComment;


/**
 * 充值记录
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 */
public class RechargeRecord  implements Serializable{
	
	public static final Integer TYPE_RECHARGE = 1;
	public static final Integer TYPE_CUT_PAYMENT = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7082067083826332243L;
	private String id;
	
	@FieldComment("会员名称")
	private String subscriberName;
	
	@FieldComment("手机号")
	private String subscriberPhoneNo;
	
	@FieldComment("金额")
	private Double rechargeAmount;
	
	@FieldComment("操作时间")
	private Date createTime;
	
	@FieldComment("操作人")
	private String operatorName;
	
	@FieldComment("备注")
	private String remark;
	
	@FieldComment("支付方式")
	private String payType;
	
	private String rechargeCardId;
	
	private String subscriberId;
	
	
	private String operatorId;
	
	
	private Integer type;
	

	private Date ts;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getRechargeCardId() {
		return rechargeCardId;
	}
	public void setRechargeCardId(String rechargeCardId) {
		this.rechargeCardId = rechargeCardId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getSubscriberPhoneNo() {
		return subscriberPhoneNo;
	}
	public void setSubscriberPhoneNo(String subscriberPhoneNo) {
		this.subscriberPhoneNo = subscriberPhoneNo;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
