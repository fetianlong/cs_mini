package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.SMSRecordDao;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.util.StringHelper;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class SMSRecordDaoImpl  extends AbstractDaoSupport implements SMSRecordDao {



	@Override
	public SMSRecord getSMSRecordById(String id) {
		return get(SMSRecord.class, id);
	}

	@Override
	public void addSMSRecord(SMSRecord smsRecord) {
		addEntity(smsRecord);	
	}

	@Override
	public Page<SMSRecord> searchSMSRecord(Page<SMSRecord> page, String hql) {
		Page<SMSRecord> resultPage=pageCache(SMSRecord.class, page, hql);
		
		resultPage.setResults(idToObj(SMSRecord.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public List<SMSRecord> searchSMSRecord(String hql) {
		return getList(SMSRecord.class, queryFList( hql));
	}

}
