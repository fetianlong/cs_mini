package com.dearho.cs.account.action.commission;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-18
 *
 */
public class CommissionCutPaymentSaveAction extends AbstractAction {

	private static final long serialVersionUID = 3504119720102723802L;
	private RechargeCardService rechargeCardService;
	private SubscriberService subscriberService;
	private AccountService accountService;
	private RechargeCard rechargeCard ;
	private Subscriber subscriber;
	private String msg;
	private String remark;
	public CommissionCutPaymentSaveAction(){
		super();
		rechargeCard=new RechargeCard();
		subscriber=new Subscriber();
	}
	@Override
	public String process() {
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);

		if(StringUtils.isEmpty(subscriber.getId())){
			msg="请选择会员!";
			return SUCCESS;
		}
		if(StringUtils.isEmpty(remark)){
			msg="请填写扣款备注!";
			return SUCCESS;
		}
		Account account=accountService.getAccoutDetail(subscriber.getId());
		if (Account.IS_REFUND_TRUE.equals(account.getIsRefund())) {
			msg="退款流程处理中，不能扣款!";
			return SUCCESS;
		}
		
		Integer customAmount=null;	
		try{
			customAmount=Integer.parseInt(getRequest().getParameter("customAmount"));
		}catch(Exception e){
			msg="充值金额有误请重新输入";
			return SUCCESS;
		}
		if(customAmount<=0 || customAmount>5000){
			msg="请输入0-5000的整数";
			return SUCCESS;
		}
		
		try {
			accountService.commissionCutPayment(customAmount,subscriber.getId(),operator,remark);
			msg="扣款成功";
		} catch (Exception e) {
			msg="扣款失败"+e.getMessage();
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}
	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}
	public RechargeCard getRechargeCard() {
		return rechargeCard;
	}
	public void setRechargeCard(RechargeCard rechargeCard) {
		this.rechargeCard = rechargeCard;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
