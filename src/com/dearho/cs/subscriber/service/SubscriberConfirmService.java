package com.dearho.cs.subscriber.service;


import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.sys.pojo.User;

public interface SubscriberConfirmService {
	
	/**
	 * 
	 * @Title: querySubscriberConfirmByPage
	 * @Description:查询审核信息列表
	 * @param page
	 * @param Subscriber
	 * @return
	 * @throws
	 */
	Page<SubscriberConfirm> querySubscriberConfirmByPage(Page<SubscriberConfirm> page,SubscriberConfirm subscriberConfirm);
	
	/**
	 * 
	 * @Title: querySubscriberConfirmById
	 * @Description:通过ID查询审核信息
	 * @param id
	 * @return
	 * @throws
	 */
	SubscriberConfirm querySubscriberConfirmById(String id);
	
	/**
	 * 
	 * @Title: querySubscriberConfirmBySubscriberId
	 * @Description:通过会员信息查询审核信息
	 * @param id
	 * @return
	 * @throws
	 */
	List<SubscriberConfirm> querySubscriberConfirmBySubscriberId(String id);
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: check
	 * @Description:
	 * @param SubscriberConfirm
	 * @param operator
	 * @param drivingLicence
	 * @return
	 * @throws
	 */
	void confirm(SubscriberConfirm subscriberConfirm,User operator) throws Exception;
	
	
	void update(SubscriberConfirm subscriberConfirm) ;

}
