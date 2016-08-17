package com.dearho.cs.account.action.center;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 * 
 */
public class AccountApplyRefundAction extends AbstractAction {

	private static final long serialVersionUID = -4365773690582053808L;

	private AccountService accountService;
	private Account account;
	private RefundService refundService;
	private WechatUserInfoService wechatUserInfoService;
	private WechatUserInfo wechatUserInfo;
	private AccountPaymentAccount paymentAccount;
	

	private Double money;
	
	public AccountApplyRefundAction() {
		super();
		account = new Account();
		paymentAccount= new AccountPaymentAccount();
	}

	/**
	 * 处理退款业务逻辑
	 */
	@Override
	public String process() {
		if (getSession().getAttribute(Constants.SESSION_SUBSCRIBER) == null) {
			return LOGIN;
		}
		Subscriber s = (Subscriber) getSession().getAttribute(
				Constants.SESSION_SUBSCRIBER);
		try {
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "成功");
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,e.getMessage());
			return 	SUCCESS;
			
		}
		


		try {
			Boolean isNeedRefundAccount=refundService.checkIsNeedRefundAccount(s.getId());
			String paymentAccountId=null;
			if(isNeedRefundAccount){
				if(paymentAccount.getType()==null){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请选择退款渠道");
					return SUCCESS;
				}
				if(Account.PAY_TYPE_ALIPAY.equals(paymentAccount.getType())){
					if(StringUtils.isEmpty(paymentAccount.getAccountNo())){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "支付宝账号不能为空");
						return SUCCESS;
					}
				}else if(Account.PAY_TYPE_WECHAT.equals(paymentAccount.getType())){
					Object obj =getSession().getAttribute("refund_wechatUserInfo");
					if(obj==null){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "页面过期请重新提交");
						return SUCCESS;
					}
					wechatUserInfo=(WechatUserInfo)getSession().getAttribute("refund_wechatUserInfo");
					if(wechatUserInfo==null || StringUtils.isEmpty(wechatUserInfo.getOpenId())){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "未绑定微信账号不能选择微信渠道退款");
						return SUCCESS;
					}
					
					paymentAccount.setAccountNo(wechatUserInfo.getOpenId());
				}else if(Account.PAY_TYPE_UNIONPAY.equals(paymentAccount.getType())){
					if(StringUtils.isEmpty(paymentAccount.getAccountNo())){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "银联账号不能为空");
						return SUCCESS;
					}
					if(StringUtils.isEmpty(paymentAccount.getName())){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "持卡人姓名不能为空");
						return SUCCESS;
					}
				}
				accountService.addAccountPaymentAccount(paymentAccount);
				paymentAccountId=paymentAccount.getId();
			}
			
			
			refundService.checkApplyRefund(s.getId());
			refundService.applyRefund(s.getId(), paymentAccountId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, e.getMessage());
		}
		
		
		

		return SUCCESS;
	}

	

	/**
	 * 前台退款页面
	 * @return
	 */
	public String gotoApplyRefund() {
		if (getSession().getAttribute(Constants.SESSION_SUBSCRIBER) == null) {
			return LOGIN;
		}
		Subscriber s = (Subscriber) getSession().getAttribute(
				Constants.SESSION_SUBSCRIBER);

		account=accountService.getAccoutDetail(s.getId());
		
		
		boolean isNeedRefundAccount=refundService.checkIsNeedRefundAccount(s.getId());
		
		//是否需要填写退款账号
		getRequest().setAttribute("isNeedAccount", isNeedRefundAccount);
		if(isNeedRefundAccount){
			wechatUserInfo =wechatUserInfoService.getUserInfoBySubscriberId(s.getId());
			Object refund_wechatUserInfo=getSession().getAttribute("refund_wechatUserInfo");
			
			if(wechatUserInfo!=null && !StringUtils.isEmpty(wechatUserInfo.getOpenId())){
				getSession().setAttribute("refund_wechatUserInfo", wechatUserInfo);
			}else if(refund_wechatUserInfo!=null &&  !StringUtils.isEmpty(((WechatUserInfo)refund_wechatUserInfo).getOpenId())){
				getSession().setAttribute("refund_wechatUserInfo", refund_wechatUserInfo);
			}else{
				WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo","/refundPrepareServlet");
				return null;
			}
		}
		
		return SUCCESS;
	}

	


	/**
	 * 退款申请校验
	 * @return
	 */
	public String gotoApplyRefundCheck() {
		if (getSession().getAttribute(Constants.SESSION_SUBSCRIBER) == null) {
			return LOGIN;
		}
		Subscriber s = (Subscriber) getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		try {
			refundService.checkApplyRefund(s.getId());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "成功");
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,e.getMessage());
			
		}
		return SUCCESS;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
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

	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}

	public AccountPaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(AccountPaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}
	
	
	
}
