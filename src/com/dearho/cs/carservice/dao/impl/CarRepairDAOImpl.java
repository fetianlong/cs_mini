package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarRepairDAO;
import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarRepairDAOImpl extends AbstractDaoSupport implements CarRepairDAO{

	@Override
	public List<CarRepair> searchCarRepair(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCarRepair(CarRepair carRepair) {
		addEntity(carRepair);
	}

	@Override
	public void updateCarRepair(CarRepair carRepair) {
		updateEntity(carRepair);
	}

	@Override
	public void deleteCarRepair(String[] checkdel) {
		final String queryString="delete CarRepair where id in (:ids)";
		deleteEntity(CarRepair.class, queryString, checkdel);
	}

	@Override
	public Page<CarRepair> searchCarRepair(Page<CarRepair> page,
			String hql) {
		Page<CarRepair> resultPage = pageCache(CarRepair.class ,page, hql);
		resultPage.setResults(idToObj(CarRepair.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public CarRepair searchCarRepairById(String id) {
		return get(CarRepair.class, id);
	}

}
