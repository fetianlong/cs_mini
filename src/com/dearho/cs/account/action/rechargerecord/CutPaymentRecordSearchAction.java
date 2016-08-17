package com.dearho.cs.account.action.rechargerecord;

import java.util.Calendar;
import java.util.Date;

import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.account.service.RechargeRecordService;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberInfoSearchAction.java creation date:[2015-5-20 上午09:27:25] by liusong
 *http://www.dearho.com
 */


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.ExcelUtil;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class CutPaymentRecordSearchAction extends AbstractAction {


	private RechargeRecordService rechargeRecordService;
	private RechargeRecord rechargeRecord ;
	private Page<RechargeRecord> page=new Page<RechargeRecord>();
	
	private Date  fromDate;
	private Date  toDate;
	
	private String downLoadDateStr;
	
	public CutPaymentRecordSearchAction (){
		super();
		rechargeRecord=new RechargeRecord();
	
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
		
		rechargeRecord.setType(RechargeRecord.TYPE_CUT_PAYMENT);
		page=rechargeRecordService.queryRechargeRecordByPage(page, rechargeRecord, fromDate, toDate);
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public void downloadCutPaymentRecord(){

		page.setPageSize(1000);
		process();
		page.getmResults();
		downLoadDateStr=transDate10String(fromDate)+"_"+transDate10String(toDate);
		
		
		ExcelUtil.exportExcel(page.getResults(), "扣款记录_"+downLoadDateStr+"_"+new Date().getTime());
	
	}
	
	public RechargeRecordService getRechargeRecordService() {
		return rechargeRecordService;
	}
	public void setRechargeRecordService(RechargeRecordService rechargeRecordService) {
		this.rechargeRecordService = rechargeRecordService;
	}
	public RechargeRecord getRechargeRecord() {
		return rechargeRecord;
	}
	public void setRechargeRecord(RechargeRecord rechargeRecord) {
		this.rechargeRecord = rechargeRecord;
	}

	public Page<RechargeRecord> getPage() {
		return page;
	}

	public void setPage(Page<RechargeRecord> page) {
		this.page = page;
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
	
	
	
	

	
}
