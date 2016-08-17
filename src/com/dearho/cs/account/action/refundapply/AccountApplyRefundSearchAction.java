package com.dearho.cs.account.action.refundapply;



/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file test.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import java.util.Date;

import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.BusinessFlow;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-18
 *
 */
public class AccountApplyRefundSearchAction extends AbstractAction {
	
	
	
	private static final long serialVersionUID = -4481608136969959869L;
	//private AccountService accountService;
	//private BusinessFlow businessFlow;
	private SubscriberConfirmService subscriberConfirmService;
	private SubscriberConfirm subscriberConfirm ;
	
	private Date  fromDate;
	private Date  toDate;
	
	private Page<SubscriberConfirm> page=new Page<SubscriberConfirm>();
	

	public AccountApplyRefundSearchAction() {
		super();
		

		subscriberConfirm=new SubscriberConfirm();
		subscriberConfirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_FALSE);
		BusinessFlow businessFlow = new BusinessFlow();
		businessFlow.setBusinessType(BusinessFlow.BUSINESS_TYPE_APPLY_REFUND);
		subscriberConfirm.setBusinessFlow(businessFlow);
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}

	@Override
	public String process() {
		
		//page=accountService.queryApplyRefundListByPage(page, businessFlow);
		page=subscriberConfirmService.querySubscriberConfirmByPage(page, subscriberConfirm);
		return SUCCESS;
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

	

	public SubscriberConfirmService getSubscriberConfirmService() {
		return subscriberConfirmService;
	}

	public void setSubscriberConfirmService(
			SubscriberConfirmService subscriberConfirmService) {
		this.subscriberConfirmService = subscriberConfirmService;
	}

	public SubscriberConfirm getSubscriberConfirm() {
		return subscriberConfirm;
	}

	public void setSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		this.subscriberConfirm = subscriberConfirm;
	}

	public Page<SubscriberConfirm> getPage() {
		return page;
	}

	public void setPage(Page<SubscriberConfirm> page) {
		this.page = page;
	}

	

	
	
	

	

	
	
}
