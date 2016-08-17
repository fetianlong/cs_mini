package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarAccidentDAO;
import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarAccidentDAOImpl extends AbstractDaoSupport implements CarAccidentDAO{

	@Override
	public List<CarAccident> searchCarAccident(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCarAccident(CarAccident carAccident) {
		addEntity(carAccident);
	}

	@Override
	public void updateCarAccident(CarAccident carAccident) {
		updateEntity(carAccident);
	}

	@Override
	public void deleteCarAccident(String[] checkdel) {
		final String queryString="delete CarAccident where id in (:ids)";
		deleteEntity(CarAccident.class, queryString, checkdel);
	}

	@Override
	public Page<CarAccident> searchCarAccident(Page<CarAccident> page,
			String hql) {
		Page<CarAccident> resultPage = pageCache(CarAccident.class ,page, hql);
		resultPage.setResults(idToObj(CarAccident.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public CarAccident searchCarAccidentById(String id) {
		return get(CarAccident.class, id);
	}

}
