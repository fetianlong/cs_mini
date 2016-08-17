package com.dearho.cs.account.action.account;

import java.util.List;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountTradingDetailSearchAction extends AbstractAction {

	
	
	private static final long serialVersionUID = -4365773690582053808L;
	
	private AccountTradeRecordService accountTradeRecordService;
	private SubscriberService subscriberService;

	private AccountTradeRecordDetail accountTradeRecordDetail;
	private AccountTradeRecord accountTradeRecord;
	private Subscriber subscriber;

	
	public AccountTradingDetailSearchAction(){
		super();
		accountTradeRecord=new AccountTradeRecord();
		accountTradeRecordDetail=new AccountTradeRecordDetail();
	}

	@Override
	public String process() {
		
		
		accountTradeRecord=accountTradeRecordService.getAccountTradeRecordById(accountTradeRecord.getId());
		if(accountTradeRecord!=null){
			subscriber=subscriberService.querySubscriberById(accountTradeRecord.getSubscriberId());
			List<AccountTradeRecordDetail>  list=accountTradeRecordService.getAccountTradeRecordDetailByBiz(accountTradeRecord.getId());
			if(list!=null &&list.size()>0){
				accountTradeRecordDetail=list.get(0);
			}
		}
	

		return SUCCESS;
	}

	public AccountTradeRecordService getAccountTradeRecordService() {
		return accountTradeRecordService;
	}

	public void setAccountTradeRecordService(AccountTradeRecordService accountTradeRecordService) {
		this.accountTradeRecordService = accountTradeRecordService;
	}

	public AccountTradeRecordDetail getAccountTradeRecordDetail() {
		return accountTradeRecordDetail;
	}

	public void setAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail) {
		this.accountTradeRecordDetail = accountTradeRecordDetail;
	}

	

	public AccountTradeRecord getAccountTradeRecord() {
		return accountTradeRecord;
	}

	public void setAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		this.accountTradeRecord = accountTradeRecord;
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
	

	
	
	
	
}
