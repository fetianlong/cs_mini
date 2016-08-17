package com.dearho.cs.subscriber.action.subscriber;


/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file test.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import java.util.Date;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 会员信息审核列表
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberSearchAction extends AbstractAction {
	
	
	
	private static final long serialVersionUID = -4481608136969959869L;
	
	public static final String SEARCH_CONDITION_SUBSCRIBER="subscriber";
	public static final String SEARCH_CONDITION_RECHARGE_LATEST="rechargeLatest";
	
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	private Date  fromDate;
	private Date  toDate;
	
	private Page page=new Page();
	
	private String state;
	
	
	public SubscriberSearchAction() {
		super();
		subscriber=new Subscriber();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}

	@Override
	public String process() {
		if("page".equals(state)){
			page=subscriberService.querySubscriberByPage(page, subscriber);
			return "search";
		}
		String searchCondition=getRequest().getParameter("searchCondition");
		String sevenDayCondition=getRequest().getParameter("sevenDayCondition");
		
		if(StringUtils.isEmpty(searchCondition)){
			getRequest().setAttribute("searchCondition", sevenDayCondition);
			return SUCCESS;
		}else if(SEARCH_CONDITION_SUBSCRIBER.equals(searchCondition)){
			//默认
			page=subscriberService.querySubscriberByPage(page, subscriber);
			return SEARCH_CONDITION_SUBSCRIBER;
		}else if("rechargeLatest".equals(searchCondition)){
			//最新充值
			page=subscriberService.querySubscriberRechargeLatestByPage(page, subscriber);
			return "rechargeLatest";
		}else if("refundLatest".equals(searchCondition)){
			//最新退款
			page=subscriberService.querySubscriberRefundLatestLatestByPage(page, subscriber);
			return "refundLatest";
		}else if("rechargeMost".equals(searchCondition)){
			//充值最多
			page=subscriberService.querySubscriberRechargeMostByPage(page, subscriber);
			return "rechargeMost";
		}else if("consumerMost".equals(searchCondition)){
			//消费最多
			page=subscriberService.querySubscriberConsumerMostByPage(page, subscriber);
			return "consumerMost";
		}else if("orderFirst".equals(searchCondition)){
			//首次用车
			page=subscriberService.querySubscriberOrderFirstByPage(page, subscriber);
			return "orderFirst";
		}else if("orderLatest".equals(searchCondition)){
			//最近用车
			page=subscriberService.querySubscriberOrderLatestByPage(page, subscriber);
			return "orderLatest";
		}else if("orderMost".equals(searchCondition)){
			//订单最多
			page=subscriberService.querySubscriberOrderMostByPage(page, subscriber);
			return "orderMost";
		}else if("orderLongest".equals(searchCondition)){
			//用时最长
			page=subscriberService.querySubscriberOrderLongestByPage(page, subscriber);
			return "orderLongest";
		}else if("lastSevenDayMember".equals(searchCondition)){
			//最近七天新增会员
			page=subscriberService.querySubscriberLastSevenDayMember(page, subscriber);
			return SEARCH_CONDITION_SUBSCRIBER;
		}
		
		return SUCCESS;
	}

	
	public String searchWindowSubscriberList(){
		//默认
		page=subscriberService.querySubscriberByPage(page, subscriber);	
		return SUCCESS;
	}
	
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Page<Subscriber> getPage() {
		return page;
	}

	public void setPage(Page<Subscriber> page) {
		this.page = page;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	

	

	
	
}
