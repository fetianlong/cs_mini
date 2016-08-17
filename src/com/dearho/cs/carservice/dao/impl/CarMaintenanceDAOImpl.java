package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarMaintenanceDAO;
import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarMaintenanceDAOImpl extends AbstractDaoSupport implements CarMaintenanceDAO{

	@Override
	public List<CarMaintenance> searchCarMaintenance(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCarMaintenance(CarMaintenance carMaintenance) {
		addEntity(carMaintenance);
	}

	@Override
	public void updateCarMaintenance(CarMaintenance carMaintenance) {
		updateEntity(carMaintenance);
	}

	@Override
	public void deleteCarMaintenance(String[] checkdel) {
		final String queryString="delete CarMaintenance where id in (:ids)";
		deleteEntity(CarMaintenance.class, queryString, checkdel);
	}

	@Override
	public Page<CarMaintenance> searchCarMaintenance(Page<CarMaintenance> page,
			String hql) {
		Page<CarMaintenance> resultPage = pageCache(CarMaintenance.class ,page, hql);
		resultPage.setResults(idToObj(CarMaintenance.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public CarMaintenance searchCarMaintenanceById(String id) {
		return get(CarMaintenance.class, id);
	}

}
