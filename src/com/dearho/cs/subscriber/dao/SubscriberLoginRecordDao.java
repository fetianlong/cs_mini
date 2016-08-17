package com.dearho.cs.subscriber.dao;



import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;

public interface SubscriberLoginRecordDao {
	
	void addSubscriberLoginRecord(SubscriberLoginRecord subscriberLoginRecord);
	
	SubscriberLoginRecord getLastLoginRecordBySubscriberId(String subscriberId);
	
	Page<SubscriberLoginRecord> querySubscriberLoginRecordByPage(Page<SubscriberLoginRecord> page,String hql);
	
	SubscriberLoginRecord getSubscriberLoginRecordById(String id);
	

	
	
	

}
