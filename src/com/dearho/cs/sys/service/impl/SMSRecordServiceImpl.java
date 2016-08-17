package com.dearho.cs.sys.service.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.SMSRecordDao;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSRecordService;
import com.dearho.cs.util.StringHelper;

public class SMSRecordServiceImpl implements SMSRecordService{

	private SMSRecordDao smsRecordDao;
	
	
	@Override
	public List<SMSRecord> searchSMSRecord(SMSRecord smsRecord) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a.id from SMSRecord a where 1 = 1 order by a.ts desc ");
		return smsRecordDao.searchSMSRecord(hql.toString());
	}

	@Override
	public SMSRecord searchSMSRecordByID(String id) {
		return smsRecordDao.getSMSRecordById(id);
	}

	@Override
	public Page<SMSRecord> searchSMSRecord(Page<SMSRecord> page, SMSRecord smsRecord) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from SMSRecord a where 1 = 1 ");
		if (smsRecord != null){
			if(!StringUtils.isEmpty(smsRecord.getPhoneNo())){
				sb.append(" and phoneNo like '%"+smsRecord.getPhoneNo()+"%' ");
			}
			if(smsRecord.getType()!=null){
				sb.append(" and type ="+smsRecord.getType()+" ");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "order by ts desc" : page.getOrderByString());
		page = smsRecordDao.searchSMSRecord(page, sb.toString());
		return page;
	}

	@Override
	public void addSMSRecord(SMSRecord smsRecord) {
		smsRecordDao.addSMSRecord(smsRecord);
		
	}

	public SMSRecordDao getSmsRecordDao() {
		return smsRecordDao;
	}

	public void setSmsRecordDao(SMSRecordDao smsRecordDao) {
		this.smsRecordDao = smsRecordDao;
	}
	
	
	
	
}
