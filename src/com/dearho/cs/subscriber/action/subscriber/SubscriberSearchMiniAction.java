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
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 会员信息审核列表
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberSearchMiniAction extends AbstractAction {
	
	
	
	private static final long serialVersionUID = -4481608136969959869L;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	private Date  fromDate;
	private Date  toDate;
	
	private Page<Subscriber> page=new Page<Subscriber>();
	
	
	
	
	public SubscriberSearchMiniAction() {
		super();
		subscriber=new Subscriber();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}

	@Override
	public String process() {

		String searchCondition=getRequest().getParameter("searchCondition");
		if(StringUtils.isEmpty(searchCondition)){
			//page=subscriberService.querySubscriberByPage(page, subscriber);
			return "subscriber";
		}
		
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

	
	
	
	

	

	
	
}
