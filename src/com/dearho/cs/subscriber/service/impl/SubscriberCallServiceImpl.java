package com.dearho.cs.subscriber.service.impl;

import java.util.Date;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberCallDao;
import com.dearho.cs.subscriber.pojo.SubscriberCall;
import com.dearho.cs.subscriber.service.SubscriberCallService;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.StringHelper;

public class SubscriberCallServiceImpl implements SubscriberCallService {

	private SubscriberCallDao subscriberCallDao;
	
	@Override
	public void addSubscriberCall(SubscriberCall subscriberCall) {
		subscriberCallDao.addSubscriberCall(subscriberCall);
		
	}

	@Override
	public SubscriberCall getSubscriberCallById(String id) {
		
		return subscriberCallDao.getSubscriberCallById(id);
	}

	@Override
	public Page<SubscriberCall> querySubscriberCallByPage(
			Page<SubscriberCall> page, SubscriberCall subscriberCall) {
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from SubscriberCall a where 1=1");
		
		if(subscriberCall!=null){
			if(!StringUtils.isEmpty(subscriberCall.getCallPhoneNo())){
				hql.append(" and callPhoneNo like '%"+subscriberCall.getCallPhoneNo()).append("%' ");
			}
			if(!StringUtils.isEmpty(subscriberCall.getCallName())){
				hql.append(" and callName like '%"+subscriberCall.getCallName()+"%' ");
			}
			if(!StringUtils.isEmpty(subscriberCall.getTitle())){
				hql.append(" and title like '%"+subscriberCall.getTitle()+"%' ");
			}
			if(subscriberCall.getState()!=null){
				hql.append(" and state = "+subscriberCall.getState());
			}
			if(subscriberCall.getType()!=null){
				hql.append(" and type = "+subscriberCall.getType());
			}
			if(subscriberCall.getLevel()!=null){
				hql.append(" and level = "+subscriberCall.getLevel());
			}
		}
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? "order by state asc,level desc,ts asc" : page.getOrderByString());
		page=subscriberCallDao.querySubscriberCallByPage(page, hql.toString());
		return page;
		
	}

	@Override
	public void updateSubscriberCall(SubscriberCall subscriberCall) {
		subscriberCallDao.updateSubscriberCall(subscriberCall);
		
	}

	public void setSubscriberCallDao(SubscriberCallDao subscriberCallDao) {
		this.subscriberCallDao = subscriberCallDao;
	}



	@Override
	public void callHandling(SubscriberCall subscriberCall, User operator) {
		SubscriberCall call=subscriberCallDao.getSubscriberCallById(subscriberCall.getId());
		call.setOperatorId(operator.getId());
		call.setOperatorName(operator.getName());
		call.setResultDesc(subscriberCall.getResultDesc());
		call.setState(subscriberCall.getState());
		call.setResultTime(new Date());
		subscriberCallDao.updateSubscriberCall(call);
		
	}
	
	

}
