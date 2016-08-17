package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dearho.cs.subscriber.pojo.Subscriber;

public class AccountTradeRecord implements Serializable {
	
	public static final Integer IS_AUTO_CLEAR_TRUE=1;
	public static final Integer IS_AUTO_CLEAR_FALSE=0;
	
	
	public static final Integer RESULT_CREATE=0;
	public static final Integer RESULT_SUCCESS=1;
	public static final Integer RESULT_FAIL=2;
	public static final Integer RESULT_TIMEOUT=3;
	
	
	public static final Integer IS_PRESET_CARD_TRUE=1;
	public static final Integer IS_PRESET_CARD_FALSE=0;
	
	
	private String id;
	private String subscriberId;
	private Integer type;//交易类型
	private Integer payType;//支付类型
	private Integer payChannel;//支付渠道
	private Double amount;
	private Double remainingAmount;
	private String bizId;//
	
	private String tradeOrderNo;//交易订单 
	
	private String subOrderId;//子订单id
	private Integer isAutoClear;//是否自动结算订单（未使用车）
	
	private Integer isPresetCard;//是否预置
	
	private Date tradeTime;
	private String description;
	private Integer result;
	private Date ts;
	private Integer orderIndex;
	
	
	
	private Subscriber subscriber;
	private AccountTradeRecordDetail accountTradeRecordDetail;
	

	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public Integer getIsAutoClear() {
		return isAutoClear;
	}
	public void setIsAutoClear(Integer isAutoClear) {
		this.isAutoClear = isAutoClear;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public Integer getIsPresetCard() {
		return isPresetCard;
	}
	public void setIsPresetCard(Integer isPresetCard) {
		this.isPresetCard = isPresetCard;
	}
	public String getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public AccountTradeRecordDetail getAccountTradeRecordDetail() {
		return accountTradeRecordDetail;
	}
	public void setAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail) {
		this.accountTradeRecordDetail = accountTradeRecordDetail;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
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
