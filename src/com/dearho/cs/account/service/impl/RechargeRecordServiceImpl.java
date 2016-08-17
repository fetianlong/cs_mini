/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountServiceImpl.java creation date:[2015-6-1 下午02:48:52] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.RechargeRecordDao;
import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.account.service.RechargeRecordService;
import com.dearho.cs.core.db.page.Page;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 *
 */
public class RechargeRecordServiceImpl implements RechargeRecordService {
	
	private RechargeRecordDao rechargeRecordDao;




	@Override
	public Page<RechargeRecord> queryRechargeRecordByPage(Page<RechargeRecord> page, RechargeRecord rechargeRecord,Date fromDate,Date toDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer hql = new StringBuffer();
		hql.append("select a.id  from RechargeRecord a where 1=1 ");
		if(rechargeRecord!=null){
			if(rechargeRecord.getType()!=null){
				hql.append(" and type=").append(rechargeRecord.getType());
			}
			if(StringUtils.isNotEmpty(rechargeRecord.getSubscriberPhoneNo())){
				hql.append(" and subscriberPhoneNo like '%").append(rechargeRecord.getSubscriberPhoneNo()).append("%'");
			}
			if(StringUtils.isNotEmpty(rechargeRecord.getSubscriberName())){
				hql.append(" and subscriberName like '%").append(rechargeRecord.getSubscriberName()).append("%'");
			}
			if(StringUtils.isNotEmpty(rechargeRecord.getOperatorName())){
				hql.append(" and operatorName like '").append(rechargeRecord.getOperatorName()).append("%'");
			}
		}
		
		if(fromDate!=null){
			hql.append(" and a.createTime  >=str_to_date('"+format.format(fromDate)+"','%Y-%m-%d')");

		}
		if(toDate!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(toDate);
			calendar.add(Calendar.DATE, +1);
			
			hql.append(" and a.createTime  <=str_to_date('"+format.format(calendar.getTime())+"','%Y-%m-%d')");
		}
		
		hql.append(" order by createTime desc");
		return  rechargeRecordDao.queryRechargeRecordByPage(page, hql.toString());
	}


	@Override
	public RechargeRecord getRechargeRecordById(String id) {
		return rechargeRecordDao.getRechargeRecordById(id);
	}


	@Override
	public void addRechargeRecord(RechargeRecord rechargeRecord) {
		rechargeRecordDao.addRechargeRecord(rechargeRecord);
	}


	@Override
	public void updateRechargeRecord(RechargeRecord rechargeRecord) {
		rechargeRecordDao.updateRechargeRecord(rechargeRecord);
		
	}


	@Override
	public List<RechargeRecord> queryRechargeRecord(RechargeRecord rechargeRecord) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id from RechargeRecord a where 1=1 ");
		hql.append(" order by createTime asc");
	
		return rechargeRecordDao.queryRechargeRecordList(hql.toString());
	}


	public RechargeRecordDao getRechargeRecordDao() {
		return rechargeRecordDao;
	}


	public void setRechargeRecordDao(RechargeRecordDao rechargeRecordDao) {
		this.rechargeRecordDao = rechargeRecordDao;
	}




	

	
	
}
