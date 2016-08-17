package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.SMSRecord;


public interface SMSRecordService {
	
	public List<SMSRecord> searchSMSRecord(SMSRecord smsRecord);

	public void addSMSRecord(SMSRecord smsRecord);

	public SMSRecord searchSMSRecordByID(String id);


	public Page<SMSRecord> searchSMSRecord(Page<SMSRecord> page, SMSRecord smsRecord);

	
}
