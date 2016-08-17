package com.dearho.cs.subscriber.dao;



import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;

public interface SubscriberDao {
	/**
	 * 添加会员
	 * @param car 
	 */
	void addSubscriber(Subscriber subscriber);
	/**
	 * 更新会员
	 * @param car
	 */
	void updateSubscriber(Subscriber subscriber);
	/**
	 * 查询分页信息
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<Subscriber> querySubscriberByPage(Page<Subscriber> page,String hql);
	
	/**
	 * 根据ID查询会员信息
	 * @param id
	 * @return
	 */
	Subscriber querySubscriberById(String id);
	
	/**
	 * 根据手机号查询会员信息
	 * @param id
	 * @return
	 */
	Subscriber querySubscriberByPhone(String id);
	
	
/*	public Page<AccountCurrentAccount> querySubscriberRechargeLatestByPage(Page<AccountCurrentAccount> page,String hql) ;
	
	*//** 最多充值记录**//*
	public Page<AccountCurrentAccount> querySubscriberRechargeMostByPage(Page<AccountCurrentAccount> page,String hql);
*/	
	/**
	 * 首次用车
	 * @param page
	 * @param string
	 * @return
	 */
	Page querySubscriberOrderFirstByPage(Page page, String string);
	
	/**
	 * 订单最多
	 * @param page
	 * @param string
	 * @return
	 */
	Page querySubscriberOrderMostByPage(Page page, String string);
	
	List<Subscriber> getSubscriberByHql(String string);
	
	
	public Subscriber querySubscriberByUnionid(String unionId);
	
	/**
	 * 根据姓名查询用户
	 * @param name
	 * @return 
	 */
	Subscriber querySubscriberByName(String name);

}
