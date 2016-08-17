package com.dearho.cs.appinterface.account;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.account.service.PayService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

public class AppAccountAction extends AbstractAction {

	private static final long serialVersionUID = -4365773690582053808L;
	private AccountService accountService;
	private AccountTradeRecordService accountTradeRecordService;
	private PayService payService;
	private RechargeCardService rechargeCardService;
	private RefundService refundService;
	private WechatUserInfoService wechatUserInfoService;
	
	
	private Subscriber subscriber;
	private AccountTradeRecord accountTradeRecord;
	private Date fromDate;
	private Date toDate;
	private Page<AccountTradeRecord> page = new Page<AccountTradeRecord>();

	
	
	private AccountPaymentAccount paymentAccount;

	public AppAccountAction() {
		super();
		accountTradeRecord = new AccountTradeRecord();
		subscriber = new Subscriber();
		page.setCurrentPage(1);
		page.setCountField("a.id");
		
		paymentAccount= new AccountPaymentAccount();

	}

	/**
	 * 记录列表
	 * 
	 * @return
	 */
	public String searchTradeRecord() {
		if (!StringUtils.isEmpty(subscriber.getId())) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "会员id为空");
			return SUCCESS;
		}

		accountTradeRecord.setSubscriberId(subscriber.getId());
		page = accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, fromDate, toDate);

		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, page);
		return SUCCESS;
	}

	/**
	 * 记录详情
	 * 
	 * @return
	 */
	public String showTradingDetail() {
		if (accountTradeRecord.getId() == null) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "记录ID为空");
			return SUCCESS;
		}
		accountTradeRecord = accountTradeRecordService.getAccountTradeRecordById(accountTradeRecord.getId());
		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, accountTradeRecord);
		return SUCCESS;
	}

	
	
	/**
	 * 充值
	 */
	public String reharge() {

		if (!StringUtils.isEmpty(subscriber.getId())) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "会员id为空");
			return SUCCESS;
		}
		// 参数校验
		String customAmountStr = getRequest().getParameter("customAmount");
		double customAmount = 0;
		String metadata = getRequest().getParameter("isMetaData");
		String rechargeCardId = getRequest().getParameter("rechargeCardId");

		if ("Y".equals(metadata)) {
			RechargeCard rechargeCard = rechargeCardService.getRechargeCardById(rechargeCardId);
			if (rechargeCard == null) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "根据充值卡ID未找到充值卡");
				return SUCCESS;
			} else if (!rechargeCard.getChannel().contains(RechargeCard.CHANNEL_WECHAT)) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "充值卡使用渠道有误");
				return SUCCESS;
			}

		} else {
			try {
				customAmount = Double.parseDouble(customAmountStr);
				rechargeCardId = null;
			} catch (Exception e) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "充值金额有误请重新输入");
				return SUCCESS;
			}
			if (customAmount < 1 || customAmount > 10000) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "请输入1-10000以内的整数");
				return SUCCESS;
			}
		}

		String payType = getRequest().getParameter("payType");

		// 微信支付
		if (Account.PAY_TYPE_WECHAT.toString().equals(payType)) {
			// 先创建订单
			AccountTradeRecord accountTradeRecord = null;
			try {
				accountTradeRecord = payService.createRechargeRecord(subscriber.getId(), null, customAmount,
						Account.PAY_CHANNEL_WECHAT, Account.PAY_TYPE_WECHAT);
				
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, accountTradeRecord);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "创建充值订单失败");
				return SUCCESS;
			}

		} else {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "未知的支付渠道");
			return SUCCESS;
		}

	}
	
	
	
	
	public String refund() {
			if (!StringUtils.isEmpty(subscriber.getId())) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "会员id为空");
				return SUCCESS;
			}


		try {
			Boolean isNeedRefundAccount=refundService.checkIsNeedRefundAccount(subscriber.getId());
			String paymentAccountId=null;
			if(isNeedRefundAccount){
				if(paymentAccount.getType()==null){
					result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "请选择退款渠道");
					return SUCCESS;
				}
				if(Account.PAY_TYPE_ALIPAY.equals(paymentAccount.getType())){
					if(StringUtils.isEmpty(paymentAccount.getAccountNo())){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "支付宝账号不能为空");
						return SUCCESS;
					}
				}else if(Account.PAY_TYPE_WECHAT.equals(paymentAccount.getType())){
					Object obj =getSession().getAttribute("refund_wechatUserInfo");
					if(obj==null){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "页面过期请重新提交");
						return SUCCESS;
					}
					WechatUserInfo wechatUserInfo=wechatUserInfoService.getUserInfoBySubscriberId(subscriber.getId());
					if(StringUtils.isEmpty(wechatUserInfo.getOpenId())){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "未绑定微信账号不能选择微信渠道退款");
						return SUCCESS;
					}
					
					paymentAccount.setAccountNo(wechatUserInfo.getOpenId());
				}else if(Account.PAY_TYPE_UNIONPAY.equals(paymentAccount.getType())){
					if(StringUtils.isEmpty(paymentAccount.getAccountNo())){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "银联账号不能为空");
						return SUCCESS;
					}
					if(StringUtils.isEmpty(paymentAccount.getName())){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "持卡人姓名不能为空");
						return SUCCESS;
					}
				}
				accountService.addAccountPaymentAccount(paymentAccount);
				paymentAccountId=paymentAccount.getId();
			}
			
			
			refundService.checkApplyRefund(subscriber.getId());
			refundService.applyRefund(subscriber.getId(), paymentAccountId);
			result = Ajax.JSONResult(Constants.APP_RESULT_CODE_SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.APP_RESULT_CODE_FAIL, "系統异常");
		}
		
		
		

		return SUCCESS;
	}

	

	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountTradeRecordService getAccountTradeRecordService() {
		return accountTradeRecordService;
	}

	public void setAccountTradeRecordService(AccountTradeRecordService accountTradeRecordService) {
		this.accountTradeRecordService = accountTradeRecordService;
	}

	public PayService getPayService() {
		return payService;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}

	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}

	public RefundService getRefundService() {
		return refundService;
	}

	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public AccountTradeRecord getAccountTradeRecord() {
		return accountTradeRecord;
	}

	public void setAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		this.accountTradeRecord = accountTradeRecord;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Page<AccountTradeRecord> getPage() {
		return page;
	}

	public void setPage(Page<AccountTradeRecord> page) {
		this.page = page;
	}

	public AccountPaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(AccountPaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	
	
	
}
