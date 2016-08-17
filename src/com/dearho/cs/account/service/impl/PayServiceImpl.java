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
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.PayService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.subscriber.dao.SubscriberDao;

public class PayServiceImpl extends PayServiceBase  implements PayService{

	private AccountDao accountDao;
	private AccountCardInstanceDao accountCardInstanceDao;//充值卡实例
	
	private AccountTradeRecordDao accountTradeRecordDao;      //交易记录
	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;//交易记录详情

	private RechargeCardService rechargeCardService;//充值卡
	
	private SubscriberDao subscriberDao;

	private  AccountPaymentAccountDao accountPaymentAccountDao;
	
	private OrdersDetailService ordersDetailService;
	
	
	

	/**
	 * 创建充值记录--走支付流程
	 * 自助
	 * @return 
	 * @throws Exception 
	 */
	public AccountTradeRecord createRechargeRecord(String subscriberId,String rechargeCardId, Double customAmount,Integer payChannel,Integer payType) throws Exception {
		
		//1.校验     
		//充值卡有效性校验
		RechargeCard rechargeCard=null;
		if(StringUtils.isEmpty(rechargeCardId)){
			rechargeCard=new RechargeCard();
			rechargeCard.setAmount(new Double(customAmount));
		}else{
			 rechargeCard=rechargeCardService.validateRechargeCard(rechargeCardId);
		}
		
		if(rechargeCard==null){
			throw new Exception("根据"+rechargeCardId+"未查询到可用充值卡");
		}
		
		//会员校验
		
		Account account=accountDao.getAccountById(subscriberId);
		
		if(account==null){
			throw new Exception("未查询到会员");
		}

		
		//2.交易记录主表，增加记录
		AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
		accountTradeRecord.setSubscriberId(subscriberId);
		accountTradeRecord.setType(Account.TYPE_RECHARGE);
		accountTradeRecord.setPayChannel(payChannel);
		accountTradeRecord.setPayType(payType);
		accountTradeRecord.setResult(AccountTradeRecord.RESULT_CREATE);
		accountTradeRecord.setBizId(rechargeCardId);
		accountTradeRecord.setSubOrderId(null);//子订单
		accountTradeRecord.setTradeOrderNo(getPayRechargeTradeNo());
		//充值卡是否为元数据
		if(StringUtils.isEmpty(rechargeCardId)){
			accountTradeRecord.setIsPresetCard(AccountTradeRecord.IS_PRESET_CARD_FALSE);
			accountTradeRecord.setDescription("自助充值自定义金额"+rechargeCard.getAmount()+"元");
		}else{
			accountTradeRecord.setIsPresetCard(AccountTradeRecord.IS_PRESET_CARD_TRUE);
			accountTradeRecord.setDescription("自助充值"+rechargeCard.getAmount()+"元");
		}
		
		accountTradeRecord.setTradeTime(new Date());
		accountTradeRecord.setTs(new Date());
		accountTradeRecord.setAmount(rechargeCard.getAmount());
		accountTradeRecord.setResult(AccountTradeRecord.RESULT_CREATE);
		accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
		
		
		//3.交易记录子表，添加记录
		AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
		accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
		accountTradeRecordDetail.setSubscriberId(subscriberId);
		accountTradeRecordDetail.setAmount(rechargeCard.getAmount());
		accountTradeRecordDetail.setDescription(null);
		accountTradeRecordDetail.setPaymentAccountId(null);
		accountTradeRecordDetail.setTs(new Date());
		accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
		
		return accountTradeRecord;
		
	}

	
	/**
	 * 充值付款回调--走支付流程
	 * @throws Exception 
	 */
	public void rechargePayCallBack(PayResponseMessage payResponseMessage) throws Exception {
		

		//1.充值记录校验  
		List<AccountTradeRecord> accountTradeRecordList=accountTradeRecordDao.queryTradeRecordByTradeOrderNo(payResponseMessage.getOrderId(),Account.TYPE_RECHARGE);
		if(accountTradeRecordList==null||accountTradeRecordList.size()==0){
			throw new Exception("充值订单不存在");
		}
		if(accountTradeRecordList.size()>1){
			throw new Exception("根据付款单号"+payResponseMessage.getOrderId()+"查到笔比充值记录");
		}
		AccountTradeRecord accountTradeRecord=accountTradeRecordList.get(0);
		if(!Account.TYPE_RECHARGE.equals(accountTradeRecord.getType())){
			throw new Exception("不为充值订单");
		}
		if(AccountTradeRecord.RESULT_SUCCESS.equals(accountTradeRecord.getResult())){
			return;
		}
		RechargeCard rechargeCard=null;
		AccountCardInstance instance =new AccountCardInstance();
		
		//2.获取充值卡  充值卡是否为预置卡类型  
		if(AccountTradeRecord.IS_PRESET_CARD_TRUE.equals(accountTradeRecord.getIsPresetCard())){
			//获取充值卡实例
			 rechargeCard=rechargeCardService.getRechargeCardById(accountTradeRecord.getBizId());
			 if(rechargeCard==null){
				 throw new Exception("充值卡不存在");
			 }
			 instance.setRechargeCardId(rechargeCard.getId());
		}else{
			 rechargeCard=new RechargeCard();
			 rechargeCard.setAmount(accountTradeRecord.getAmount());
		}
		
		
		//3.账户主表变更 
		Account account=accountDao.getAccountById(accountTradeRecord.getSubscriberId()); 
		
		BigDecimal cardAmount = new BigDecimal(rechargeCard.getAmount());  
		BigDecimal accountAmount = new BigDecimal(account.getAmount());
		account.setAmount( new Double(accountAmount.add(cardAmount).doubleValue()));
		
		
		
		//4.充值卡实例添加
		instance.setSubscriberId(accountTradeRecord.getSubscriberId());
		instance.setIsValid(AccountCardInstance.IS_VALID_TRUE);
		

		instance.setAmount(rechargeCard.getAmount());
		instance.setRemainingAmount(rechargeCard.getAmount());
		
		instance.setIsSystem(AccountCardInstance.IS_SYSTEM_FALSE);
	
		instance.setCreateTime(new Date());
		instance.setTs(new Date());
		accountCardInstanceDao.addAccountCardInstance(instance);
		
		
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
		
		
		//5.更新account 押金及可用余额
		computeFrozenAndUsableAmount(account);

		account.setLastOperateTime(new Date());
		account.setLastOperateAmount(rechargeCard.getAmount());
		account.setLastOperateType(Account.TYPE_RECHARGE);
		
		accountDao.updateAccount(account);
		
		//更新交易记录表
		accountTradeRecord.setRemainingAmount(account.getAmount());
		accountTradeRecord.setResult(AccountTradeRecord.RESULT_SUCCESS);
		accountTradeRecordDao.updateAccountTradeRecord(accountTradeRecord);
		
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


	
	
	
	
	
	//===========================get and set
	
	public AccountDao getAccountDao() {
		return accountDao;
	}


	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}


	


	public AccountCardInstanceDao getAccountCardInstanceDao() {
		return accountCardInstanceDao;
	}


	public void setAccountCardInstanceDao(AccountCardInstanceDao accountCardInstanceDao) {
		this.accountCardInstanceDao = accountCardInstanceDao;
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


	@Override
	public AccountCardInstanceDao getAccountCardInstanceDaoInstance() {
		
		return accountCardInstanceDao;
	}


	@Override
	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDaoInstance() {
		
		return accountTradeRecordDetailDao;
	}
	
	
	
	
	
	

}
