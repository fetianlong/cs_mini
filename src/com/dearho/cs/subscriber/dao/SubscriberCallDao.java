package com.dearho.cs.subscriber.dao;



import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberCall;

public interface SubscriberCallDao {
	/**
	 * 添加RFID
	 * @param car 
	 */
	void addSubscriberCall(SubscriberCall subscriberCall);
	/**
	 * 更新RFID
	 * @param car
	 */
	void updateSubscriberCall(SubscriberCall subscriberCall);
	/**
	 * 查询分页信息
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<SubscriberCall> querySubscriberCallByPage(Page<SubscriberCall> page,String hql);
	
	/**
	 * 根据ID查询信息
	 * @param id
	 * @return
	 */
	SubscriberCall getSubscriberCallById(String id);
	
	List<SubscriberCall> querySubscriberCallList(String hql);
	
	
	

}
