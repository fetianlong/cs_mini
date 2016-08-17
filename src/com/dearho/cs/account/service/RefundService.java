package com.dearho.cs.account.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.PayResponseMessage;
import com.dearho.cs.subscriber.pojo.OrderMessage;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.sys.pojo.User;

public interface RefundService {

	
	public void applyRefund(String subscriberId,String paymentId);

	public void refundCallBack(PayResponseMessage payResponseMessage) throws Exception;
	
	public AccountTradeRecord applyRefundSuccess(SubscriberConfirm confirm,String operatorId) throws  Exception;
	
	public   void applyRefundFail(SubscriberConfirm subscriberConfirm,User operator) throws Exception;
	
	public void handleRefundRecord(String tradeOrderNo,HttpServletRequest request,HttpServletResponse response)throws Exception;
	
	public boolean checkIsNeedRefundAccount(String subscriberId);
	
	public void checkApplyRefund(String subscriberId) throws Exception;

	public String generateRefundDescription(List<AccountCardInstance> instanceList);
}
