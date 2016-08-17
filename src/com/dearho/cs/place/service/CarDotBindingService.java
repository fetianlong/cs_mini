package com.dearho.cs.place.service;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.CarDotBinding;

public interface CarDotBindingService {

	Page<Car> searchCars(Page<Car> page, Car car, String dotId,String type,String vehiclePlateId,int isUsed);

	void add(CarDotBinding carDotBinding);

	void delete(CarDotBinding carDotBinding);

	void update(CarDotBinding carDotBinding);

	CarDotBinding getBindingByCarId(String carId, int isUsed);

	/**
	 * 根据网点id查询网点下绑定的车辆
	 * @param dotId
	 * @param isUsed
	 * @return
	 */
	List<CarDotBinding> searchBindingByDotId(String dotId, Integer isUsed);
}
