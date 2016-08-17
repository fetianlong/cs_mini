package com.dearho.cs.account.action.account;

import java.util.ArrayList;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.account.vo.AccountTradeRecordVO;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.ExcelUtil;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountTradingSearchAction extends AbstractAction {

	
	
	private static final long serialVersionUID = -4365773690582053808L;
	
	private AccountTradeRecordService accountTradeRecordService;
	
	
	private AccountTradeRecord accountTradeRecord;
	private AccountTradeRecordDetail accountTradeRecordDetail;
	private Date  fromDate;
	private Date  toDate;
	private String downLoadDateStr;
	private Page<AccountTradeRecord> page=new Page<AccountTradeRecord>();
	public AccountTradingSearchAction(){
		super();
		accountTradeRecordDetail=new AccountTradeRecordDetail();
		accountTradeRecord=new AccountTradeRecord();
		page.setCurrentPage(1);
		page.setCountField("a.id");
		

	}

	@Override
	public String process() {
		
		
		Date today=null;
		Date lateMonth=null;
		
		Calendar calendar= Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		lateMonth=calendar.getTime();
        
		today=new Date();
		
		
		
		if(fromDate==null){
			fromDate=lateMonth;
		}
		if(toDate==null){
			toDate=today;
		}
		
		String type=getRequest().getParameter("type");
		if("show".equals(type)){
			return SUCCESS;
		}
		
		page=	accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, fromDate, toDate);

		return "result_mini";
	}
	

	
	public void downloadAccountTrading(){
		page.setPageSize(1000);
		process();
		downLoadDateStr=transDate10String(fromDate)+"_"+transDate10String(toDate);
		
		List<Object> objList = new ArrayList<Object>();
		
		if(page.getmResults()!=null&& !page.getmResults().isEmpty()){
			for(int i=0;i<page.getmResults().size();i++){
				AccountTradeRecord accountTradeRecord=	(AccountTradeRecord)page.getmResults().get(i);
				AccountTradeRecordVO tradeRecordVO = new AccountTradeRecordVO();
				tradeRecordVO.setSubscriberPhoneNo(accountTradeRecord.getSubscriber().getPhoneNo());
				tradeRecordVO.setSubscriberName(accountTradeRecord.getSubscriber().getName());
				tradeRecordVO.setType(Account.getTradeType(accountTradeRecord.getType()));
				tradeRecordVO.setAmount(accountTradeRecord.getAmount());
				tradeRecordVO.setTradeTime(accountTradeRecord.getTradeTime());
				tradeRecordVO.setPayChannel(Account.getPayChannel(accountTradeRecord.getPayChannel()));
				tradeRecordVO.setPayType(Account.getPayType(accountTradeRecord.getPayType()));
				String bizId=null;
				if(Account.TYPE_ORDER.equals(accountTradeRecord.getType())){
					bizId=accountTradeRecord.getBizId();
				}else{
					bizId=accountTradeRecord.getTradeOrderNo();
				}
				tradeRecordVO.setBizId(bizId);
				objList.add(tradeRecordVO);
			}
			
		}
		
		ExcelUtil.exportExcel(objList, "交易记录_"+downLoadDateStr+"_"+new Date().getTime());
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

	public AccountTradeRecordDetail getAccountTradeRecordDetail() {
		return accountTradeRecordDetail;
	}

	public void setAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail) {
		this.accountTradeRecordDetail = accountTradeRecordDetail;
	}

	
	
	
	
}
