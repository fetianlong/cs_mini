package com.dearho.cs.subscriber.action.call;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberInfoSearchAction.java creation date:[2015-5-20 上午09:27:25] by liusong
 *http://www.dearho.com
 */


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberCall;
import com.dearho.cs.subscriber.service.SubscriberCallService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-20
 *
 */
public class SubscriberCallSearchAction extends AbstractAction {


	private static final long serialVersionUID = -3614544661758149762L;
	
	private SubscriberCallService subscriberCallService;
	private SubscriberCall subscriberCall ;
	private Page<SubscriberCall> page=new Page<SubscriberCall>();
	
	public SubscriberCallSearchAction (){
		super();
		subscriberCall=new SubscriberCall();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	@Override
	public String process() {
		page=subscriberCallService.querySubscriberCallByPage(page, subscriberCall);
		
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
	public Page<SubscriberCall> getPage() {
		return page;
	}
	public void setPage(Page<SubscriberCall> page) {
		this.page = page;
	}
	

	
}
