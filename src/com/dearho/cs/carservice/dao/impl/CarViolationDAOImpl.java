package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarViolationDAO;
import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarViolationDAOImpl extends AbstractDaoSupport implements CarViolationDAO{

	@Override
	public List<CarViolation> searchCarViolation(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCarViolation(CarViolation carViolation) {
		addEntity(carViolation);
	}

	@Override
	public void updateCarViolation(CarViolation carViolation) {
		updateEntity(carViolation);
	}

	@Override
	public void deleteCarViolation(String[] checkdel) {
		final String queryString="delete CarViolation where id in (:ids)";
		deleteEntity(CarViolation.class, queryString, checkdel);
	}

	@Override
	public Page<CarViolation> searchCarViolation(Page<CarViolation> page,
			String hql) {
		Page<CarViolation> resultPage = pageCache(CarViolation.class ,page, hql);
		resultPage.setResults(idToObj(CarViolation.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public CarViolation searchCarViolationById(String id) {
		return get(CarViolation.class, id);
	}

}
