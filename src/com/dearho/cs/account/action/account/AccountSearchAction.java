/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.action.account;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.BusinessFlow;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountSearchAction extends AbstractAction {

	
	
	private static final long serialVersionUID = -4365773690582053808L;
	private AccountService accountService;
	private AccountTradeRecordService accountTradeRecordService;
	
	private Account account;
	private Subscriber subscriber;
	private Page<Subscriber> page=new Page<Subscriber>();
	
	private String type;

	
	public AccountSearchAction(){
		super();
		account= new Account();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}

	@Override
	public String process() {
		if("near7days".equals(type)){
			page = accountService.queryLastOperate(page, 7,BusinessFlow.BUSINESS_TYPE_APPLY_REFUND);
		}
		else{
			page=accountService.queryAccountByPage(page, subscriber);
		}
		
		return 	SUCCESS;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setAccountTradeRecordService(
			AccountTradeRecordService accountTradeRecordService) {
		this.accountTradeRecordService = accountTradeRecordService;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Page<Subscriber> getPage() {
		return page;
	}

	public void setPage(Page<Subscriber> page) {
		this.page = page;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public AccountTradeRecordService getAccountTradeRecordService() {
		return accountTradeRecordService;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	
	
}
