package com.dearho.cs.place.dao;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.CarDotBinding;

public interface CarDotBindingDao {

	int getCarCountByDotId(String dotId,int isUsed);
	
	Page<Car> queryCarByPage(Page<Car> page, String string);

	List<CarDotBinding> queryCarDotBinding(String queryStr,Object... objs);

	void add(CarDotBinding carDotBinding);

	void delete(String[] ids);
	
	void update(CarDotBinding carDotBinding);

}
