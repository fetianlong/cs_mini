package com.dearho.cs.car.dao.impl;

import java.util.List;

import com.dearho.cs.car.dao.CarDao;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

public class CarDaoImpl extends AbstractDaoSupport implements CarDao {

	@Override
	public void addCar(Car car) {
		addEntity(car);
	}

	@Override
	public void updateCar(Car car) {
		updateEntity(car);
	}


	@Override
	public Page<Car> queryCarByPage(Page<Car> page, String hql) {
		Page<Car> resultPage=pageCache(Car.class, page, hql);
		resultPage.setResults(idToObj(Car.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null&&resultPage.getResults().size()>0){
			for (int i = 0; i < resultPage.getResults().size(); i++) {
				Car car=(Car) resultPage.getResults().get(i);
				if(StringHelper.isNotEmpty(car.getModelId())){
					car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId().trim()));
				}
			}
		}
		return resultPage;
	}
	
	@Override
	public Car queryCarById(String id) {
		Car car=get(Car.class, id);
		if(null == car ) return null;
		if(StringHelper.isNotEmpty(car.getModelId())){
			car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId()));
		}
		return car;
	}

	@Override
	public void deleteCarArrays(String[] ids) {
		final String hql="delete Car where id in (:ids)";
		deleteEntity(Car.class, hql, ids);
	}

	@Override
	public List<Car> queryCarByModelId(String modelId) {
		String hql="select id from Car where modelId ='"+modelId+"'";
		return getList(Car.class,queryFList(hql));
	}

	@Override
	public Car queryCarByVehiclePlateId(String vehiclePlateId) {
		String hql="select id from Car where vehiclePlateId='"+vehiclePlateId+"'";
		return get(Car.class, (String)getQuery(hql).uniqueResult());
	}

	@Override
	public List<Car> queryCars(Car car) {
		String hql="select id from Car where 1=1 ";
		if(car != null){
			if(StringHelper.isNotEmpty(car.getModelId())){
				hql += " and modelId = '" + car.getModelId()+"'";
			}
			if(StringHelper.isNotEmpty(car.getVehiclePlateId())){
				hql += " and vehiclePlateId like '%"+car.getVehiclePlateId()+"%'";
			}
		}
		return getList(Car.class,queryFList(hql));
	}

	@Override
	public List<Car> queryCarsByHql(String hql) {
		return  getList(Car.class,queryFList(hql));
	}



}
