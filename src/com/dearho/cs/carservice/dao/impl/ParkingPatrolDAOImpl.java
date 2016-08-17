package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.ParkingPatrolDAO;
import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class ParkingPatrolDAOImpl extends AbstractDaoSupport implements ParkingPatrolDAO{

	@Override
	public List<ParkingPatrol> searchParkingPatrol(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addParkingPatrol(ParkingPatrol parkingPatrol) {
		addEntity(parkingPatrol);
	}

	@Override
	public void updateParkingPatrol(ParkingPatrol parkingPatrol) {
		updateEntity(parkingPatrol);
	}

	@Override
	public void deleteParkingPatrol(String[] checkdel) {
		final String queryString="delete ParkingPatrol where id in (:ids)";
		deleteEntity(ParkingPatrol.class, queryString, checkdel);
	}

	@Override
	public Page<ParkingPatrol> searchParkingPatrol(Page<ParkingPatrol> page,
			String hql) {
		Page<ParkingPatrol> resultPage = pageCache(ParkingPatrol.class ,page, hql);
		resultPage.setResults(idToObj(ParkingPatrol.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public ParkingPatrol searchParkingPatrolById(String id) {
		return get(ParkingPatrol.class, id);
	}

	@Override
	public boolean checkHasChildPatrol(String pid) {
		String queryString = " select id from CarPatrol a where a.parkingPatrolId = '" + pid +"'";
		List<CarPatrol> result = getHibernateTemplate().find(queryString);
		return result.size() > 0 ;
	}

}
