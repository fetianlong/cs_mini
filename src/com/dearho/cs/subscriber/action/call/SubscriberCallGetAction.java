package com.dearho.cs.subscriber.action.call;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberInfoSearchAction.java creation date:[2015-5-20 上午09:27:25] by liusong
 *http://www.dearho.com
 */


import java.util.Date;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.SubscriberCall;
import com.dearho.cs.subscriber.service.SubscriberCallService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-20
 *
 */
public class SubscriberCallGetAction extends AbstractAction  {

	

	private static final long serialVersionUID = 8199882721435918124L;
	private SubscriberCallService subscriberCallService;
	private SubscriberCall subscriberCall ;
	
	public SubscriberCallGetAction(){
		super();
		subscriberCall=new SubscriberCall();
	}
	
	@Override
	public String process() {
	
		
		if(!StringUtils.isEmpty(subscriberCall.getId())){
			subscriberCall=subscriberCallService.getSubscriberCallById(subscriberCall.getId());
			
		
		}
			
			
		
		
		return SUCCESS;
	}

	public String addInput(){
		return SUCCESS;
	}
	public SubscriberCallService getSubscriberCallService() {
		return subscriberCallService;
	}

	public void setSubscriberCallService(SubscriberCallService subscriberCallService) {
		this.subscriberCallService = subscriberCallService;
	}

	public SubscriberCall getSubscriberCall() {
		return subscriberCall;
	}

	public void setSubscriberCall(SubscriberCall subscriberCall) {
		this.subscriberCall = subscriberCall;
	}

	
	

	
}
