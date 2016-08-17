package com.dearho.cs.subscriber.service;


import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.OrderMessage;
import com.dearho.cs.subscriber.pojo.Subscriber;

public interface SubscriberService {
	

	Subscriber addSubscriber(Subscriber subscriber);

	void updateSubscriber(Subscriber subscriber);
	
	
	
	Subscriber querySubscriberById(String id);
	
	Subscriber querySubscriberByPhoneNo(String id);
	
	Subscriber querySubscriberByUnionId(String unionId);
	
	Subscriber login(Subscriber subscriber);
	

	
	public Subscriber completeRegister(Subscriber subscriber,boolean isFromMobile);
	
	public Subscriber querySubscriberAllInfoById(String id);
	
	public OrderMessage validateSubscriberForOrder(String subscriberId) ;
	
//	public OrderMessage validateAccountForOrder(String subscriberId,String payStyle);
//	public OrderMessage validateAccountForOrder(String subscriberId,Double minAmount);
	
//	public OrderMessage validateAccountForOrder(String subscriberId,Double minAmount,String payStyle);
	
	public Page querySubscriberByPage(Page page,Subscriber subscriber);

	public Page querySubscriberRechargeLatestByPage(Page page, Subscriber subscriber) ;

	Page querySubscriberRefundLatestLatestByPage(Page page,	Subscriber subscriber);

	Page querySubscriberRechargeMostByPage(Page page, Subscriber subscriber);

	Page querySubscriberConsumerMostByPage(Page page, Subscriber subscriber);

	Page querySubscriberOrderFirstByPage(Page page, Subscriber subscriber);

	Page querySubscriberOrderLatestByPage(Page page, Subscriber subscriber);

	Page querySubscriberOrderMostByPage(Page page, Subscriber subscriber);

	Page querySubscriberOrderLongestByPage(Page page, Subscriber subscriber);

	Page querySubscriberLastSevenDayMember(Page page, Subscriber subscriber);

	
	
	
	
	List<Subscriber> querySubscriber(Subscriber subscriber);

	/**
	 * 查询id在ids里面的用户
	 * @param ids  用,隔开的id字符串
	 * @return
	 */
	List<Subscriber> searchSubscribersIn(String ids);

	/**
	 * 查询id不在ids里面的用户
	 * @param ids  用,隔开的id字符串
	 * @return
	 */
	List<Subscriber> searchSubscribersNotIn(String ids);
	
	public  String sendSMSCode(String type,String phoneNo,Integer channel) throws Exception;

	/**
	 * 查询姓名是否存在会员名称
	 * @param name
	 * @return
	 */
	Subscriber querySubscriberByName(String name);


}
