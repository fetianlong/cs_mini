package com.dearho.cs.subscriber.service;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberCall;
import com.dearho.cs.sys.pojo.User;

public interface SubscriberCallService {
	
	void addSubscriberCall(SubscriberCall subscriberCall);

	void updateSubscriberCall(SubscriberCall subscriberCall);
	
	Page<SubscriberCall> querySubscriberCallByPage(Page<SubscriberCall> page,SubscriberCall subscriberCall);
	
	SubscriberCall getSubscriberCallById(String id);

	void callHandling(SubscriberCall subscriberCall, User operator);

		

	
	
	

}
