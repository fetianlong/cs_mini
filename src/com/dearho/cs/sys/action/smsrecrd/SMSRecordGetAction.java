package com.dearho.cs.sys.action.smsrecrd;



import org.apache.cxf.common.util.StringUtils;

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
public class SMSRecordGetAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private SMSRecordService smsRecordService;
	
	private SMSRecord smsRecord;
	
	public SMSRecordGetAction(){
		super();
		smsRecord=new SMSRecord();
	}
	
	public String process() {
		//修改
		if(!StringUtils.isEmpty(smsRecord.getId())){
			smsRecord=smsRecordService.searchSMSRecordByID(smsRecord.getId());
		}
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

	
	
	

}
