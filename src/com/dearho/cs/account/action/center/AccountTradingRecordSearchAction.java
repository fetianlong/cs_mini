/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.action.center;

import java.util.Calendar;
import java.util.Date;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountTradingRecordSearchAction extends AbstractAction {

	
	
	private static final long serialVersionUID = -4365773690582053808L;
	private AccountService accountService;
	private AccountTradeRecordService accountTradeRecordService;
	private Account account;
	private AccountTradeRecord accountTradeRecord;
	private Date  fromDate;
	private Date  toDate;
	private Page<AccountTradeRecord> page=new Page<AccountTradeRecord>();
	public AccountTradingRecordSearchAction(){
		super();
		accountTradeRecord=new AccountTradeRecord();
		account= new Account();
		page.setCurrentPage(1);
		page.setCountField("a.id");
		

	}

	@Override
	public String process() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
	
		
		Date today=new Date();
		Date lateMonth=null;
		
		Calendar calendar= Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		lateMonth=calendar.getTime();
        

		if(fromDate==null){
			fromDate=lateMonth;
		}
		
		if(toDate==null){
			toDate=today;
		}
		accountTradeRecord.setSubscriberId(subscriber.getId());
		page=	accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, fromDate, toDate);
		account=accountService.getAccoutDetail(subscriber.getId());
		

		return SUCCESS;
	}
	

	public String mobileSearchTradeRecord() {
		
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
	
		
		accountTradeRecord.setSubscriberId(subscriber.getId());
		page=	accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, fromDate, toDate);
		account=accountService.getAccoutDetail(subscriber.getId());
		

		return SUCCESS;
	}
	
	public String showTradingDetailMini(){
		if(accountTradeRecord.getId()==null){
		//	return "fail";
			return SUCCESS;
		}
		accountTradeRecord=accountTradeRecordService.getAccountTradeRecordById(accountTradeRecord.getId());
		
		return 	SUCCESS;
	}
	
	public String showTradingListMini(){
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
	
		
		accountTradeRecord.setSubscriberId(subscriber.getId());
		page=	accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, fromDate, toDate);
		account=accountService.getAccoutDetail(subscriber.getId());
			
		return 	SUCCESS;
	}
	
	/*public String process() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
	
		
		Date today=null;
		Date lateMonth=null;
		Date lastThreeMonth=null;
		Date lastSixMonth=null;
		
		Calendar calendar= Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		lateMonth=calendar.getTime();
        
        calendar= Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-3);
		lastThreeMonth=calendar.getTime();
        
        calendar= Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-6);
		lastSixMonth=calendar.getTime();
         
		today=new Date();
		
		getRequest().setAttribute("lateMonth", lateMonth);
		getRequest().setAttribute("lastThreeMonth", lastThreeMonth);
		getRequest().setAttribute("lastSixMonth", lastSixMonth);
		getRequest().setAttribute("today", today);
		
		if(fromDate==null){
			fromDate=lateMonth;
		}
		
		if(toDate==null){
			toDate=today;
		}
		accountTradeRecord.setSubscriberId(subscriber.getId());
		page=	accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, fromDate, toDate);
		account=accountService.getAccoutDetail(subscriber.getId());
		getRequest().setAttribute("pageIndex", 1+(page.getPageSize())*(page.getCurrentPage()-1));
		if(StringUtils.isEmpty(getRequest().getParameter("dateType"))){
			getRequest().setAttribute("dateType", "oneM");
		}else{
			getRequest().setAttribute("dateType", getRequest().getParameter("dateType"));
		}
		getRequest().setAttribute("test","5");
		
		return SUCCESS;
	}
	*/


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
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


	public void setAccountTradeRecordService(
			AccountTradeRecordService accountTradeRecordService) {
		this.accountTradeRecordService = accountTradeRecordService;
	}


	public AccountService getAccountService() {
		return accountService;
	}

	public AccountTradeRecordService getAccountTradeRecordService() {
		return accountTradeRecordService;
	}

	public AccountTradeRecord getAccountTradeRecord() {
		return accountTradeRecord;
	}

	public void setAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		this.accountTradeRecord = accountTradeRecord;
	}

	public Page<AccountTradeRecord> getPage() {
		return page;
	}

	public void setPage(Page<AccountTradeRecord> page) {
		this.page = page;
	}

	
	
	
	
}
