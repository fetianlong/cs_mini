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

import com.dearho.cs.account.dao.AccountTradeRecordDao;
import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.core.db.page.Page;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountTradeRecordServiceImpl implements AccountTradeRecordService {
	private AccountTradeRecordDao accountTradeRecordDao;
	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;

	public void setAccountTradeRecordDao(AccountTradeRecordDao accountTradeRecordDao) {
		this.accountTradeRecordDao = accountTradeRecordDao;
	}

	
	@Override
	public Page<AccountTradeRecord> queryAccountTradeRecordByPage(Page<AccountTradeRecord> page,AccountTradeRecord accountTradeRecord,Date fromDate,Date toDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 
		StringBuffer hql = new StringBuffer();
		hql.append(" from AccountTradeRecord a where 1=1 and result = 1");
		
		if(accountTradeRecord!=null){
			
			if(accountTradeRecord.getType()!=null){
				hql.append(" and a.type= "+accountTradeRecord.getType());
			}
			if(StringUtils.isNotEmpty(accountTradeRecord.getSubscriberId())){
				hql.append(" and a.subscriberId= '"+accountTradeRecord.getSubscriberId()+"'");
			}
		}
		
		if(fromDate!=null){
			hql.append(" and a.tradeTime  >=str_to_date('"+format.format(fromDate)+"','%Y-%m-%d')");

		}
		if(toDate!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(toDate);
			calendar.add(Calendar.DATE, +1);
			
			hql.append(" and a.tradeTime  <=str_to_date('"+format.format(calendar.getTime())+"','%Y-%m-%d')");
		}
		hql.append(" order by a.tradeTime desc");

		return accountTradeRecordDao.queryAccountTradeRecordByPage(page, hql.toString());
	}


	public  List<AccountTradeRecordDetail>  getAccountTradeRecordDetailByBiz(String recordId) {
		
		return accountTradeRecordDetailDao.getAccountTradeRecordDetailByBizId(recordId);
	}
	
	public  Page<AccountTradeRecordDetail> queryAccountTradeRecordDetailByPage(Page<AccountTradeRecordDetail> page,AccountTradeRecordDetail accountTradeRecordDetail){
		StringBuffer hql = new StringBuffer();
		hql.append(" from AccountTradeRecordDetail a where 1=1 ");
		if(accountTradeRecordDetail!=null){
			if(StringUtils.isNotEmpty(accountTradeRecordDetail.getTradeRecordId())){
				hql.append(" tradeRecordId='").append(accountTradeRecordDetail.getTradeRecordId()).append("'");
			}
			
		}
		return accountTradeRecordDetailDao.queryAccountTradeRecordDetailByPage(page, hql.toString());
	}
	
	@Override
	public AccountTradeRecord getAccountTradeRecordById(String id) {
		return accountTradeRecordDao.getAccountTradeRecordById(id);
		
	}


	public void setAccountTradeRecordDetailDao(AccountTradeRecordDetailDao accountTradeRecordDetailDao) {
		this.accountTradeRecordDetailDao = accountTradeRecordDetailDao;
	}
	
	
	
}
