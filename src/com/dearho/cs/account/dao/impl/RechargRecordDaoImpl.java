/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountTradeRecordDaoImpl.java creation date:[2015-6-1 下午02:38:41] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.dao.impl;

import java.util.List;

import com.dearho.cs.account.dao.RechargeRecordDao;
import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.OrdersBill;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class RechargRecordDaoImpl extends AbstractDaoSupport implements RechargeRecordDao {




	@Override
	public void addRechargeRecord(RechargeRecord rechargeRecord) {
		addEntity(rechargeRecord);
	}


	@Override
	public void updateRechargeRecord(RechargeRecord rechargeRecord) {
		updateEntity(rechargeRecord);
	}


	@Override
	public Page<RechargeRecord> queryRechargeRecordByPage(Page<RechargeRecord> page, String hql) {
		Page<RechargeRecord> resultPage=pageCache(RechargeRecord.class, page, hql);
		resultPage.setResults(idToObj(RechargeRecord.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public RechargeRecord getRechargeRecordById(String id) {
		return get(RechargeRecord.class, id);
	}


	@Override
	public List<RechargeRecord> queryRechargeRecordList(String hql) {
		return getList(RechargeRecord.class, queryFList(hql));
	}



}
