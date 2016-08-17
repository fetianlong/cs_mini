package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;

public class AccountTradeRecordDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1842149527921701320L;
	
	private String id;
	private String subscriberId;
	private String tradeRecordId;
	private String paymentAccountId;
	private Double amount;
	private String tradeNo;
	private String rechargeCardInstanceId;
	
	private String description;
	private String refundTradeNo;//退款交易流水
	private Integer orderIndex;//多笔扣款充值卡时，扣款顺序
	private Date ts;
	
	private AccountPaymentAccount accountPaymentAccount;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeRecordId() {
		return tradeRecordId;
	}
	public void setTradeRecordId(String tradeRecordId) {
		this.tradeRecordId = tradeRecordId;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getPaymentAccountId() {
		return paymentAccountId;
	}
	public void setPaymentAccountId(String paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRechargeCardInstanceId() {
		return rechargeCardInstanceId;
	}
	public void setRechargeCardInstanceId(String rechargeCardInstanceId) {
		this.rechargeCardInstanceId = rechargeCardInstanceId;
	}
	
	public String getRefundTradeNo() {
		return refundTradeNo;
	}
	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}
	public AccountPaymentAccount getAccountPaymentAccount() {
		return accountPaymentAccount;
	}
	public void setAccountPaymentAccount(AccountPaymentAccount accountPaymentAccount) {
		this.accountPaymentAccount = accountPaymentAccount;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	
	
	
	
	
}
