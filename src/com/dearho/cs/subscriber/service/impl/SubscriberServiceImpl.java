package com.dearho.cs.subscriber.service.impl;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.AccountDao;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberConfirmDao;
import com.dearho.cs.subscriber.dao.SubscriberDao;
import com.dearho.cs.subscriber.pojo.OrderMessage;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.dao.BusinessFlowDAO;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.Sha1Util;
import com.dearho.cs.util.StringHelper;

public class SubscriberServiceImpl implements SubscriberService {
	private SubscriberDao subscriberDao;
	private AccountDao accountDao;
	private BusinessFlowDAO businessFlowDAO;
	private SubscriberConfirmDao subscriberConfirmDao;
	
	private SMSCodeService smsCodeService;

	

	
	

	@Override
	public Subscriber addSubscriber(Subscriber s) {
		
		if(s==null){
			throw new RuntimeException("创建注册用户参数为空");
		}
		if(StringUtils.isEmpty(s.getPhoneNo())){
			throw new RuntimeException("创建注册用户手机号为空");
		}
			
		
		//创建会员基本信息
		Subscriber subscriber = new Subscriber();
		subscriber.setPhoneNo(s.getPhoneNo());
		subscriber.setPassword(Sha1Util.SHA1Encode(s.getPassword()));
		subscriber.setState(Subscriber.STATE_UNCONFIRMED);
		subscriber.setTs(new Date());
		subscriber.setCreateDate(new Date());
		subscriber.setSource(s.getSource());
		subscriber.setWechatUnionId(s.getWechatUnionId());
		subscriberDao.addSubscriber(subscriber);
		
		//创建会员账户信息
		Account account=new Account();
		account.setSubscriberId(subscriber.getId());
		account.setAmount(0d);
		account.setFrozenAmount(0d);
		account.setUsableAmount(0d);
		account.setTs(new Date());
		accountDao.addAccount(account);
		
		return subscriber;
		
	}
	
	

	@Override
	public Subscriber querySubscriberById(String id) {
		return subscriberDao.querySubscriberById(id);
	}
	
	public Subscriber querySubscriberAllInfoById(String id) {
		Subscriber subscriber= subscriberDao.querySubscriberById(id);
		if(subscriber==null){
			return null;
		}
		subscriber.setAccount(accountDao.getAccountById(id));
		return subscriber;
	}
	

	@Override
	public Page querySubscriberByPage(Page page, Subscriber subscriber) {
		StringBuffer hql=new StringBuffer();
		hql.append(" from Subscriber a where 1=1");
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and eventState="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and phoneNo like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
			
		

		}
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? "order by createDate desc" : page.getOrderByString());
		page=subscriberDao.querySubscriberByPage(page, hql.toString());
		return page;
	}

	@Override
	public Subscriber querySubscriberByPhoneNo(String phoneNo) {
		Subscriber subscriber=subscriberDao.querySubscriberByPhone(phoneNo);
		if(subscriber==null){
			return null;
		}
		subscriber.setAccount(accountDao.getAccountById(subscriber.getId()));
		return subscriber;
	}

	@Override
	public void updateSubscriber(Subscriber Subscriber) {
		Subscriber.setTs(new Date());
		subscriberDao.updateSubscriber(Subscriber);
	}

	@Override
	public Subscriber login(Subscriber Subscriber) {
		Subscriber loginSubscriber=subscriberDao.querySubscriberByPhone(Subscriber.getPhoneNo());
		if(loginSubscriber==null){
			return null;
		}
		
		if(loginSubscriber.getPassword().equals(Sha1Util.SHA1Encode(Subscriber.getPassword()))){
			return loginSubscriber;
		}else{
			return null;
		}
		
		
	}


	
	


	
	public Subscriber completeRegister(Subscriber s,boolean isMobile){
		//保存用户上传信息
		Subscriber subscriber=subscriberDao.querySubscriberById(s.getId());
		subscriber.setState(Subscriber.STATE_WAIT_CONFIRMED);
		subscriber.setName(s.getName());
		if(!StringUtils.isEmpty(s.getIdCardImg())){
			subscriber.setIdCardImg(s.getIdCardImg());
		}
		if(!StringUtils.isEmpty(s.getDrivingLicenseImg())){
			subscriber.setDrivingLicenseImg(s.getDrivingLicenseImg());
		}
		if(!StringUtils.isEmpty(s.getDrivingLicenseNo())){
			subscriber.setDrivingLicenseNo(s.getDrivingLicenseNo());
		}
		subscriber.setProvince(s.getProvince());
		subscriber.setCity(s.getCity());
		subscriber.setCounty(s.getCounty());
		subscriber.setAddress(s.getAddress());
		subscriber.setState(Subscriber.STATE_WAIT_CONFIRMED);
		subscriberDao.updateSubscriber(subscriber);
		
				
		//审核信息
		SubscriberConfirm subscriberConfirm= new SubscriberConfirm();
		subscriberConfirm.setSubscriberId(s.getId());
		subscriberConfirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_FALSE);
		if(isMobile){
			subscriberConfirm.setAttribute_1("wx");
		}
		subscriberConfirmDao.addSubscriberConfirm(subscriberConfirm);
		
		BusinessFlow businessFlow = new BusinessFlow();
		businessFlow.setApplicantId(s.getId());
		businessFlow.setApplyTime(new Date());
		businessFlow.setTs(new Date());
		businessFlow.setConfirmId(subscriberConfirm.getId());
		businessFlow.setBusinessId(null);
		businessFlow.setBusinessType(BusinessFlow.BUSINESS_TYPE_CONFIRM);
		
		businessFlowDAO.addBusinessFlow(businessFlow);
		
		return subscriber;
	}
	
	/**
	 *下单前会员状态校验， 只校验会员状态 TODO 是否退款处理中
	 */
	@Override
	public OrderMessage validateSubscriberForOrder(String subscriberId)  {
		Subscriber subscriber=querySubscriberAllInfoById(subscriberId);
		
		OrderMessage orderMessage= new OrderMessage();
		if(subscriber==null){
			orderMessage.setResult(OrderMessage.CODE_FAIL, "未查询到会员信息");
			return orderMessage;
		}else if(Subscriber.STATE_UNCONFIRMED.equals(subscriber.getState())){   //未审核	
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息未审核");
			return orderMessage;
		}else if(Subscriber.STATE_WAIT_CONFIRMED.equals(subscriber.getState())){//审核中
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息审核中");
			return orderMessage;
		}else if(Subscriber.STATE_NO_CONFIRMED.equals(subscriber.getState())){   //审核未通过
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息审核未通过");
			return orderMessage;
		}
		if(Subscriber.EVENT_STATE_HALF.equals(subscriber.getEventState())){//半锁
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"此账号已冻结");
			 return orderMessage;
		}else if(Subscriber.EVENT_STATE_FUll.equals(subscriber.getEventState())){//全锁
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"此账号已锁定");
			 return orderMessage;
		}
		Account account=subscriber.getAccount();
		if(Account.IS_REFUND_TRUE.equals(account.getIsRefund())){
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"退款流程处理中，暂不能租车");
			 return orderMessage;
		}
		orderMessage.setResult(OrderMessage.CODE_SUCCESS_ALL,"成功");
		orderMessage.setSubscriber(subscriber);
		return orderMessage;
	}
	
	/**
	 * 下单前会员状态，支付方式账户金额最小值校验
	 */
	/*public OrderMessage validateAccountForOrder(String subscriberId,Double minAmount,String payStyle)  {
		Subscriber subscriber=querySubscriberAllInfoById(subscriberId);
		
		OrderMessage orderMessage= new OrderMessage();
		if(subscriber==null){
			orderMessage.setResult(OrderMessage.CODE_FAIL, "未查询到会员信息");
			return orderMessage;
		}else if(Subscriber.STATE_UNCONFIRMED.equals(subscriber.getState())){   //未审核	
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息未审核");
			return orderMessage;
		}else if(Subscriber.STATE_WAIT_CONFIRMED.equals(subscriber.getState())){//审核中
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息审核中");
			return orderMessage;
		}else if(Subscriber.STATE_NO_CONFIRMED.equals(subscriber.getState())){   //审核未通过
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息审核未通过");
			return orderMessage;
		}
		if(Subscriber.EVENT_STATE_HALF.equals(subscriber.getEventState())){//半锁
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"此账号已冻结");
			 return orderMessage;
		}else if(Subscriber.EVENT_STATE_FUll.equals(subscriber.getEventState())){//全锁
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"此账号已锁定");
			 return orderMessage;
		}
		
		//账号支付
		if(Account.PAY_TYPE_ACCOUNT.toString().equals(payStyle)){
		
			Boolean hasApplyRefund=hasApplyRefund(subscriberId);//退款申请比较
			if(hasApplyRefund){
				 orderMessage.setResult(OrderMessage.CODE_FAIL,"申请退款中，不能租车！");
			}else{
				Boolean hasEnoughMoney=false;
				Account account=accountDao.getAccountById(subscriberId);
				//BigDecimal accountMoney= new BigDecimal(account.getAmount());
				BigDecimal minOrderMoney= new BigDecimal(minAmount);
				if(accountMoney.compareTo(minOrderMoney)>=0  ){
					hasEnoughMoney=true;
				}
				if(hasEnoughMoney){
					 orderMessage.setResult(OrderMessage.CODE_SUCCESS_ALL,"账户金额足够");
					 orderMessage.setSubscriber(subscriber);
				}else{
					// orderMessage.setResult(OrderMessage.CODE_FAIL,"账户金额不足");
				}
			}
			
		//信用卡支付
		}else if(Account.PAY_TYPE_CREDIT.toString().equals(payStyle)){
			AccountCreditCardBinding accountCreditCardBinding=accountCreditCardBindingDao.getAccountCreditCardBindingById(subscriberId);
			if(accountCreditCardBinding!=null){
				 orderMessage.setResult(OrderMessage.CODE_SUCCESS_ALL,"已绑定信用卡");
				 orderMessage.setSubscriber(subscriber);
				 return orderMessage;
			}else{
				 orderMessage.setResult(OrderMessage.CODE_FAIL,"请您先在个人中心绑定信用卡");
			}
			
		}else{
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"未知的支付方式");
			 return orderMessage;
		}
		
		return orderMessage;
	}*/
	
	/*
	public OrderMessage validateAccountForOrder(String subscriberId,String payStyle)  {
		Subscriber subscriber=querySubscriberAllInfoById(subscriberId);
		
		OrderMessage orderMessage= new OrderMessage();
		if(subscriber==null){
			orderMessage.setResult(OrderMessage.CODE_FAIL, "未查询到会员信息");
			return orderMessage;
		}else if(Subscriber.STATE_UNCONFIRMED.equals(subscriber.getState())){   //未审核	
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息未审核");
			return orderMessage;
		}else if(Subscriber.STATE_WAIT_CONFIRMED.equals(subscriber.getState())){//审核中
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息审核中");
			return orderMessage;
		}else if(Subscriber.STATE_NO_CONFIRMED.equals(subscriber.getState())){   //审核未通过
			orderMessage.setResult(OrderMessage.CODE_FAIL,"资料信息审核未通过");
			return orderMessage;
		}
		if(Subscriber.EVENT_STATE_HALF.equals(subscriber.getEventState())){//半锁
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"此账号已冻结");
			 return orderMessage;
		}else if(Subscriber.EVENT_STATE_FUll.equals(subscriber.getEventState())){//全锁
			 orderMessage.setResult(OrderMessage.CODE_FAIL,"此账号已锁定");
			 return orderMessage;
		}
		
		Boolean hasCreditCard=false;
		Boolean hasEnoughMoney=false;
		Boolean hasApplyRefund=hasApplyRefund(subscriberId);//退款申请比较
		
		//绑定信用卡账号
		AccountCreditCardBinding accountCreditCardBinding=accountCreditCardBindingDao.getAccountCreditCardBindingById(subscriberId);
		if(accountCreditCardBinding!=null){
			hasCreditCard=true;
		}
		//账户金额比较
		Account account=accountDao.getAccountById(subscriberId);
		BigDecimal accountMoney= new BigDecimal(account.getAmount());
		BigDecimal minOrderMoney= new BigDecimal(minAmount);
		if(accountMoney.compareTo(minOrderMoney)>=0  ){
			hasEnoughMoney=true;
		}
		
		if(hasApplyRefund){
			if(hasCreditCard){
				 orderMessage.setResult(OrderMessage.CODE_SUCCESS_CREDIT_CARD,"已绑定信用卡");
				 orderMessage.setSubscriber(subscriber);
				 return orderMessage;
			}else{
				orderMessage.setResult(OrderMessage.CODE_FAIL,"退款流程处理中，如需使用请绑定信用卡");
				 return orderMessage;
			}
		}else{
			if(hasCreditCard && !hasEnoughMoney){
				 orderMessage.setResult(OrderMessage.CODE_SUCCESS_CREDIT_CARD,"已绑定信用卡");
				 orderMessage.setSubscriber(subscriber);
				 return orderMessage;
			}else if(!hasCreditCard && hasEnoughMoney){
				 orderMessage.setResult(OrderMessage.CODE_SUCCESS_ACCOUNT,"账户金额满足");
				 orderMessage.setSubscriber(subscriber);
				 return orderMessage;
			}else if(hasCreditCard && hasEnoughMoney){
				 orderMessage.setResult(OrderMessage.CODE_SUCCESS_ALL,"成功");
				 orderMessage.setSubscriber(subscriber);
				 return orderMessage;
			}else{
				 orderMessage.setResult(OrderMessage.CODE_FAIL,"请充值或绑定信用卡");
				 return orderMessage;
			}
		}
	}*/
	
	/*public  Boolean hasApplyRefund(String accountId){
		Boolean result=false;
		
		StringBuffer hql= new StringBuffer();
		hql.append("select a.id from SubscriberConfirm a  where a.subscriberId= '"+accountId+"' and  isComplete="+SubscriberConfirm.IS_COMPLETE_FALSE);

		List<SubscriberConfirm> list=subscriberConfirmDao.querySubscriberConfirmList(hql.toString());
		if(list!=null &&list.size()>0){
			result=true;
		}
		return result;
	}*/
	
	/*
	
	if(s!=null&&StringHelper.isNotEmpty(s.getId())){
				subscriber=subscriberService.querySubscriberAllInfoById(s.getId());
				if(subscriber.getState()==Subscriber.STATE_UNCONFIRMED){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "您的资料信息未审核,请等审核通过后租车");
				}else if(subscriber.getState()==Subscriber.STATE_WAIT_CONFIRMED){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "资料信息正在审核中,,请等审核通过后租车");
				}else if(subscriber.getState()==Subscriber.STATE_NO_CONFIRMED){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "您的信息审核未通过,重新提交资料!");
				}else if(subscriber.getEventState()!=null){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "您的帐户已锁定,暂时不能租车");
				}else if (subscriber.getAccount().getIsOrder()==null || !subscriber.getAccount().getIsOrder().equals(Account.IS_ORDER_ON)) {
					System.out.println("进来了");
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "账户已锁定暂时不能租车！");
				}else{
					result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "");
				}
				if(StringHelper.isNotEmpty(state)&&state.equals("step3")){
					if(null==subscriber.getAccountCreditCardBinding() || StringHelper.isNotEmpty(subscriber.getAccountCreditCardBinding().getSubscriberId())){
						result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "未绑定信用卡,暂时不能租车");
					}
				}
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请先登录在下单");
			}
			
			*/
	
	/*
	@Override
	public String checkSubscriberStateForAccount(String subscriberId) {
		Subscriber subscriber=subscriberDao.querySubscriberById(subscriberId);
		Account account=accountDao.getAccountById(subscriberId);
		String msg=null;
		if(subscriber==null){
			msg="未查询到会员信息";
		}else if(Subscriber.EVENT_STATE_HALF.equals(subscriber.getEventState())){//半锁
			msg="此账号已冻结，不能进行账号操作。<br/> 如有疑问，请联系客服。";
		}else if(Account.IS_ORDER_OFF.equals(account.getIsOrder())){
			msg="会员账号退款流程处理中，暂不能进行账号操作!";
		}
		return msg;
	}*/

	//set
	public void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setBusinessFlowDAO(BusinessFlowDAO businessFlowDAO) {
		this.businessFlowDAO = businessFlowDAO;
	}



	public void setSubscriberConfirmDao(SubscriberConfirmDao subscriberConfirmDao) {
		this.subscriberConfirmDao = subscriberConfirmDao;
	}




	
	/**
	 * 最新充值
	 */
	@Override
	public Page querySubscriberRechargeLatestByPage(Page page, Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		
		hql.append("SELECT a.id as id  FROM acc_trade_record_list a  where  type=1 and result=1  ");
		
		
		
		if(subscriber!=null){
			if(subscriber.getState()!=null || subscriber.getEventState()!=null || StringUtils.isNotEmpty(subscriber.getPhoneNo()) || StringUtils.isNotEmpty(subscriber.getSex()) ){
				hql.append(" and a.subscriber_id in ( select id from sub_subscriber where 1=1 ");
			}
			if(subscriber.getState()!=null){
				hql.append(" and state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
			if(subscriber.getState()!=null || subscriber.getEventState()!=null || StringUtils.isNotEmpty(subscriber.getPhoneNo()) || StringUtils.isNotEmpty(subscriber.getSex()) ){
				hql.append(" ) ");
			}
		}
		hql.append("group by a.subscriber_id  order by a.ts desc");
	//	hql.append(" group by a.subscriber_id   ");
	//	hql.append("order by a.create_time desc ");
		//hql.append(StringHelper.isEmpty(page.getOrderByString2()) ? "" : page.getOrderByString2());
		
		hql.insert(0, "select b.id from ( ");
		hql.append(" )b");
		
		page.setCountField("b.id");
		page=subscriberDao.querySubscriberByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberRefundLatestLatestByPage(Page page,	Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from acc_current_account a ,sub_subscriber s  where s.id=a.subscriber_id and a.type="+Account.TYPE_REFUND );
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
		}
		hql.append(" group by a.subscriber_id   ");
		hql.append("order by a.create_time desc ");
	//	hql.append(StringHelper.isEmpty(page.getOrderByString2()) ? "" : page.getOrderByString2());
		
		hql.insert(0, "select b.id from ( ");
		hql.append(" )b");
		
		page.setCountField("b.id");
	//	page=subscriberDao.querySubscriberRechargeLatestByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberRechargeMostByPage(Page page,	Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		hql.append("select a.subscriber_id as subscriber_id ,sum(a.amount) as sum_amount from acc_current_account a ,sub_subscriber s  where s.id=a.subscriber_id and a.type="+Account.TYPE_RECHARGE);
		//hql.append("select a.subscriber_id as subscriber_id ,sum(a.amount) as sum_amount from acc_current_account a   where  a.type="+Account.TYPE_RECHARGE);
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
		}
		hql.append(" group by a.subscriber_id   ");
		hql.append(" order by sum(a.amount) desc ");
		
		
		hql.insert(0, "select b.subscriber_id ,sum_amount from ( ");
		hql.append(" )b");
		
		page.setCountField("b.subscriber_id");
//		page=subscriberDao.querySubscriberRechargeMostByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberConsumerMostByPage(Page page,Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		
		hql.append("select a.subscriber_id as subscriber_id ,sum(a.amount) as sum_amount from acc_trade_record a ,sub_subscriber s  where s.id=a.subscriber_id and a.type="+Account.TYPE_ORDER);
		
	
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
		}
		hql.append(" group by a.subscriber_id   ");
		hql.append(" order by sum(a.amount) desc ");
		
		
		hql.insert(0, "select b.subscriber_id ,sum_amount from ( ");
		hql.append(" )b");
		
		page.setCountField("b.subscriber_id");
//		page=subscriberDao.querySubscriberRechargeMostByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberOrderFirstByPage(Page page, Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from acc_trade_record a ,sub_subscriber s  where s.id=a.subscriber_id and a.type="+Account.TYPE_ORDER+" and (is_auto_clear is null or is_auto_clear !=1)");
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
		}
		hql.append(" group by a.subscriber_id  having count(a.subscriber_id)=1 ");
		
		//hql.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		
		
		hql.insert(0, "select b.id  from ( ");
		hql.append(" )b");
		
		page.setCountField("b.id");
		page=subscriberDao.querySubscriberOrderFirstByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberOrderLatestByPage(Page page,Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from acc_trade_record a ,sub_subscriber s  where s.id=a.subscriber_id and a.type="+Account.TYPE_ORDER+" and (is_auto_clear is null or is_auto_clear !=1)");
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and s.name="+subscriber.getName());
			}
		}
		hql.append(" group by a.subscriber_id   ");
		
		hql.append(" order by a.trade_time desc");
		
		
		hql.insert(0, "select b.id  from ( ");
		hql.append(" )b");
		
		page.setCountField("b.id");
		page=subscriberDao.querySubscriberOrderFirstByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberOrderMostByPage(Page page, Subscriber subscriber) {

		StringBuffer hql=new StringBuffer();
		
		hql.append("select a.subscriber_id as  subscriber_id, count(*) as allOrder from  acc_trade_record a , sub_subscriber s  where s.id=a.subscriber_id and a.type="+Account.TYPE_ORDER );
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and s.name="+subscriber.getName());
			}
		}
		hql.append(" group by a.subscriber_id   ");
		hql.append(" order by count(*) desc ");
		
		
		hql.insert(0, "select b.subscriber_id ,allOrder from ( ");
		hql.append(" )b");
		
		page.setCountField("b.subscriber_id");
		page=subscriberDao.querySubscriberOrderMostByPage(page, hql.toString());
		return page;
	
	}



	@Override
	public Page querySubscriberOrderLongestByPage(Page page,	Subscriber subscriber) {
		StringBuffer hql=new StringBuffer();
		
		hql.append("select a.member_id as subscriber_id ,sum(orders_duration) as sumOrderTime from ord_orders a , sub_subscriber s  where s.id=a.member_id    and (a.state=2 or a.state=0)  ");
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and s.state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and s.event_state="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and s.phone_no like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and s.name="+subscriber.getName());
			}
		}
		hql.append(" group by a.member_id   ");
		hql.append(" order by sum(orders_duration) desc ");
		
		
		hql.insert(0, "select b.subscriber_id ,sumOrderTime from ( ");
		hql.append(" )b");
		
		page.setCountField("b.subscriber_id");
//		page=subscriberDao.querySubscriberRechargeMostByPage(page, hql.toString());
		return page;
	}

	@Override
	public Page querySubscriberLastSevenDayMember(Page page,
			Subscriber subscriber) {
		StringBuffer hql=new StringBuffer();
		hql.append(" from Subscriber a where 1=1");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -7);
		hql.append(" and a.createDate > '").append(DateUtil.getChar19DateString(cal.getTime())).append("'");
		hql.append(" and a.createDate < '").append(DateUtil.getChar19DateString(new Date())).append("'");
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? "order by createDate desc" : page.getOrderByString());
		page=subscriberDao.querySubscriberByPage(page, hql.toString());
		return page;	
	}



	public  String sendSMSCode(String type,String phoneNo,Integer channel) throws Exception{
		String result=null;
		
		if(StringUtils.isEmpty(phoneNo)){
			result = "手机号不能为空";
			return result;
		}
		
		Subscriber s=querySubscriberByPhoneNo(phoneNo);
		String message=null;
		if(Constants.SUBSCIRBER_REGISTER_PHONE_CODE.equals(type)){
			 message=" 乐途出行用户注册验证码:code，此验证码5分钟内有效，如非本人操作请忽略。";
			 if(s != null){
				  result ="手机号已注册";
				  return result;
			}
				
		}else if(Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE.equals(type)){
			 message=" 乐途出行用户修改手机号验证码:code，此验证码5分钟内有效，如非本人操作请忽略。";
			 if(s != null){
				  result ="手机号已注册";
				  return result;
			}
		}else if(Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE.equals(type)){
			 message=" 乐途出行用户修改手机号验证码:code，此验证码5分钟内有效，如非本人操作请忽略。";
			 if(s == null){
				  result ="手机号为空";
				  return result;
			}
		}else if(Constants.SUBSCRIBER_LOGIN_CODE.equals(type)){
			 message=" 乐途出行用户登录验证码:code，此验证码5分钟内有效。";
			 if(s == null){
				  result ="用户未注册";
				  return result;
			}
		}
		
		SMSCode smsCode=new SMSCode();
		smsCode.setPhoneNo(phoneNo);
		if(channel!=null){
			smsCode.setChannel(channel);
		}
		smsCode.setType(type);
		smsCode=smsCodeService.getLatestSMSCode(smsCode, Constants.REGISTER_SMS_VALID_RETRY_MINUTE);
		if(smsCode!=null){
			result = "120秒内只能获取一次验证码，请稍后重试";
			return result;
		}

		
		StringBuffer code=new StringBuffer(); 
		Random r=new Random(); 
		for(int i=0;i<4;i++){ 
			code.append(r.nextInt(10));
		}; 
		
		SMSCode smsCode2=new SMSCode();
		smsCode2.setType(type);
		smsCode2.setPhoneNo(phoneNo);
		smsCode2.setChannel(channel);
		smsCode2.setMessage(message);;
		smsCode2.setCode(code.toString());
		smsCode2.setTs(new Date());
		smsCodeService.addSMSCode(smsCode2);
		SMSUtil.sendSMS(phoneNo, message.replace("code", code),SMSRecord.TYPE_REGISTER);
		
		result="ok";
		return result;
	}
	

	
	public List<Subscriber> querySubscriber(Subscriber subscriber){
		
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id  from Subscriber a where 1=1 ");
		
		if(subscriber!=null){
			if(subscriber.getState()!=null){
				hql.append(" and state="+subscriber.getState());
			}
			if(subscriber.getEventState()!=null){
				hql.append(" and eventState="+subscriber.getEventState());
			}
			if(StringUtils.isNotEmpty(subscriber.getPhoneNo())){
				hql.append(" and phoneNo like '%"+subscriber.getPhoneNo()+"%'");
			}
			if(StringUtils.isNotEmpty(subscriber.getSex())){
				hql.append(" and sex = '"+subscriber.getSex()+"'");
			}
			if(StringUtils.isNotEmpty(subscriber.getName())){
				hql.append(" and name like '%"+subscriber.getName()+"%'");
			}
		}
		hql.append("order by phoneNo asc");
		
		return subscriberDao.getSubscriberByHql(hql.toString());
		
	}



	@Override
	public List<Subscriber> searchSubscribersIn(String ids) {
		String hql = "select a.id from Subscriber a where a.id in ("+ids+")";
		return subscriberDao.getSubscriberByHql(hql);
	}



	@Override
	public List<Subscriber> searchSubscribersNotIn(String ids) {
		String hql = "select a.id from Subscriber a where a.id not in ("+ids+")";
		return subscriberDao.getSubscriberByHql(hql);
	}



	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
	}



	@Override
	public Subscriber querySubscriberByUnionId(String unionId) {
		return subscriberDao.querySubscriberByUnionid(unionId);
	}



	@Override
	public Subscriber querySubscriberByName(String name) {
		return subscriberDao.querySubscriberByName(name);
	}



	

}
