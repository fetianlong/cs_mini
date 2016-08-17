package com.dearho.cs.orders.dao.impl;


import java.util.List;


import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.ChargingLogDao;
import com.dearho.cs.orders.pojo.ChargingLog;

/**
 * @author GaoYunpeng
 *
 */
public class ChargingLogDaoImpl extends AbstractDaoSupport implements ChargingLogDao {


	@Override
	public void addChargingLog(ChargingLog chargingLog) {
		addEntity(chargingLog);
	}

	@Override
	public void updateChargingLog(ChargingLog chargingLog) {
		updateEntity(chargingLog);
	}

	@Override
	public Page<ChargingLog> queryChargingLogPage(Page<ChargingLog> page, String hql) {
		Page<ChargingLog> resultPage=pageCache(ChargingLog.class, page, hql);
		resultPage.setResults(idToObj(ChargingLog.class, resultPage.getmResults()));
		return resultPage;
	}

	public List<ChargingLog> queryChargingLog(String hql) {
		return getList(ChargingLog.class, queryFList(hql));
	}

	@Override
	public ChargingLog queryChargingLogById(String id) {
		ChargingLog chargingLog=get(ChargingLog.class, id);
		return chargingLog;
	}

	@Override
	public List<ChargingLog> queryOrderByHql(String hql) {
		return getHibernateTemplate().find(hql);
	}

}
