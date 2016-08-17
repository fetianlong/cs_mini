package com.dearho.cs.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.AccountCardInstanceDao;
import com.dearho.cs.account.dao.AccountDao;
import com.dearho.cs.account.dao.AccountPaymentAccountDao;
import com.dearho.cs.account.dao.AccountTradeRecordDao;
import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.account.pojo.PayResponseMessage;
import com.dearho.cs.account.pojo.PayServiceBase;
import com.dearho.cs.account.service.AccountCardInstanceService;
import com.dearho.cs.account.service.OrderPayService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.dao.SubscriberDao;

public class OrderPayServiceImpl extends PayServiceBase  implements OrderPayService{

	
	
	private AccountDao accountDao;
//	private AccountCardInstanceService accountCardInstanceService;//充值卡实例
	private AccountCardInstanceDao accountCardInstanceDao;
	
	private AccountTradeRecordDao accountTradeRecordDao;      //交易记录
	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;//交易记录详情

	private RechargeCardService rechargeCardService;//充值卡
	
	private SubscriberDao subscriberDao;

	private  AccountPaymentAccountDao accountPaymentAccountDao;
	
	private OrdersDetailService ordersDetailService;
	private OrdersService ordersService;
	
	
	/**
	 * 订单支付
	 * @return 
	 * @throws Exception 
	 */
	public AccountTradeRecord orderPayForOnline(List<OrdersDetail> orderList,String subscriberId,Integer payChannel,Integer payType ) throws Exception {
		
		//1.校验     
		
		if(orderList==null){
			throw new Exception("支付订单为空!");
		}
		
		
		AccountTradeRecord  mainRecord=new AccountTradeRecord();
		mainRecord.setTradeOrderNo(getPayOrderTradeNo());
		mainRecord.setAmount(0d);
		mainRecord.setTradeTime(new Date());
		Integer index=1;
		for(OrdersDetail orderDetail:orderList){
			if(orderDetail.getTotalFee()==null){
				throw new Exception("订单金额为空");
			}
			
			Account account=accountDao.getAccountById(subscriberId);
			
			if(account==null){
				throw new Exception("未查询到会员");
			}

			//支付金额相加
			
			BigDecimal mainAmount = new BigDecimal(mainRecord.getAmount());  
			BigDecimal orderAmount = orderDetail.getTotalFee();
			if(orderDetail.getTicketsFee()!=null){
				orderAmount=orderAmount.add(orderDetail.getTicketsFee());
			}
			
			mainRecord.setAmount( new Double(mainAmount.add(orderAmount).doubleValue()));
			
			//2.交易记录主表，增加记录
			AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
			accountTradeRecord.setSubscriberId(subscriberId);
			accountTradeRecord.setType(Account.TYPE_ORDER);
			accountTradeRecord.setPayChannel(payChannel);
			accountTradeRecord.setPayType(payType);
			accountTradeRecord.setResult(AccountTradeRecord.RESULT_CREATE);
			accountTradeRecord.setBizId(orderDetail.getOrdersNo());
			accountTradeRecord.setSubOrderId(orderDetail.getOrdersDetailNo());//子订单
			accountTradeRecord.setTradeOrderNo(mainRecord.getTradeOrderNo());
			//充值卡是否为元数据
			
			accountTradeRecord.setIsPresetCard(AccountTradeRecord.IS_PRESET_CARD_FALSE);
			accountTradeRecord.setDescription("订单支付"+orderDetail.getTotalFee().doubleValue()+"元");
			
			
			accountTradeRecord.setTradeTime(mainRecord.getTradeTime());
			accountTradeRecord.setTs(new Date());
			accountTradeRecord.setAmount(orderDetail.getTotalFee().doubleValue());
		
			
		
			accountTradeRecord.setRemainingAmount(account.getAmount());
		
			
			accountTradeRecord.setResult(AccountTradeRecord.RESULT_CREATE);
			accountTradeRecord.setOrderIndex(index++);
			accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
			
			
			//3.交易记录子表，添加记录
			AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
			accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
			accountTradeRecordDetail.setSubscriberId(subscriberId);
			accountTradeRecordDetail.setAmount(orderDetail.getTotalFee().doubleValue());
		
			accountTradeRecordDetail.setDescription(null);
			accountTradeRecordDetail.setPaymentAccountId(null);
			accountTradeRecordDetail.setTs(new Date());
			accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
			
		}
		mainRecord.setDescription("订单支付"+mainRecord.getAmount());
		
		return mainRecord;
		
	}
	
	
	/**
	 * 付款回调--走支付流程
	 * @throws Exception 
	 */
	public void orderPayOnlineCallBack(PayResponseMessage payResponseMessage,String step) throws Exception{
		//1.充值记录校验  
		List<AccountTradeRecord> accountTradeRecordList=accountTradeRecordDao.queryTradeRecordByTradeOrderNo(payResponseMessage.getOrderId(),Account.TYPE_ORDER);
		if(accountTradeRecordList==null||accountTradeRecordList.size()==0){
			throw new Exception("充值订单不存在");
		}
		AccountTradeRecord accountTradeRecord=accountTradeRecordList.get(0);
		if(!Account.TYPE_ORDER.equals(accountTradeRecord.getType())){
			throw new Exception("不为租车订单");
		}
		if(AccountTradeRecord.RESULT_SUCCESS.equals(accountTradeRecord.getResult())){
			return;
		}
		
		AccountCardInstance instance =new AccountCardInstance();
		
		
		//付款账号
		AccountPaymentAccount paymentAccount=null;
		if(!StringUtils.isEmpty(payResponseMessage.getAccNo())){
			paymentAccount=accountPaymentAccountDao.queryPaymentAccountByAccountNoAndSubscriberId(payResponseMessage.getAccNo(), accountTradeRecord.getSubscriberId());
			if(paymentAccount==null){
				paymentAccount=new AccountPaymentAccount();
				paymentAccount.setType(payResponseMessage.getPayType());
				paymentAccount.setAccountNo(payResponseMessage.getAccNo());
				paymentAccount.setSubscriberId(accountTradeRecord.getSubscriberId());
				paymentAccount.setTs(new Date());
				accountPaymentAccountDao.addAccountPaymentAccount(paymentAccount);
			}
		}
		
		//是找主副订单还是 依次遍历
		String ordersDetailNo=null;
		Integer payType=null;
		for(AccountTradeRecord record:accountTradeRecordList){
			if(record.getOrderIndex()==1){
				ordersDetailNo=record.getSubOrderId();
			}
			payType=record.getPayType();
			record.setResult(AccountTradeRecord.RESULT_SUCCESS);
			accountTradeRecordDao.updateAccountTradeRecord(record);
			//6.更新交易记录详情表
			List<AccountTradeRecordDetail> list=accountTradeRecordDetailDao.getAccountTradeRecordDetailByBizId(accountTradeRecord.getId());
			if(list==null || list.size()==0){
				throw new Exception("未查询到交易记录详情");
			}
			AccountTradeRecordDetail accountTradeRecordDetail=list.get(0);
			accountTradeRecordDetail.setRechargeCardInstanceId(instance.getId());
			
			accountTradeRecordDetail.setTradeNo(payResponseMessage.getQueryId());
			if(paymentAccount!=null){
				accountTradeRecordDetail.setPaymentAccountId(paymentAccount.getId());
			}
			accountTradeRecordDetailDao.updateAccountTradeRecordDetail(accountTradeRecordDetail);
		}
		
		
		if(STEP_CRATE.equals(step)){
			ordersDetailService.ordersPaySuccess(ordersDetailNo,payType);
			System.out.println("创建订单=========================================");
		}else if(STEP_FINISH.equals(step)){
			ordersService.completeOrder(null,ordersDetailNo,payType,null,null);
			System.out.println("结算订单=========================================");
		}else{
			throw new Exception("未知的支付环节");
		}
		
	}


	
	
	
	
	/**
	 * 订单消费扣款
	 * 根据充值卡的优先级进行扣费
	 * @param orderList
	 * @param subscriberId
	 * @param payChannel
	 * @throws Exception
	 */
	public synchronized void orderPayForAccount(List<OrdersDetail> orderList,String subscriberId,Integer payChannel,String step) throws Exception{
			
			if(StringUtils.isEmpty(subscriberId)){
				throw new Exception("会员id为空");
			}
			if(orderList==null||orderList.size()==0){
				throw new Exception("订单信息为空");
			}
			
			//账户金额校验
			BigDecimal feeAmount=new BigDecimal(0);
			for(OrdersDetail orderDetail:orderList){
				if(orderDetail.getTotalFee()==null){
					throw new Exception("订单金额为空");
				}
				if(orderDetail.getTotalFee() != null)
				feeAmount=feeAmount.add(orderDetail.getTotalFee());
				if(orderDetail.getTicketsFee() != null)
				feeAmount=feeAmount.add(orderDetail.getTicketsFee());
			}
		
			Account account=accountDao.getAccountById(subscriberId);
			
			if(feeAmount.compareTo(new BigDecimal(account.getUsableAmount()))>0){
				throw new Exception("账户余额不足");
			}
			
			Integer orderIndex=1;
			
			//依次循环 每笔订单消费
			for(OrdersDetail orderDetail:orderList){
				
				
				BigDecimal orderFee=orderDetail.getTotalFee();
				if(orderDetail.getTicketsFee()!=null)
				orderFee=orderFee.add(orderDetail.getTicketsFee());
				
				//子订单主消费记录
				AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
				accountTradeRecord.setSubscriberId(subscriberId);
				accountTradeRecord.setType(Account.TYPE_ORDER);
				accountTradeRecord.setPayChannel(payChannel);
				accountTradeRecord.setPayType(Account.PAY_TYPE_ACCOUNT);
				accountTradeRecord.setResult(AccountTradeRecord.RESULT_SUCCESS);
				accountTradeRecord.setBizId(orderDetail.getOrdersNo());
				accountTradeRecord.setSubOrderId(orderDetail.getOrdersDetailNo());//子订单
				accountTradeRecord.setTradeOrderNo(getPayOrderTradeNo());
			
				accountTradeRecord.setIsPresetCard(null);
				accountTradeRecord.setDescription("账户订单付款"+orderFee.doubleValue()+"元");
				
				accountTradeRecord.setTradeTime(new Date());
				accountTradeRecord.setTs(new Date());
				accountTradeRecord.setAmount(orderFee.doubleValue());
				accountTradeRecord.setOrderIndex(orderIndex++);
				
				accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
				
				
				//为子订单扣款
				//取卡顺序一次次取卡消费
				BigDecimal subOrderAmount=new BigDecimal(0);
				BigDecimal subOrderActualAmount=new BigDecimal(0);
				
			
				int index=1;//扣款顺序
				Boolean isEnoughMoney=false;
				List<AccountCardInstance> cardInstanceList=accountCardInstanceDao.queryEnabledCardInstanceByAccountId(account.getSubscriberId());
				
				//倒叙取实例
				if(cardInstanceList!=null && cardInstanceList.size()>0){
					for(int i=0;i<cardInstanceList.size();i++){
						    AccountCardInstance cardInstance= cardInstanceList.get(i);
						    BigDecimal instanceAmount = new BigDecimal(cardInstance.getRemainingAmount());  
						
						
							//是否有足够押金、足够的话计算可用余额
							
							//如果还没划够 -划 else 退出
						    if(!isEnoughMoney){
								//充值卡足够扣款
								if(subOrderAmount.add(instanceAmount).compareTo(orderFee)>0){
									  
									   //还差多少元
									   BigDecimal difference=orderFee.subtract(subOrderAmount);
									   
									   subOrderAmount=subOrderAmount.add(difference);
									   
									   
									  
										cardInstance.setRemainingAmount(instanceAmount.subtract(difference).doubleValue());
										cardInstance.setIsValid(AccountCardInstance.IS_SYSTEM_TRUE);
										accountCardInstanceDao.updateAccountCardInstance(cardInstance);
										
										isEnoughMoney=true;
									   
									   
									   	AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
										accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
										accountTradeRecordDetail.setSubscriberId(subscriberId);
										accountTradeRecordDetail.setAmount(difference.doubleValue());
										accountTradeRecordDetail.setRechargeCardInstanceId(cardInstance.getId());
										accountTradeRecordDetail.setOrderIndex(index++);
										accountTradeRecordDetail.setDescription(null);
										accountTradeRecordDetail.setPaymentAccountId(null);
										accountTradeRecordDetail.setTs(new Date());
										accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
									   
									   
									   
								}else if(subOrderAmount.add(instanceAmount).compareTo(orderFee)==0){
										//充值卡金额刚够-- 扣款 卡失效
										subOrderAmount=subOrderAmount.add(instanceAmount);
									  
										cardInstance.setRemainingAmount(0d);
									
										cardInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
										accountCardInstanceDao.updateAccountCardInstance(cardInstance);
										
										isEnoughMoney=true;
										
										
										AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
										accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
										accountTradeRecordDetail.setSubscriberId(subscriberId);
										accountTradeRecordDetail.setAmount(instanceAmount.doubleValue());

										accountTradeRecordDetail.setRechargeCardInstanceId(cardInstance.getId());
										accountTradeRecordDetail.setOrderIndex(index++);
										accountTradeRecordDetail.setDescription(null);
										accountTradeRecordDetail.setPaymentAccountId(null);
										accountTradeRecordDetail.setTs(new Date());
										accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
									   
								}else{
									//充值卡金额不够
									
									subOrderAmount=subOrderAmount.add(instanceAmount);
									
									cardInstance.setRemainingAmount(0d);
									cardInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
									accountCardInstanceDao.updateAccountCardInstance(cardInstance);
									
									AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
									accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
									accountTradeRecordDetail.setSubscriberId(subscriberId);
									accountTradeRecordDetail.setAmount(instanceAmount.doubleValue());
									accountTradeRecordDetail.setRechargeCardInstanceId(cardInstance.getId());
									accountTradeRecordDetail.setOrderIndex(index++);
									accountTradeRecordDetail.setDescription(null);
									accountTradeRecordDetail.setPaymentAccountId(null);
									accountTradeRecordDetail.setTs(new Date());
									accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
									
								}
							}else{
								break;
							}
					}
				}
				
				//订单消费记录字表
				computeFrozenAndUsableAmount(account);
				accountDao.updateAccount(account);
				
	
				accountTradeRecord.setRemainingAmount(account.getAmount());
			
				
				accountTradeRecordDao.updateAccountTradeRecord(accountTradeRecord);
				
				
			}
			
			
			
			if(STEP_CRATE.equals(step)){
				System.out.println("创建订单=========================================");
				ordersDetailService.ordersPaySuccess(orderList.get(0).getOrdersDetailNo(),Account.PAY_TYPE_ACCOUNT);
			}else if(STEP_FINISH.equals(step)){
				System.out.println("结算订单=========================================");
				ordersService.completeOrder(null,orderList.get(0).getOrdersDetailNo(),Account.PAY_TYPE_ACCOUNT,null,null);
			}else{
				throw new Exception("未知的支付环节");
			}
			

	}

	//===========================get and set
	
	public AccountDao getAccountDao() {
		return accountDao;
	}


	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}


	

	public AccountTradeRecordDao getAccountTradeRecordDao() {
		return accountTradeRecordDao;
	}


	public void setAccountTradeRecordDao(AccountTradeRecordDao accountTradeRecordDao) {
		this.accountTradeRecordDao = accountTradeRecordDao;
	}


	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDao() {
		return accountTradeRecordDetailDao;
	}


	public void setAccountTradeRecordDetailDao(AccountTradeRecordDetailDao accountTradeRecordDetailDao) {
		this.accountTradeRecordDetailDao = accountTradeRecordDetailDao;
	}


	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}


	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}


	public SubscriberDao getSubscriberDao() {
		return subscriberDao;
	}


	public void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}


	public AccountPaymentAccountDao getAccountPaymentAccountDao() {
		return accountPaymentAccountDao;
	}


	public void setAccountPaymentAccountDao(AccountPaymentAccountDao accountPaymentAccountDao) {
		this.accountPaymentAccountDao = accountPaymentAccountDao;
	}




	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}


	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}


	public OrdersService getOrdersService() {
		return ordersService;
	}


	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}


	public AccountCardInstanceDao getAccountCardInstanceDao() {
		return accountCardInstanceDao;
	}


	public void setAccountCardInstanceDao(AccountCardInstanceDao accountCardInstanceDao) {
		this.accountCardInstanceDao = accountCardInstanceDao;
	}


	@Override
	public AccountCardInstanceDao getAccountCardInstanceDaoInstance() {
		return accountCardInstanceDao;
	}


	@Override
	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDaoInstance() {
		return accountTradeRecordDetailDao;
	}
	
	
	
	
	
	

}
