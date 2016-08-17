package com.dearho.cs.subscriber.service;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;

public interface SubscriberLoginRecordService {
	
	void addSubscriberLoginRecord(SubscriberLoginRecord subscriberLoginRecord);
	
	SubscriberLoginRecord getLastLoginRecordBySubscriberId(String subscriberId);
	
	Page<SubscriberLoginRecord> querySubscriberLoginRecordByPage(Page<SubscriberLoginRecord> page,SubscriberLoginRecord subscriberLoginRecord);
	


	
	
	
	

}
