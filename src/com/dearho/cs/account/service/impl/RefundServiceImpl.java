package com.dearho.cs.account.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.dao.SubscriberConfirmDao;
import com.dearho.cs.subscriber.dao.SubscriberDao;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.sys.dao.BusinessFlowDAO;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wxpay.WxPayUtil;

public class RefundServiceImpl extends PayServiceBase implements  RefundService {
	
	private AccountDao accountDao;
	private AccountCardInstanceDao accountCardInstanceDao;//充值卡实例
	
	private AccountTradeRecordDao accountTradeRecordDao;      //交易记录
	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;//交易记录详情
	
	private SubscriberDao subscriberDao;

	private  AccountPaymentAccountDao accountPaymentAccountDao;
	
	private OrdersDetailService ordersDetailService;
	
	private OrdersService ordersService;
	
	private SubscriberConfirmDao subscriberConfirmDao;
	private BusinessFlowDAO businessFlowDAO;
	
	private RechargeCardService rechargeCardService;
	
	
	
	
	
	/**
	 * 申请退款校验
	 */
	

	
	/**
	 * 提前 退款截止时间小于等于5天的，则需要用户填写退款账号
	 * 申请退款流程
	 */
	public   void applyRefund(String subscriberId,String paymentId) {
		
		//
		Account account= accountDao.getAccountById(subscriberId);
		account.setIsRefund(Account.IS_REFUND_TRUE);
		accountDao.updateAccount(account);
		
		//审核信息
		SubscriberConfirm subscriberConfirm= new SubscriberConfirm();
		subscriberConfirm.setSubscriberId(subscriberId);
		subscriberConfirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_FALSE);
		subscriberConfirm.setAttribute_1(paymentId);
		subscriberConfirm.setAttribute_2(String.format("%.2f", account.getAmount()));
		
		List< AccountCardInstance> instanceList=accountCardInstanceDao.queryEnabledCardInstanceByAccountId(subscriberId);
		StringBuffer buf=new StringBuffer();
		if(instanceList!=null && instanceList.size()>0){
			for(AccountCardInstance instance:instanceList){
				if(buf.length()>0){
					buf.append(",");
				}
				buf.append("'").append(instance.getId()).append("'");
			}
			
		}
		
		subscriberConfirm.setAttribute_3(buf.toString());
		subscriberConfirmDao.addSubscriberConfirm(subscriberConfirm);
		
		
		//流程信息
		BusinessFlow businessFlow = new BusinessFlow();
		businessFlow.setConfirmId(subscriberConfirm.getId());
		businessFlow.setApplicantId(subscriberId);
		businessFlow.setApplyTime(new Date());
		businessFlow.setTs(new Date());
		businessFlow.setBusinessId(null);
		businessFlow.setBusinessType(BusinessFlow.BUSINESS_TYPE_APPLY_REFUND);
		businessFlowDAO.addBusinessFlow(businessFlow);
		
		
		
	}

	
	
	/**
	 * 创建退款交易记录，不调银行退款接口
	 * 
	 * @param confirmId 退款审批id
	 * @param operatorId 操作人id
	 * @throws Exception
	 */
	public synchronized AccountTradeRecord applyRefundSuccess(SubscriberConfirm resultConfirm,String operatorId) throws  Exception{
		
		SubscriberConfirm confirm=subscriberConfirmDao.getSubscriberConfirm(resultConfirm.getId());
		String subscriberId=confirm.getSubscriberId();
		//创建退款记录主表
		AccountTradeRecord  mainRecord=new AccountTradeRecord();
		mainRecord.setTradeOrderNo(getRefundTradeNo());
		mainRecord.setAmount(0d);
		mainRecord.setTradeTime(new Date());
		
		AccountPaymentAccount transferAccount=null;
		
		List< AccountCardInstance> instanceList=accountCardInstanceDao.queryEnabledCardInstanceByAccountId(subscriberId);
		for(AccountCardInstance instance:instanceList){
		
			//要得到卡的充值记录
			AccountTradeRecord oldAccountTradeRecord=accountTradeRecordDetailDao.getTradeRecordByCarInstanceId(instance.getId());
			Integer oldOrderPayType=oldAccountTradeRecord.getPayType();
			Date orderOrderTradeTime=oldAccountTradeRecord.getTradeTime();
			
			String paymentAccountId=confirm.getAttribute_1();
			Boolean isBack=null;
			//验证是否可以退款
			if(valideCanRefundBack(oldOrderPayType,orderOrderTradeTime)){
				isBack=true;
			}else{
				isBack=false;
				transferAccount=accountPaymentAccountDao.getAccountPaymentAccountById(paymentAccountId);
				if(transferAccount==null){
					throw new RuntimeException("走转账流程，未找到转账账户");
				}
			}
			
			String refundTradeNo=oldAccountTradeRecord.getAccountTradeRecordDetail().getTradeNo();
		
			
			//2.交易记录主表，增加记录
			AccountTradeRecord accountTradeRecord= new AccountTradeRecord();
			accountTradeRecord.setSubscriberId(subscriberId);
			accountTradeRecord.setType(Account.TYPE_REFUND);
			accountTradeRecord.setPayChannel(Account.PAY_CHANNEL_CONSOLE);
			accountTradeRecord.setResult(AccountTradeRecord.RESULT_CREATE);
			
			
			//退款的充值卡实例
			accountTradeRecord.setBizId(instance.getId());
			accountTradeRecord.setTradeOrderNo(mainRecord.getTradeOrderNo());
			accountTradeRecord.setSubOrderId(getRefundTradeNo());
			
			
			accountTradeRecord.setAmount(instance.getRemainingAmount());
		
			//充值卡是否走退款流程
			if(isBack){
				accountTradeRecord.setPayType(oldOrderPayType);
				accountTradeRecord.setIsPresetCard(AccountTradeRecord.IS_AUTO_CLEAR_TRUE);//走退款流程
				accountTradeRecord.setDescription("账户退款 "+instance.getRemainingAmount().doubleValue()+"元");
			}else{
				accountTradeRecord.setPayType(transferAccount.getType());
				accountTradeRecord.setIsPresetCard(AccountTradeRecord.IS_AUTO_CLEAR_FALSE);//走转账流程
				accountTradeRecord.setDescription("账户退款 "+instance.getRemainingAmount().doubleValue()+"元");
			}
			
			accountTradeRecord.setTradeTime(mainRecord.getTradeTime());
			accountTradeRecord.setTs(new Date());
			
			accountTradeRecordDao.addAccountTradeRecord(accountTradeRecord);
			
			
			//3.交易记录子表，添加记录
			
			AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
			accountTradeRecordDetail.setTradeRecordId(accountTradeRecord.getId());
			accountTradeRecordDetail.setSubscriberId(subscriberId);
			accountTradeRecordDetail.setAmount(instance.getRemainingAmount());
			accountTradeRecordDetail.setTs(new Date());
			if(isBack){
				accountTradeRecordDetail.setRefundTradeNo(refundTradeNo);
			}else{
				accountTradeRecordDetail.setPaymentAccountId(paymentAccountId);
			}
			
			accountTradeRecordDetailDao.addAccountTradeRecordDetail(accountTradeRecordDetail);
			
			
		
		}
		
		
		//审核流程完成
		confirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_TRUE);
		confirm.setResult(SubscriberConfirm.RESULT_SUCCESS);
		confirm.setResultDesc(resultConfirm.getResultDesc());
		confirm.setRemark(resultConfirm.getRemark());
		subscriberConfirmDao.updateSubscriberConfirm(confirm);
		
		//工作流表完成
		BusinessFlow businessFlow =businessFlowDAO.getBusinessFlowByConfirmId(confirm.getId()); 
		businessFlow.setBusinessId(mainRecord.getTradeOrderNo());
		businessFlow.setReviewerId(operatorId);
		businessFlow.setReviewTime(new Date());
		businessFlow.setResult(BusinessFlow.RESULT_SUCCESS);
		businessFlow.setResultDesc("退款成功");
		businessFlowDAO.updateBusinessFlow(businessFlow);
		
		
		
		Subscriber subscriber = subscriberDao.querySubscriberById(confirm.getSubscriberId());
		String smsMessage= "您的退款申请已审批通过，5个工作日内将款项退还到您的账户，请注意查收。";
		String phoneNo=subscriber.getPhoneNo();
		SMSUtil.sendSMS(phoneNo, smsMessage,SMSRecord.TYPE_REFUND);
		
		
		return mainRecord;
	}


	
	//由于上一步可能会出现异常，则消费记录创建成功后才走退款流程
	
	/**
	 * 处理退款记录
	 * @throws Exception 
	 */
	public void handleRefundRecord(String tradeOrderNo,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//退款记录
		List<AccountTradeRecord> list=accountTradeRecordDao.queryTradeRecordByTradeOrderNo(tradeOrderNo,Account.TYPE_REFUND);
		for(AccountTradeRecord record:list){
			//已处理的退款请求，则略过
			if(AccountTradeRecord.RESULT_SUCCESS.equals(record.getResult())){
				continue;
			}
			//退款充值卡实例
			AccountCardInstance cardInstance=accountCardInstanceDao.getAccountCardInstanceById(record.getBizId());
			
			//得到新充值记录
			List<AccountTradeRecordDetail> detailList =	accountTradeRecordDetailDao.getAccountTradeRecordDetailByBizId(record.getId());
			AccountTradeRecordDetail detail=detailList.get(0);
			
			//走退款流程
			if(AccountTradeRecord.IS_PRESET_CARD_TRUE.equals(record.getIsPresetCard())){
				String transactionId=detail.getRefundTradeNo();
				String outRefundNo=record.getSubOrderId();//新交易流水
				double totalFee=cardInstance.getAmount();
				double refundFee=cardInstance.getRemainingAmount();
				
				//微信退款
				 if(Account.PAY_TYPE_WECHAT.equals(record.getPayType())){
					
					WxPayUtil.refund(transactionId, outRefundNo, totalFee, refundFee);
				}else{
					throw new RuntimeException("退款流程：未知的支付方式");
				}
				
				
			//走转账流程
			}else{
				if(StringUtils.isEmpty(detail.getPaymentAccountId())){
					throw new RuntimeException("退款转账：未找到退款账号");
				}
				AccountPaymentAccount accountPaymentAccount=accountPaymentAccountDao.getAccountPaymentAccountById(detail.getPaymentAccountId());
				//微信转账
				 if(Account.PAY_TYPE_WECHAT.equals(record.getPayType())){
					
					WxPayUtil.mpPayment(record.getSubOrderId(), accountPaymentAccount.getAccountNo(), record.getDescription(), record.getAmount(), request, response);
				
				}else{
					throw new RuntimeException("退款转账：未知的支付方式");
				}
			}
		}
		
	}
	
	
	
	
	
	/**
	 * 退款--是否需要填写退款账号
	 * @param subscriberId
	 * @return
	 */
	public boolean checkIsNeedRefundAccount(String subscriberId){
		boolean isNeedAccount=false;
		List< AccountCardInstance> instanceList=accountCardInstanceDao.queryEnabledCardInstanceByAccountId(subscriberId);
		if(instanceList==null||instanceList.size()==0){
			isNeedAccount=true;
			return isNeedAccount;
		}
		for(AccountCardInstance instance:instanceList){
			
			/**
			 * 是否代充无充值来源
			 */
			if(AccountCardInstance.IS_SYSTEM_TRUE.equals(instance.getIsSystem())){
				isNeedAccount=true;
				break;
			}
			
			//要得到卡的充值记录
			AccountTradeRecord oldAccountTradeRecord=accountTradeRecordDetailDao.getTradeRecordByCarInstanceId(instance.getId());
			Integer oldOrderPayType=oldAccountTradeRecord.getPayType();
			Date orderOrderTradeTime=oldAccountTradeRecord.getTradeTime();
			

			//验证是否可以退款
			if(!valideCanRefundBackAddTransit(oldOrderPayType,orderOrderTradeTime)){
				isNeedAccount=true;
				break;
			}
		}
		return isNeedAccount;
	}
	
	
	/**
	 * 添加中转时间后，是否还能退款至支付渠道
	 * @param paytype
	 * @param tradeTime
	 * @return
	 */
	private boolean valideCanRefundBackAddTransit(Integer paytype,Date tradeTime){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tradeTime);
		if(Account.PAY_TYPE_ALIPAY.equals(paytype)){
			calendar.add(Calendar.MONTH, Constants.ALIPAY_REFUND_VALID_MONTH);
		}else if(Account.PAY_TYPE_WECHAT.equals(paytype)){
			calendar.add(Calendar.MONTH, Constants.WXPAY_REFUND_VALID_MONTH);
		}else if(Account.PAY_TYPE_UNIONPAY.equals(paytype)){
			calendar.add(Calendar.MONTH, Constants.UNIONPAY_REFUND_VALID_MONTH);
		}else{
			throw new RuntimeException("退款：未知的支付类型");
		}
		
		//5天后的处理时间
		Calendar handelCalendar = Calendar.getInstance();
		handelCalendar.add(Calendar.DATE, Constants.REFUND_TRANSIT_DAY);
		if((handelCalendar.getTime()).before(calendar.getTime())){
			System.out.println("退款时间段内，能退款");
			return true;
		}else{
			System.out.println("退款时间段内，不能退款，走转账");
			return false;
		}
	}
	

	/**
	 * 退款申请不通过
	 */
	public   void applyRefundFail(SubscriberConfirm resultConfirm,User operator) throws Exception{
		SubscriberConfirm confirm=subscriberConfirmDao.getSubscriberConfirm(resultConfirm.getId());
		confirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_TRUE);
		confirm.setResult(SubscriberConfirm.RESULT_FAIL);
		confirm.setResultDesc(resultConfirm.getResultDesc());
		confirm.setRemark(resultConfirm.getRemark());
		subscriberConfirmDao.updateSubscriberConfirm(confirm);
		
		Subscriber subscriber=subscriberDao.querySubscriberById(confirm.getSubscriberId());
		
		
		Account account =accountDao.getAccountById(confirm.getSubscriberId());
		account.setIsRefund(Account.IS_REFUND_FALSE);
		accountDao.updateAccount(account);
		
		BusinessFlow businessFlow=businessFlowDAO.getBusinessFlowByConfirmId(confirm.getId());
		businessFlow.setReviewerId(operator.getId());
		businessFlow.setReviewTime(new Date());
		businessFlow.setResult(BusinessFlow.RESULT_FAIL);
		businessFlow.setResultDesc(resultConfirm.getResultDesc());
		businessFlowDAO.updateBusinessFlow(businessFlow);
		
		StringBuffer smsMessageBuffer = new StringBuffer();
		smsMessageBuffer.append("您的退款申请审批未通过，原因："+resultConfirm.getResultDesc()+"。");

		String phoneNo=subscriber.getPhoneNo();
		
		SMSUtil.sendSMS(phoneNo, smsMessageBuffer.toString(),SMSRecord.TYPE_REFUND);
	}
	
	
	
	
	
	
	
	
	@Override
	public void refundCallBack(PayResponseMessage payResponseMessage)throws Exception {
		//1.退款记录校验  
		List<AccountTradeRecord> accountTradeRecordList=accountTradeRecordDao.queryTradeRecordBySubOrderNo(payResponseMessage.getOrderId(),Account.TYPE_REFUND);
		
		if(accountTradeRecordList==null||accountTradeRecordList.size()==0){
			throw new Exception("退款订单不存在");
		}
		if(accountTradeRecordList.size()>1){
			throw new Exception("根据退款单号"+payResponseMessage.getOrderId()+"查到多笔退款记录");
		}
		AccountTradeRecord accountTradeRecord=accountTradeRecordList.get(0);
		if(!Account.TYPE_REFUND.equals(accountTradeRecord.getType())){
			throw new Exception("不为退款订单");
		}
		if(AccountTradeRecord.RESULT_SUCCESS.equals(accountTradeRecord.getResult())){
			return;
		}
		
		
		//2获取充值卡实例
		AccountCardInstance instance=accountCardInstanceDao.getAccountCardInstanceById(accountTradeRecord.getBizId());
		 if(instance==null){
			 throw new Exception("充值卡实例不存在");
		 }
		
		//3.账户主表变更 
		Account account=accountDao.getAccountById(accountTradeRecord.getSubscriberId()); 
		account.setLastOperateAmount(instance.getRemainingAmount());
		
		//金额
		BigDecimal cardAmount = new BigDecimal(instance.getRemainingAmount());  
		BigDecimal accountAmount = new BigDecimal(account.getAmount());
		account.setAmount(accountAmount.subtract(cardAmount).doubleValue());
		
		
		
		
		
		//4.充值卡实例修改
		instance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
		instance.setRemainingAmount(0d);
		instance.setTs(new Date());
		
		accountCardInstanceDao.updateAccountCardInstance(instance);
		
		

		//5.更新account 押金及可用余额
		computeFrozenAndUsableAmount(account);
		
		
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
		accountTradeRecordDetail.setTradeNo(payResponseMessage.getQueryId());
		accountTradeRecordDetailDao.updateAccountTradeRecordDetail(accountTradeRecordDetail);

	
		//更新会员退款记录状态
		AccountTradeRecord queryAccountTradeRecord=new AccountTradeRecord();
		queryAccountTradeRecord.setTradeOrderNo(accountTradeRecord.getTradeOrderNo());
		queryAccountTradeRecord.setResult(AccountTradeRecord.RESULT_CREATE);
		List<AccountTradeRecord> waitRefundList=accountTradeRecordDao.queryAccountTradeRecordList(queryAccountTradeRecord);
		if(waitRefundList==null || waitRefundList.size()==0){
			account.setIsRefund(Account.IS_REFUND_FALSE);
		}
	
		account.setLastOperateTime(new Date());
		account.setLastOperateType(Account.TYPE_REFUND);
		
		accountDao.updateAccount(account);
	}

	
	
	
	
	/**
	 * TODO 
	 */
	public void checkApplyRefund(String subscriberId) throws Exception{

	
		Subscriber s =subscriberDao.querySubscriberById(subscriberId);
		/** 校验 start **/
		if (Subscriber.EVENT_STATE_HALF.equals(s.getEventState())) {
			throw new Exception("此账号已冻结，不能申请退款。<br/> 如有疑问，请联系客服。");
		}
		if (Subscriber.STATE_WAIT_CONFIRMED.equals(s.getState())
				|| Subscriber.STATE_NO_CONFIRMED.equals(s.getState())
				|| Subscriber.STATE_UNCONFIRMED.equals(s.getState())) {
			
			throw new Exception("帐户资料未审核通过，不能申请退款");
		}
		Account account = accountDao.getAccountById(s.getId());
		if(Account.IS_REFUND_TRUE.equals(account.getIsRefund())){
			throw new Exception("退款流程处理中，请耐心等候");
		}
		
		BigDecimal actualAmount = new BigDecimal(account.getAmount());  
	
		//账户没钱
		if(actualAmount.compareTo(new BigDecimal(0))<=0){
			throw new Exception("账户无可用余额，无需退款");
		}
		
		//是否有进行中的订单
		Orders order = ordersService.queryCurrentOrder(subscriberId);
		if(order!=null){
			throw new Exception("当前有进行中的订单，不能申请退款");
		}
		
	
		
		
		
		//最近的一笔订单是否满足退款
		List<Orders> list=ordersService.queryEndOrdersBy15Days(subscriberId);
		if(list !=null && list.size()>0){
			throw new Exception(Constants.REFUND_LATELY_COMPLETION_DAY+"天内有成功完结订单，不能申请退款");
		}
	
	}
	
	
	/**
	 * 1.无进行中订单，2有钱  TODO
	 */
	/*private void validateRefundAccount(String accountId) throws RuntimeException{
			
	
			String cancelOrderFlag="0";
			String successOrderFlag="2";
			
			
		//	OrderMessage orderMessage = new OrderMessage();
			
			Orders order = new Orders();
			order.setMemberId(accountId);
			List<Orders> list=ordersService.queryOrdersByCon(order);
		
			Boolean hasOngoingOrder=false;
			if(list!=null && list.size()>0){
					if(cancelOrderFlag.equals(list.get(0).getState())  || successOrderFlag.equals(list.get(0).getState())){
						hasOngoingOrder=false;
					}else{
						hasOngoingOrder=true;
					}
					
					//账户支付进行中的订单
					if(hasOngoingOrder){
						throw new RuntimeException("有未完成的账户支付订单，不能申请退款");
					}else{
					
						//有15天内订单
						List<Orders> endOrderlist=ordersService.queryEndOrdersBy15Days(accountId);
						
						//未找到订单交易记录
						if(endOrderlist==null||endOrderlist.size()==0){
							//orderMessage.setResult(OrderMessage.CODE_SUCCESS_ALL, "成功");
							
						}else{//无15天内订单
							throw new RuntimeException("最近完成账户支付订单小于15天，不能申请退款");
						}
							
					}
					
			}else{
				//无未完成订单 可以退款
				//orderMessage.setResult(OrderMessage.CODE_SUCCESS_ALL, "成功");
				//return orderMessage;
			}
	}*/
	
	
	
	
	
	
	// get and set
	

	

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public AccountCardInstanceDao getAccountCardInstanceDao() {
		return accountCardInstanceDao;
	}


	public void setAccountCardInstanceDao(AccountCardInstanceDao accountCardInstanceDao) {
		this.accountCardInstanceDao = accountCardInstanceDao;
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


	public SubscriberConfirmDao getSubscriberConfirmDao() {
		return subscriberConfirmDao;
	}


	public void setSubscriberConfirmDao(SubscriberConfirmDao subscriberConfirmDao) {
		this.subscriberConfirmDao = subscriberConfirmDao;
	}


	public BusinessFlowDAO getBusinessFlowDAO() {
		return businessFlowDAO;
	}


	public void setBusinessFlowDAO(BusinessFlowDAO businessFlowDAO) {
		this.businessFlowDAO = businessFlowDAO;
	}


	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}


	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}


	@Override
	public AccountCardInstanceDao getAccountCardInstanceDaoInstance() {
		return accountCardInstanceDao;
	}



	@Override
	public String generateRefundDescription(List<AccountCardInstance> instanceList) {
		if(instanceList==null || instanceList.size()==0){
			return "";
		}
		 BigDecimal zhuan = new BigDecimal(0);
		 BigDecimal tui = new BigDecimal(0);
		for(AccountCardInstance instance:instanceList){
			if(AccountCardInstance.IS_SYSTEM_TRUE.equals(instance.getIsSystem())){
				zhuan=zhuan.add(new BigDecimal(instance.getRemainingAmount()));
			}else{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(instance.getCreateTime());
				calendar.add(Calendar.MONTH, Constants.WXPAY_REFUND_VALID_MONTH);
				
				if(calendar.after(new Date())){
					zhuan=zhuan.add(new BigDecimal(instance.getRemainingAmount()));
				}else{
					tui=tui.add(new BigDecimal(instance.getRemainingAmount()));
				}
			}
		}
		
		
		return "转账:"+zhuan.doubleValue()+" ,退款:"+tui.doubleValue();
	}



	@Override
	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDaoInstance() {
		return accountTradeRecordDetailDao;
	}



	
	

	

}
