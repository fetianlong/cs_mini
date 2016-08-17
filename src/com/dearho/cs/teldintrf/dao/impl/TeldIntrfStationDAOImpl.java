package com.dearho.cs.teldintrf.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.dao.TeldIntrfStationDAO;
import com.dearho.cs.teldintrf.pojo.TeldIntrfStation;

public class TeldIntrfStationDAOImpl extends AbstractDaoSupport implements TeldIntrfStationDAO {

	@Override
	public void addTeldIntrfStation(TeldIntrfStation station) {
		addEntity(station);
	}

	@Override
	public void updateTeldIntrfStation(TeldIntrfStation station) {
		updateEntity(station);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeldIntrfStation> findByAllHql(String hql, Object... objects) {
		return getHibernateTemplate().find(hql,objects);
	}

	@Override
	public Page<TeldIntrfStation> getTeldStationPages(
			Page<TeldIntrfStation> page, String hql) {
		Page<TeldIntrfStation> resultPage = pageCache(TeldIntrfStation.class ,page, hql);
		resultPage.setResults(idToObj(TeldIntrfStation.class, resultPage.getmResults()));
		return resultPage;
	}

}
