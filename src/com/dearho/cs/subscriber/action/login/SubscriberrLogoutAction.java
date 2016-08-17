package com.dearho.cs.subscriber.action.login;



/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberLoginAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */
import javax.servlet.http.HttpSession;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.opensymphony.webwork.ServletActionContext;

/**
 * @Author liusong
 * @Description 会员注销
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberrLogoutAction extends AbstractAction {


	private static final long serialVersionUID = 9052312888517878632L;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	private String retMsg;
	
	
	public SubscriberrLogoutAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {
		
			HttpSession session = ServletActionContext.getRequest().getSession();
			Subscriber m=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
			if(m!=null){
				session.removeAttribute(Constants.SESSION_SUBSCRIBER);
				CookieTool.removeCurrentUser(getRequest(),getResponse());
			}
		return SUCCESS;
	}
	
	
	
	

	
	
	public String gotoLogin(){
		return 	SUCCESS;
	}
	


	public SubscriberService getSubscriberService() {
		return subscriberService;
	}


	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}


	public Subscriber getSubscriber() {
		return subscriber;
	}


	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}


	public String getRetMsg() {
		return retMsg;
	}


	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
	
	
	
	
	
	

}
