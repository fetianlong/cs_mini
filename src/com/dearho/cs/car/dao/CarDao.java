package com.dearho.cs.car.dao;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;

public interface CarDao {
	/**
	 * 添加车辆
	 * @param car 
	 */
	void addCar(Car car);
	/**
	 * 更新车辆
	 * @param car
	 */
	void updateCar(Car car);
	/**
	 * 查询分页信息
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<Car> queryCarByPage(Page<Car> page,String hql);
	
	/**
	 * 根据ID查询车辆信息
	 * @param id
	 * @return
	 */
	Car queryCarById(String id);
	/**
	 * 删除
	 * @param ids
	 */
	void deleteCarArrays(String[] ids);
	
	/**
	 * 根据车型查询车辆信息
	 * @param modelIds
	 * @return
	 */
	List<Car> queryCarByModelId(String modelId);

	
	/**
	 * 根据车牌号查询车辆信息
	 * @return
	 */
	Car queryCarByVehiclePlateId(String vehiclePlateId);
	/**
	 * @Title:queryCars
	 * @Description:根据对象设置的字段来查询
	 * @param car
	 * @return
	 * @throws
	 */
	List<Car> queryCars(Car car);
	
	List<Car> queryCarsByHql(String hql);

}
