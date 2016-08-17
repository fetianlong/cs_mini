package com.dearho.cs.account.action.center;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */


import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public abstract class AccountBaseAction extends AbstractAction {

	
	private AccountService accountService;
	private SubscriberService subscriberService;
	private Subscriber subscriber;
	
	
	public Boolean isLogin(){
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
		}
		subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		return null;
	}
	public void isCheckLocked(String type){
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
		}
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		subscriberService.querySubscriberById(subscriber.getId());
		
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
	
	
	



	
	
}
