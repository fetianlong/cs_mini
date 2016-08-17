/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file Account.java creation date:[2015-5-22 上午10:00:03] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author liusong
 * @Description 账户主表
 * @Version 1.0,2015-5-22
 *
 */
public class Account  implements Serializable{


	private static final long serialVersionUID = -7554019163271910543L;
	
	
	public static final Integer TYPE_RECHARGE=1;//充值
	public static final Integer TYPE_REFUND=2;//退款
	public static final Integer TYPE_CARE_BINDING=3;//绑定信用卡
	public static final Integer TYPE_CARD_UNBINDING=4;//绑定信用卡
	public static final Integer TYPE_ORDER=5;//订单消费
	public static final Integer TYPE_GIVE=6;//赠送充值卡
	public static final Integer TYPE_ORDER_REFUND=7;//订单退款
	public static final Integer TYPE_CUT_PAYMENT=8;//扣款
	
	
	
	public static final Integer PAY_TYPE_ACCOUNT=1;//账户支付
	public static final Integer PAY_TYPE_CREDIT=2; //信用卡支付
	public static final Integer PAY_TYPE_ALIPAY=3; //支付宝
	public static final Integer PAY_TYPE_UNIONPAY=4; //银联
	public static final Integer PAY_TYPE_WECHAT=5; //微信
	
	
	public static final Integer PAY_CHANNEL_PORTAL=1;//PC
	public static final Integer PAY_CHANNEL_WECHAT=2;//微信
	public static final Integer PAY_CHANNEL_CONSOLE=3;//后台
	public static final Integer PAY_CHANNEL_APP=4;//app 

	
	
	public static Integer IS_REFUND_FALSE=0;//无退款
	public static Integer IS_REFUND_TRUE=1;//退款中
	
	
	
	private String subscriberId;
	private Double amount;
	private Double frozenAmount;//冻结金额
	private Double usableAmount;//可用金额
		
	private Integer isRefund;//退款中
	
	private Date lastOperateTime;
	private Double lastOperateAmount;
	private Integer lastOperateType;
	
	private String operatorId;
	private Date ts;
	
	
	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public Date getLastOperateTime() {
		return lastOperateTime;
	}
	public void setLastOperateTime(Date lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}

	public Double getLastOperateAmount() {
		return lastOperateAmount;
	}
	public void setLastOperateAmount(Double lastOperateAmount) {
		this.lastOperateAmount = lastOperateAmount;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
	public static String getTradeType(Integer type){
		String typeStr="";
		if(TYPE_RECHARGE.equals(type)){
			typeStr="充值";
		}else if(TYPE_REFUND.equals(type)){
			typeStr= "退款";
		}else if(TYPE_ORDER.equals(type)){
			typeStr= "订单";
		}else if(TYPE_CARE_BINDING.equals(type)){
			typeStr= "信用卡绑定";
		}else if(TYPE_CARE_BINDING.equals(type)){
			typeStr= "信用卡解绑";
		}else if(TYPE_ORDER_REFUND.equals(type)){
			typeStr= "订单退款";
		}else if(TYPE_GIVE.equals(type)){
			typeStr= "赠送";
		}else if(TYPE_CUT_PAYMENT.equals(type)){
			typeStr="扣款";
		}
		return typeStr;
	}
	
	public static String getPayType(Integer payType){
		String payTypeStr="";
		if(Account.PAY_TYPE_ACCOUNT.equals(payType)){
			payTypeStr="账户";
		}else if(Account.PAY_TYPE_CREDIT.equals(payType)){
			payTypeStr="信用卡";
		}else if(Account.PAY_TYPE_ALIPAY.equals(payType)){
			payTypeStr="支付宝";
		}else if(Account.PAY_TYPE_UNIONPAY.equals(payType)){
			payTypeStr="银联";
		}else if(Account.PAY_TYPE_WECHAT.equals(payType)){
			payTypeStr="微信";
		}
		return payTypeStr;
	}
	
	public static String getPayChannel(Integer payChannel){
		String payTypeStr="";
		if(Account.PAY_CHANNEL_CONSOLE.equals(payChannel)){
			payTypeStr="后台";
		}else if(Account.PAY_CHANNEL_PORTAL.equals(payChannel)){
			payTypeStr="网站";
		}else if(Account.PAY_CHANNEL_WECHAT.equals(payChannel)){
			payTypeStr="微信";
		}else if(Account.PAY_CHANNEL_APP.equals(payChannel)){
			payTypeStr="APP";
		}
		return payTypeStr;
	}
	
	public Integer getLastOperateType() {
		return lastOperateType;
	}
	public void setLastOperateType(Integer lastOperateType) {
		this.lastOperateType = lastOperateType;
	}
	
	public Double getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(Double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Double getUsableAmount() {
		return usableAmount;
	}
	public void setUsableAmount(Double usableAmount) {
		this.usableAmount = usableAmount;
	}
	public Integer getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
	

}
