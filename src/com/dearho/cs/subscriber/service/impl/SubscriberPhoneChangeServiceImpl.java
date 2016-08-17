package com.dearho.cs.subscriber.service.impl;

import com.dearho.cs.subscriber.dao.SubscriberPhoneChangeDao;
import com.dearho.cs.subscriber.pojo.SubscriberPhoneChangeRecord;
import com.dearho.cs.subscriber.service.SubscriberPhoneChangeService;

public class SubscriberPhoneChangeServiceImpl implements
		SubscriberPhoneChangeService {
	
	private SubscriberPhoneChangeDao subscriberPhoneChangeDao;

	@Override
	public void addRecord(
			SubscriberPhoneChangeRecord record) {
		subscriberPhoneChangeDao.addRecord(record);;
	}

	public SubscriberPhoneChangeDao getSubscriberPhoneChangeDao() {
		return subscriberPhoneChangeDao;
	}
	public void setSubscriberPhoneChangeDao(
			SubscriberPhoneChangeDao subscriberPhoneChangeDao) {
		this.subscriberPhoneChangeDao = subscriberPhoneChangeDao;
	}
}
