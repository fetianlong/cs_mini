package com.dearho.cs.account.service;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.PayResponseMessage;

public interface PayService {
	/**
	 * 创建充值记录
	 * @param subscriberId
	 * @param rechargeCardId
	 * @param payChannel
	 * @param payType
	 * @return 
	 * @throws Exception
	 */
	public AccountTradeRecord createRechargeRecord(String subscriberId,String rechargeCardId, Double customAmount,Integer payChannel,Integer payType) throws Exception ;
	

	/**
	 * 充值成功回调
	 * @param unionPayResponseMessage
	 * @throws Exception
	 */
	public void rechargePayCallBack(PayResponseMessage payResponseMessage) throws Exception;

}
