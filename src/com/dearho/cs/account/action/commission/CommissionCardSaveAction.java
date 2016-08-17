package com.dearho.cs.account.action.commission;

import java.util.regex.Pattern;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-18
 *
 */
public class CommissionCardSaveAction extends AbstractAction {

	private static final long serialVersionUID = 3504119720102723802L;
	private RechargeCardService rechargeCardService;
	private SubscriberService subscriberService;
	private AccountService accountService;
	private RechargeCard rechargeCard ;
	private Subscriber subscriber;
	private String msg;
	private String remark;
	private String payType;
	public CommissionCardSaveAction(){
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
			msg="请填写代充备注!";
			return SUCCESS;
		}
		
		
		Account account=accountService.getAccoutDetail(subscriber.getId());
		if (Account.IS_REFUND_TRUE.equals(account.getIsRefund())) {
			msg="退款流程处理中，不能充值!";
			return SUCCESS;
		}
		
		String rechargeCardId=null;
		Double customAmount=null;
		
		String metadata=getRequest().getParameter("isMetaData");
		if("Y".equals(metadata)){
			if(StringUtils.isEmpty(rechargeCard.getId())){
				msg="请选择充值卡!";
				return SUCCESS;
			}
			RechargeCard rechargeCard2=rechargeCardService.getRechargeCardById(rechargeCard.getId());
			rechargeCardId=rechargeCard.getId();
			if(rechargeCard2==null){
				msg="充值卡不存在!";
				return SUCCESS;
			}
		}else{
			
			 Pattern p = Pattern.compile("^[1-9]\\d*$");
			 if(!p.matcher(getRequest().getParameter("customAmount")).matches()){
				 	msg="请输入10-10000以内的整数";
					return SUCCESS;
			 }     
			try{
				customAmount=Double.parseDouble(getRequest().getParameter("customAmount"));
				rechargeCardId=null;
			}catch(Exception e){
				msg="充值金额有误请重新输入";
				return SUCCESS;
			}
			
			  
			     
			        
			if(customAmount<10 || customAmount>10000){
				msg="请输入10-10000以内的整数";
				return SUCCESS;
			}
		}
		
		
		
		try {
			accountService.commissionRechargeCard(rechargeCardId,customAmount,subscriber.getId(),operator,payType,remark);
			msg="充值成功";
		} catch (Exception e) {
			msg="充值失败"+e.getMessage();
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	
}
