package com.dearho.cs.account.vo;

import java.util.Date;

import com.dearho.cs.sys.pojo.FieldComment;

public class AccountTradeRecordVO  {
	
	
	/**手机号*/
	@FieldComment("手机号")
	private String subscriberPhoneNo;
	
	/**姓名 */
	@FieldComment("姓名")
	private String subscriberName;
	
	/**交易类型 */
	@FieldComment("交易类型")
	private String type;
	
	/**交易金额 */
	@FieldComment("交易金额")
	private Double amount;
	
	/**交易时间 */
	@FieldComment("交易时间")
	private Date tradeTime;
	
	/**支付渠道 */
	@FieldComment("支付渠道")
	private String payChannel;
	
	/**支付方式 */
	@FieldComment("支付方式")
	private String payType;
	
	/**订单ID*/
	@FieldComment("订单ID")
	private String bizId;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	

	
	
	
	
	
}
