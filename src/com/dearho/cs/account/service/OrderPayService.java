package com.dearho.cs.account.service;

import java.util.List;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.PayResponseMessage;
import com.dearho.cs.orders.pojo.OrdersDetail;

public interface OrderPayService {
	public static final String STEP_CRATE="create";
	public static final String STEP_PORTAL_CRATE="portal_create";
	public static final String STEP_FINISH="finish";
	
	public AccountTradeRecord orderPayForOnline(List<OrdersDetail> orderList,String subscriberId,Integer payChannel,Integer payType ) throws Exception ;
			
	public void orderPayOnlineCallBack(PayResponseMessage payResponseMessage,String step) throws Exception;
	
	public void orderPayForAccount(List<OrdersDetail> orderList,String subscriberId,Integer payChannel,String step) throws Exception;

}
