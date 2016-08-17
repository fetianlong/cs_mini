package com.dearho.cs.subscriber.dao.impl;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.subscriber.dao.SubscriberPhoneChangeDao;
import com.dearho.cs.subscriber.pojo.SubscriberPhoneChangeRecord;

public class SubscriberPhoneChangeDaoImpl extends AbstractDaoSupport implements SubscriberPhoneChangeDao {

	@Override
	public void addRecord(SubscriberPhoneChangeRecord record) {
		addEntity(record);
	}

}
