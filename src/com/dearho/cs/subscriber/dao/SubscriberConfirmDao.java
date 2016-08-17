package com.dearho.cs.subscriber.dao;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubscriberConfirmDao.java creation date:[2015-5-19 下午04:56:14] by liusong
 *http://www.dearho.com
 */

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.sys.pojo.BusinessFlow;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-19
 *
 */
public interface SubscriberConfirmDao {

	public void addSubscriberConfirm(SubscriberConfirm subscriberConfirm);
	
	public void updateSubscriberConfirm(SubscriberConfirm subscriberConfirm);
	
	public Page<SubscriberConfirm> queryPageSubscriberConfirm(Page<SubscriberConfirm> page,String hql);
	
	public List<SubscriberConfirm> querySubscriberConfirmList(String hql);
	
	public SubscriberConfirm getSubscriberConfirm(String id);
	

}
