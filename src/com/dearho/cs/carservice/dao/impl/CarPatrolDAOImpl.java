package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarPatrolDAO;
import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarPatrolDAOImpl extends AbstractDaoSupport implements CarPatrolDAO{

	@Override
	public List<CarPatrol> searchCarPatrol(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCarPatrol(CarPatrol carPatrol) {
		addEntity(carPatrol);
	}

	@Override
	public void updateCarPatrol(CarPatrol carPatrol) {
		updateEntity(carPatrol);
	}

	@Override
	public void deleteCarPatrol(String[] checkdel) {
		final String queryString="delete CarPatrol where id in (:ids)";
		deleteEntity(CarPatrol.class, queryString, checkdel);
	}

	@Override
	public Page<CarPatrol> searchCarPatrol(Page<CarPatrol> page,
			String hql) {
		Page<CarPatrol> resultPage = pageCache(CarPatrol.class ,page, hql);
		resultPage.setResults(idToObj(CarPatrol.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public CarPatrol searchCarPatrolById(String id) {
		return get(CarPatrol.class, id);
	}

}
