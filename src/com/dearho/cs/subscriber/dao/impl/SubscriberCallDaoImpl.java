package com.dearho.cs.subscriber.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberCallDao;
import com.dearho.cs.subscriber.pojo.SubscriberCall;

public class SubscriberCallDaoImpl  extends AbstractDaoSupport implements SubscriberCallDao {

	@Override
	public void addSubscriberCall(SubscriberCall subscriberCall) {
		addEntity(subscriberCall);

	}

	@Override
	public SubscriberCall getSubscriberCallById(String id) {
		SubscriberCall subscriberCall= get(SubscriberCall.class, id);
		
		return subscriberCall;
	}

	@Override
	public Page<SubscriberCall> querySubscriberCallByPage(
			Page<SubscriberCall> page, String hql) {
		Page<SubscriberCall> resultPage=pageCache(SubscriberCall.class, page, hql);
		
		resultPage.setResults(idToObj(SubscriberCall.class, resultPage.getmResults()));
		
	
		
		return resultPage;
	}

	@Override
	public List<SubscriberCall> querySubscriberCallList(String hql) {
		return getList(SubscriberCall.class, queryFList(hql));
	}

	@Override
	public void updateSubscriberCall(SubscriberCall subscriberCall) {
		updateEntity(subscriberCall);

	}

}
