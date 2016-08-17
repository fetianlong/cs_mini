package com.dearho.cs.subscriber.dao.impl;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberCheckDaoImpl.java creation date:[2015-5-19 下午04:59:35] by liusong
 *http://www.dearho.com
 */


import java.util.List;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberConfirmDao;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.sys.pojo.BusinessFlow;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-19
 *
 */
public class SubscriberConfirmDaoImpl extends AbstractDaoSupport implements  SubscriberConfirmDao  {

	
	@Override
	public void addSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		addEntity(subscriberConfirm);
		
	}
	@Override
	public List<SubscriberConfirm> querySubscriberConfirmList(String hql) {
		return getList(SubscriberConfirm.class, queryFList(hql));
	}
	@Override
	public Page<SubscriberConfirm> queryPageSubscriberConfirm(
			Page<SubscriberConfirm> page, String hql) {
		
		Page<SubscriberConfirm> resultPage=page(page, hql);
		resultPage.setResults(idToObj(SubscriberConfirm.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				SubscriberConfirm confirm=(SubscriberConfirm) resultPage.getResults().get(i);
				Subscriber subscriber=get(Subscriber.class,confirm.getSubscriberId());
				Account account=get(Account.class,confirm.getSubscriberId());
				subscriber.setAccount(account);
				confirm.setSubscriber(subscriber);
				BusinessFlow businessFlow=getBusinessFlowByConfirmId(confirm.getId());
				confirm.setBusinessFlow(businessFlow);
			}
		}
		return resultPage;
	}
	@Override
	public void updateSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		updateEntity(subscriberConfirm);
		
	}
	@Override
	public SubscriberConfirm getSubscriberConfirm(String id) {
		return get(SubscriberConfirm.class, id);
	}


	private BusinessFlow getBusinessFlowByBizId(String bizId) {
		return get(BusinessFlow.class, (String)getQuery("select id from BusinessFlow where businessId = '"+bizId+"'").uniqueResult());
	}
	private BusinessFlow getBusinessFlowByConfirmId(String confirmId) {
		return get(BusinessFlow.class, (String)getQuery("select id from BusinessFlow where confirmId = '"+confirmId+"'").uniqueResult());
	}


	


}
