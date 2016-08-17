package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarInsuranceDAO;
import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarInsuranceDAOImpl extends AbstractDaoSupport implements CarInsuranceDAO{

	@Override
	public List<CarInsurance> searchCarInsurance(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCarInsurance(CarInsurance carInsurance) {
		addEntity(carInsurance);
	}

	@Override
	public void updateCarInsurance(CarInsurance carInsurance) {
		updateEntity(carInsurance);
	}

	@Override
	public void deleteCarInsurance(String[] checkdel) {
		final String queryString="delete CarInsurance where id in (:ids)";
		deleteEntity(CarInsurance.class, queryString, checkdel);
	}

	@Override
	public Page<CarInsurance> searchCarInsurance(Page<CarInsurance> page,
			String hql) {
		Page<CarInsurance> resultPage = pageCache(CarInsurance.class ,page, hql);
		resultPage.setResults(idToObj(CarInsurance.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public CarInsurance searchCarInsuranceById(String id) {
		return get(CarInsurance.class, id);
	}

}
