package com.dearho.cs.account.action.refundapply;



import java.util.List;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberCheckGetAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-18
 *
 */
public class AccountApplyRefundGetAction extends AbstractAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1211364735594888007L;
	
	private SubscriberConfirmService subscriberConfirmService;
	private SubscriberConfirm subscriberConfirm ;
	private UserService userService;
	private AccountService accountService;
	List< AccountCardInstance> instanceList;
	private RefundService refundService;
	
	
	public AccountApplyRefundGetAction() {
		super();
		subscriberConfirm=new SubscriberConfirm();
	}


	
	@Override
	public String process() {
		
		subscriberConfirm=subscriberConfirmService.querySubscriberConfirmById(subscriberConfirm.getId());
		
		if(subscriberConfirm.getBusinessFlow()!=null && StringUtils.isNotEmpty(subscriberConfirm.getBusinessFlow().getReviewerId())){
			User user=userService.searchUserByID(subscriberConfirm.getBusinessFlow().getReviewerId());
			if(user!=null){
				subscriberConfirm.getBusinessFlow().setReviewerId(user.getName());
			}
		}
		if(StringUtils.isEmpty(subscriberConfirm.getAttribute_4())&&StringUtils.isNotEmpty(subscriberConfirm.getAttribute_1()) && StringUtils.isNotEmpty(subscriberConfirm.getAttribute_3())){
			instanceList=accountService.queryCardInstanceByIds(subscriberConfirm.getAttribute_3());
			String str=refundService.generateRefundDescription(instanceList);
			subscriberConfirm.setAttribute_4(str);
			subscriberConfirmService.update(subscriberConfirm);
		}
		
		
		return SUCCESS;
	}



	public SubscriberConfirm getSubscriberConfirm() {
		return subscriberConfirm;
	}



	public void setSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		this.subscriberConfirm = subscriberConfirm;
	}



	public void setSubscriberConfirmService(
			SubscriberConfirmService subscriberConfirmService) {
		this.subscriberConfirmService = subscriberConfirmService;
	}



	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public List<AccountCardInstance> getInstanceList() {
		return instanceList;
	}



	public void setInstanceList(List<AccountCardInstance> instanceList) {
		this.instanceList = instanceList;
	}



	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}



	public RefundService getRefundService() {
		return refundService;
	}



	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	
	
	


	
	
}
