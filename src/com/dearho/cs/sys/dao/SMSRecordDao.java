package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.SMSRecord;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public interface SMSRecordDao {
	SMSRecord getSMSRecordById(String id);
	
	void addSMSRecord(SMSRecord smsRecord);
	
	Page<SMSRecord> searchSMSRecord(Page<SMSRecord> page,String hql);
	
	public List<SMSRecord> searchSMSRecord(String hql);
	
	
}
