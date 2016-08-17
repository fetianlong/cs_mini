package com.dearho.cs.place.dao.impl;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.dao.CarDotBindingDao;
import com.dearho.cs.place.pojo.CarDotBinding;

public class CarDotBindingDaoImpl extends AbstractDaoSupport implements CarDotBindingDao{

	@Override
	@SuppressWarnings("unchecked")
	public int getCarCountByDotId(String dotId,int isUsed) {
		String queryString = " select id from CarDotBinding a where a.dotId = ? and a.isUsed = ?";
		List<CarDotBinding> result = getHibernateTemplate().find(queryString, dotId,isUsed);
		return result.size();
	}

	@Override
	public Page<Car> queryCarByPage(Page<Car> page, String hql) {
		Page<Car> resultPage=pageCache(Car.class, page, hql);
		resultPage.setResults(idToObj(Car.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public List<CarDotBinding> queryCarDotBinding(String queryStr,Object... objs) {
		return getHibernateTemplate().find(queryStr,objs);
	}

	@Override
	public void add(CarDotBinding carParkingBind) {
		addEntity(carParkingBind);;
	}

	@Override
	public void delete(String[] ids) {
		final String queryString="delete CarDotBinding where id in (:ids)";
		deleteEntity(CarDotBinding.class, queryString, ids);
	}

	@Override
	public void update(CarDotBinding carDotBinding) {
		updateEntity(carDotBinding);
	}

}
