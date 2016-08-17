package com.dearho.cs.orders.service.impl;


import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.ChargingLogDao;
import com.dearho.cs.orders.pojo.ChargingLog;
import com.dearho.cs.orders.service.ChargingLogService;
import com.dearho.cs.util.StringHelper;

public class ChargingLogServiceImpl implements ChargingLogService {
	
	private ChargingLogDao chargingLogDao;
	
	public void setChargingLogDao(ChargingLogDao chargingLogDao) {
		this.chargingLogDao = chargingLogDao;
	}

	@Override
	public void addChargingLog(ChargingLog chargingLog) {
		chargingLogDao.addChargingLog(chargingLog);
	}

	@Override
	public void updateChargingLog(ChargingLog chargingLog) {
		chargingLogDao.updateChargingLog(chargingLog);
	}


	@Override
	public List<ChargingLog> queryChargingLog(String hql) {
		return chargingLogDao.queryChargingLog(hql);
	}


	@Override
	public ChargingLog queryChargingLogById(String id) {
		return chargingLogDao.queryChargingLogById(id);
	}

	@Override
	public Page<ChargingLog> queryChargingLogPage(Page<ChargingLog> page,
			ChargingLog chargingLog) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from ChargingLog a where 1=1");
		sb.append(" order by a.startTime desc");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.startTime desc " : page.getOrderByString());
		page=chargingLogDao.queryChargingLogPage(page, sb.toString());
		return page;
	}

	@Override
	public ChargingLog queryChargingLogByOrdersId(String ordersId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from ChargingLog a where a.ordersId = '").append(ordersId).append("'");
		sb.append(" and a.endTime = null");
		List<ChargingLog> logs =  chargingLogDao.queryChargingLog(sb.toString());
		if(logs != null && logs.size() > 0){
			return logs.get(0);
		}
		return null;
	}


}
