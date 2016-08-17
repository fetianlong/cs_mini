package com.dearho.cs.sys.action.smsrecrd;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.sys.service.SMSRecordService;

/**
 * @Author liusogn
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class SMSRecordSerachAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SMSRecordService smsRecordService;
	
	private SMSRecord smsRecord;
	private Page<SMSRecord> page=new Page<SMSRecord>();
	public SMSRecordSerachAction (){
		super();
		smsRecord=new SMSRecord();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	
	public String process() {
		page=smsRecordService.searchSMSRecord(page, smsRecord);
		return SUCCESS;
	}


	public SMSRecordService getSmsRecordService() {
		return smsRecordService;
	}


	public void setSmsRecordService(SMSRecordService smsRecordService) {
		this.smsRecordService = smsRecordService;
	}


	public SMSRecord getSmsRecord() {
		return smsRecord;
	}


	public void setSmsRecord(SMSRecord smsRecord) {
		this.smsRecord = smsRecord;
	}


	public Page<SMSRecord> getPage() {
		return page;
	}


	public void setPage(Page<SMSRecord> page) {
		this.page = page;
	}


	

}
