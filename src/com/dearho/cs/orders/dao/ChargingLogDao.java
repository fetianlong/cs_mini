package com.dearho.cs.orders.dao;


import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.ChargingLog;



/**
 * @author GaoYunpeng
 */
public interface ChargingLogDao {
	void addChargingLog(ChargingLog chargingLog);
	
	void updateChargingLog(ChargingLog chargingLog);
	
	Page<ChargingLog> queryChargingLogPage(Page<ChargingLog> page,String hql);
	
	List<ChargingLog> queryChargingLog(String hql);
	
	ChargingLog queryChargingLogById(String id);
	
	List<ChargingLog> queryOrderByHql(String string);
	
}
