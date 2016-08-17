package com.dearho.cs.orders.service;



import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.ChargingLog;

public interface ChargingLogService {
	
	void addChargingLog(ChargingLog chargingLog);
	
	void updateChargingLog(ChargingLog chargingLog);
	
	List<ChargingLog> queryChargingLog(String hql);
	
	ChargingLog queryChargingLogById(String id);
	
	Page<ChargingLog> queryChargingLogPage(Page<ChargingLog> page,ChargingLog chargingLog);

	ChargingLog queryChargingLogByOrdersId(String id);

	
}
