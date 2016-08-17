package com.dearho.cs.subscriber.action.call;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberInfoSearchAction.java creation date:[2015-5-20 上午09:27:25] by liusong
 *http://www.dearho.com
 */


import java.util.List;

import net.sf.json.JSONObject;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-7-02
 *
 */
public class SubscriberCallPhoneSearchAction extends AbstractAction  {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8152476790376696353L;
	
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	private Subscriber subscriber;

	public SubscriberCallPhoneSearchAction(){
		super();
		subscriber = new Subscriber();
	}
	
	@Override
	public String process() {
		if(StringUtils.isEmpty(subscriber.getPhoneNo())){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"会员手机号不能为空!");
			return ERROR;
		}
		subscriber=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
		
		if(subscriber==null){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"无此会员");
			return ERROR;
		}
		
		JSONObject json=new JSONObject();
		json.element("result", Constants.RESULT_CODE_SUCCESS).element("subscriberName", subscriber.getName()).element("subscriberId", subscriber.getId());
		Orders order= new Orders();
		order.setMemberId(subscriber.getId());
		List<Orders> list=ordersService.queryOrdersByCon(order);
		if(list==null || list.size()==0){
			json.element("orderId", "").element("orderNo", "");
		}else{
			json.element("orderId", list.get(0).getId()).element("orderNo", list.get(0).getOrdersNo());
		}
		result=json.toString();
		
		return SUCCESS;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	
	
	

	
}
