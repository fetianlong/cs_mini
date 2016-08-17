/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountServiceImpl.java creation date:[2015-6-1 下午02:48:52] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.AccountCardInstanceDao;
import com.dearho.cs.account.dao.AccountDao;
import com.dearho.cs.account.dao.AccountPaymentAccountDao;
import com.dearho.cs.account.dao.AccountTradeRecordDao;
import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.account.dao.RechargeRecordDao;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.account.pojo.PayServiceBase;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.dao.SubscriberConfirmDao;
import com.dearho.cs.subscriber.dao.SubscriberDao;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.dao.BusinessFlowDAO;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public  class AccountServiceImpl extends PayServiceBase implements AccountService {
	
	private AccountDao accountDao;
	private AccountCardInstanceDao accountCardInstanceDao;//充值卡实例
	
	private AccountPaymentAccountDao accountPaymentAccountDao;//付款账号
	
	private AccountTradeRecordDao accountTradeRecordDao;      //交易记录
	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;//交易记录详情
	
	
	
	private RechargeCardService rechargeCardService;//充值卡
	
	private SubscriberService subscriberService;
	
	private BusinessFlowDAO businessFlowDAO;//工作流
	private SubscriberDao subscriberDao;
	private SubscriberConfirmDao subscriberConfirmDao;
	
	private OrdersService ordersService;
	private RechargeRecordDao rechargeRecordDao;
	


	
	
	
	/**
	 * 查询账号详情
	 * @Title: queryAccoutDetail
	 * @Description:
	 * @param id
	 * @throws
	 */
	public Account getAccoutDetail(String id){
		return accountDao.getAccountById(id);
	}
	
	
	
	
	
	
	public Page<Subscriber> queryAccountByPage(Page<Subscriber> page, Subscriber subscriber) {
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from Account a , Subscriber s where a.id=s.id ");
	

		if(subscriber!=null){
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phoneNo like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and s.name='"+subscriber.getName()+"' ");
			}
		}
		
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.lastOperateTime desc " : page.getOrderByString());
		return accountDao.queryAccountByPage(page, hql.toString());
	
	}

	
	

	

@Override
public Page<Subscriber> queryLastOperate(Page<Subscriber> page, int days, Integer typeRefund) {
	String hql = "select a.id from Account a, BusinessFlow b where a.id=b.applicantId ";
	if(typeRefund != null){
		hql += " and b.businessType="+typeRefund;
	}
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.DAY_OF_YEAR, 0-days);
		hql += " and b.result="+1;
		hql += " and b.reviewTime > '"+ DateUtil.getChar19DateString(cal.getTime()) + "'";
		hql += " and b.reviewTime < '"+ DateUtil.getChar19DateString(new Date()) + "'";
	
	hql += StringHelper.isEmpty(page.getOrderByString()) ? " order by a.lastOperateTime desc " : page.getOrderByString();
	return accountDao.queryAccountByPage(page, hql.toString());

}

	


	


/**
 * 代办 购买充值卡--不走支付流程（相当于已经从其他渠道付款）
 * @throws Exception 
 */
	public void commissionRechargeCard(String rechargeCardId,Double amount, String subscriberId, User operator,String payType,String remark) throws Exception {
		
		//1校验     
		//1.1充值卡有效性校验
		RechargeCard rechargeCard=null;
		if(!StringUtils.isEmpty(rechargeCardId)){
			rechargeCard=rechargeCardService.validateRechargeCard(rechargeCardId);
		}else{
			rechargeCard=new RechargeCard();
			rechargeCard.setAmount(amount);
		}
		
		//1.2会员校验
		Subscriber subscriber=subscriberDao.querySubscriberById(subscriberId);
		Account account=accountDao.getAccountById(subscriberId);
		
		if(subscriber==null ||account==null){
			throw new Exception("未查询到会员");
		}
		
		
		//3.用户实例表添加记录
		AccountCardInstance instance =new AccountCardInstance();
		instance.setSubscriberId(subscriberId);
		instance.setIsValid(AccountCardInstance.IS_VALID_TRUE);
		
		instance.setRechargeCardId(rechargeCardId);
		
		instance.setAmount(rechargeCard.getAmount());
		instance.setRemainingAmount(rechargeCard.getAmount());
	
		
		//代办人信息
		instance.setIsSystem(AccountCardInstance.IS_SYSTEM_TRUE);
		instance.setCreatorId(operator.getId());
		instance.setCreateTime(new Date());
		instance.setTs(new Date());
		accountCardInstanceDao.addAccountCardInstance(instance);
		
		
		//4.交易记录主表，增加记录
		AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
		accountTradeRecord.setSubscriberId(subscriberId);
		accountTradeRecord.setType(Account.TYPE_RECHARGE);
		accountTradeRecord.setPayChannel(Account.PAY_CHANNEL_CONSOLE);
		accountTradeRecord.setPayType(null);
		accountTradeRecord.setResult(AccountTradeRecord.RESULT_SUCCESS);
		
		accountTradeRecord.setBizId(rechargeCardId);
		accountTradeRecord.setTradeTime(new Date());
		accountTradeRecord.setTs(new Date());
		accountTradeRecord.setAmount(rechargeCard.getAmount());

		accountTradeRecord.setRemainingAmount(account.getAmount());
		accountTradeRecord.setDescription("代充"+rechargeCard.getAmount());
		accountTradeRecord.setTradeOrderNo(getPayRechargeTradeNo());
		accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
		
		//5.交易记录子表，添加记录
		AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
		accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
		accountTradeRecordDetail.setSubscriberId(subscriberId);
		accountTradeRecordDetail.setRechargeCardInstanceId(instance.getId());
		accountTradeRecordDetail.setAmount(rechargeCard.getAmount());
		accountTradeRecordDetail.setDescription("代充");
		accountTradeRecordDetail.setPaymentAccountId(null);
		accountTradeRecordDetail.setTradeNo(null);
		accountTradeRecordDetail.setTs(new Date());
		accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
		
		
		//整理扣款欠的钱
		clearUpPaymentCutAmount(account);
		
		//6.更新account 押金及可用余额
		computeFrozenAndUsableAmount(account);

	
		//账户上次操作类型
		account.setLastOperateTime(new Date());
		account.setLastOperateAmount(rechargeCard.getAmount());
		account.setLastOperateType(Account.TYPE_RECHARGE);
		
		accountDao.updateAccount(account);
		
		RechargeRecord record = new RechargeRecord();
		record.setCreateTime(new Date());
		record.setOperatorId(operator.getId());
		record.setOperatorName(operator.getName());
		record.setRechargeAmount(rechargeCard.getAmount());
		record.setRechargeCardId(rechargeCardId);
		record.setType(RechargeRecord.TYPE_RECHARGE);
		record.setPayType(payType);
		record.setRemark(remark);
		record.setSubscriberId(subscriber.getId());
		record.setSubscriberName(subscriber.getName());
		record.setSubscriberPhoneNo(subscriber.getPhoneNo());
		record.setTs(new Date());
		rechargeRecordDao.addRechargeRecord(record);
	}


	@Override
	public void commissionCutPayment(Integer customAmount, String subscriberId, User operator, String remark) throws Exception{
		
		/*//1校验     
		//1.1充值卡有效性校验
		
		
		
		RechargeCard rechargeCard=new RechargeCard();
		rechargeCard.setAmount(Double.parseDouble(-customAmount+""));
		
		
		//1.2会员校验
		Subscriber subscriber=subscriberDao.querySubscriberById(subscriberId);
		Account account=accountDao.getAccountById(subscriberId);
		
		if(subscriber==null ||account==null){
			throw new Exception("未查询到会员");
		}
		
		
		//3.用户实例表添加记录
		AccountCardInstance instance =new AccountCardInstance();
		instance.setSubscriberId(subscriberId);
		instance.setIsValid(AccountCardInstance.IS_VALID_TRUE);
		instance.setRechargeCardId(null);
		instance.setAmount(rechargeCard.getAmount());
		instance.setRemainingAmount(rechargeCard.getAmount());
	
		
		//代办人信息
		instance.setIsSystem(AccountCardInstance.IS_SYSTEM_TRUE);
		instance.setCreatorId(operator.getId());
		instance.setCreateTime(new Date());
		instance.setTs(new Date());
		accountCardInstanceDao.addAccountCardInstance(instance);
		
		
		//4.交易记录主表，增加记录
		AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
		accountTradeRecord.setSubscriberId(subscriberId);
		accountTradeRecord.setType(Account.TYPE_CUT_PAYMENT);
		accountTradeRecord.setPayChannel(Account.PAY_CHANNEL_CONSOLE);
		accountTradeRecord.setPayType(null);
		accountTradeRecord.setResult(AccountTradeRecord.RESULT_SUCCESS);
		
		accountTradeRecord.setBizId(null);
		accountTradeRecord.setTradeTime(new Date());
		accountTradeRecord.setTs(new Date());
		accountTradeRecord.setAmount(rechargeCard.getAmount());

		accountTradeRecord.setRemainingAmount(account.getAmount());
		accountTradeRecord.setDescription("扣款"+rechargeCard.getAmount());
		accountTradeRecord.setTradeOrderNo(getCutPaymentNo());
		accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
		
		//5.交易记录子表，添加记录
		AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
		accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
		accountTradeRecordDetail.setSubscriberId(subscriberId);
		accountTradeRecordDetail.setRechargeCardInstanceId(instance.getId());
		accountTradeRecordDetail.setAmount(rechargeCard.getAmount());
		accountTradeRecordDetail.setDescription("代充");
		accountTradeRecordDetail.setPaymentAccountId(null);
		accountTradeRecordDetail.setTradeNo(null);
		accountTradeRecordDetail.setTs(new Date());
		accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
		
		
		
		//6.更新account 押金及可用余额
		computeFrozenAndUsableAmount(account);

	
		//账户上次操作类型
		account.setLastOperateTime(new Date());
		account.setLastOperateAmount(rechargeCard.getAmount());
		account.setLastOperateType(Account.TYPE_RECHARGE);
		
		accountDao.updateAccount(account);
		
		RechargeRecord record = new RechargeRecord();
		record.setCreateTime(new Date());
		record.setOperatorId(operator.getId());
		record.setOperatorName(operator.getName());
		record.setRechargeAmount(rechargeCard.getAmount());
		record.setType(RechargeRecord.TYPE_CUT_PAYMENT);
		record.setRechargeCardId(null);
		record.setRemark(remark);
		record.setSubscriberId(subscriber.getId());
		record.setSubscriberName(subscriber.getName());
		record.setSubscriberPhoneNo(subscriber.getPhoneNo());
		record.setTs(new Date());
		rechargeRecordDao.addRechargeRecord(record);*/
		
		
		Subscriber subscriber=subscriberDao.querySubscriberById(subscriberId);
		RechargeRecord record = new RechargeRecord();
		record.setCreateTime(new Date());
		record.setOperatorId(operator.getId());
		record.setOperatorName(operator.getName());
		record.setRechargeAmount(-customAmount.doubleValue());
		record.setType(RechargeRecord.TYPE_CUT_PAYMENT);
		record.setRechargeCardId(null);
		record.setRemark(remark);
		record.setSubscriberId(subscriber.getId());
		record.setSubscriberName(subscriber.getName());
		record.setSubscriberPhoneNo(subscriber.getPhoneNo());
		record.setTs(new Date());
		rechargeRecordDao.addRechargeRecord(record);
		

		
		
		
		//账户金额校验
		Account account=accountDao.getAccountById(subscriberId);
		
			
			//子订单主消费记录
			AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
			accountTradeRecord.setSubscriberId(subscriberId);
			accountTradeRecord.setType(Account.TYPE_CUT_PAYMENT);
			accountTradeRecord.setPayChannel(Account.PAY_CHANNEL_CONSOLE);
			accountTradeRecord.setPayType(null);
			accountTradeRecord.setResult(AccountTradeRecord.RESULT_SUCCESS);
			
			accountTradeRecord.setSubOrderId(null);//子订单
			accountTradeRecord.setTradeOrderNo(getCutPaymentNo());
			accountTradeRecord.setIsPresetCard(null);
			accountTradeRecord.setDescription("后台扣费："+remark);
			
			accountTradeRecord.setTradeTime(new Date());
			accountTradeRecord.setTs(new Date());
			accountTradeRecord.setAmount(Double.parseDouble(-customAmount+""));
			
			
			accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
			
			
			//为子订单扣款
			//取卡顺序一次次取卡消费
			BigDecimal cutAmount=new BigDecimal(0);
			BigDecimal cutMaxAmount=new BigDecimal(customAmount);
			
			
		
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
							if(cutAmount.add(instanceAmount).compareTo(cutMaxAmount)>0){
								  
								   //还差多少元
								   BigDecimal difference=cutMaxAmount.subtract(cutAmount);
								   
								   cutAmount=cutAmount.add(difference);
								   
								  
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
								   
								   
							}else if(cutAmount.add(instanceAmount).compareTo(cutMaxAmount)==0){
									//充值卡金额刚够-- 扣款 卡失效
									cutAmount=cutAmount.add(instanceAmount);
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
								cutAmount=cutAmount.add(instanceAmount);
								
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
			
			//不够减的，增加一张充值卡  测试，负钱充值后归0
			if(cutAmount.compareTo(cutMaxAmount)<0){
				
				BigDecimal difference=cutMaxAmount.subtract(cutAmount);
				 
				//3.用户实例表添加记录
				AccountCardInstance instance =new AccountCardInstance();
				instance.setSubscriberId(subscriberId);
				instance.setIsValid(AccountCardInstance.IS_VALID_TRUE);
				instance.setRechargeCardId(null);
				instance.setAmount(-difference.doubleValue());
				instance.setRemainingAmount(-difference.doubleValue());
			
				
				//代办人信息
				instance.setIsSystem(AccountCardInstance.IS_SYSTEM_TRUE);
				instance.setCreatorId(operator.getId());
				instance.setCreateTime(new Date());
				instance.setTs(new Date());
				accountCardInstanceDao.addAccountCardInstance(instance);
				
				
				
				accountTradeRecord.setBizId(instance.getId());
				accountTradeRecordDao.updateAccountTradeRecord(accountTradeRecord);
				
			}
			
			//订单消费记录字表
			computeFrozenAndUsableAmount(account);
			accountDao.updateAccount(account);
			

			accountTradeRecord.setRemainingAmount(account.getAmount());
			accountTradeRecordDao.updateAccountTradeRecord(accountTradeRecord);
			
		
	}




	
	@Override
	public AccountCardInstanceDao getAccountCardInstanceDaoInstance() {
		return accountCardInstanceDao;
	}
	
	
	public void addAccountPaymentAccount(AccountPaymentAccount accountPaymentAccount){
		accountPaymentAccountDao.addAccountPaymentAccount(accountPaymentAccount);
	}
//get set  start
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	public void setAccountTradeRecordDao(AccountTradeRecordDao accountTradeRecordDao) {
		this.accountTradeRecordDao = accountTradeRecordDao;
	}
	
	
	
	
	
	public void setBusinessFlowDAO(BusinessFlowDAO businessFlowDAO) {
		this.businessFlowDAO = businessFlowDAO;
	}
	
	public void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}
	
	public void setSubscriberConfirmDao(SubscriberConfirmDao subscriberConfirmDao) {
		this.subscriberConfirmDao = subscriberConfirmDao;
	}
	
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}

	public void setAccountTradeRecordDetailDao(AccountTradeRecordDetailDao accountTradeRecordDetailDao) {
		this.accountTradeRecordDetailDao = accountTradeRecordDetailDao;
	}


	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public void setAccountPaymentAccountDao(AccountPaymentAccountDao accountPaymentAccountDao) {
		this.accountPaymentAccountDao = accountPaymentAccountDao;
	}



	public void setAccountCardInstanceDao(AccountCardInstanceDao accountCardInstanceDao) {
		this.accountCardInstanceDao = accountCardInstanceDao;
	}






	public void setRechargeRecordDao(RechargeRecordDao rechargeRecordDao) {
		this.rechargeRecordDao = rechargeRecordDao;
	}






	@Override
	public List<AccountCardInstance> queryCardInstanceByIds(String ids) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id from AccountCardInstance a where 1=1 ");
		hql.append(" and id in (").append(ids).append(") ");
		hql.append(" order by isSystem asc,createTime desc");
		
		return accountCardInstanceDao.queryAccountCardInstanceList(hql.toString());
		
	}






	@Override
	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDaoInstance() {
		return accountTradeRecordDetailDao;
	}










	


	
	
//get set  end

}
