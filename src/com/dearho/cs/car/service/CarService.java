package com.dearho.cs.car.service;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Company;

public interface CarService {
	/**添加*/
	void addCar(Car car);
	/**更新*/
	void updateCar(Car car);
	/**分页查询*/
	Page<Car> queryCarByPage(Page<Car> page,Car car);
	
	/**分页查询是否绑定的车辆*/
	Page<Car> queryBindingCarByPage(Page<Car> page,Car car,int bindType);
	
	
	/**根据Id查询车辆信息*/
	Car queryCarById(String id);
	/**
	 * 删除操作
	 * @param ids
	 */
	void deleteCarByIds(String[] ids);
	
	/**
	 * 根据车型查询车辆信息
	 * @param modelIds
	 * @return
	 */
	List<Car> queryCarListByModelId(String modelId);
	
	/**
	 * 根据车牌号查询车辆信息
	 * @param vehiclePlateId
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
	
	List<Car> queryCars(Car car,Company company);
	
	/**
	 * @Title:queryCarByPageParking
	 * @Description:查询网点内的汽车
	 * @param page
	 * @param car
	 * @param parkingId
	 * @return
	 * @throws
	 */
	Page<Car> queryCarByPageDot(Page<Car> page, Car car,String dotId);
	
	/**
	* 查询违章车辆
	* @param page
	* @param car
	* @return
	* @return Page<Car>
	*/
	Page<Car> queryViolationCarByPage(Page<Car> page, Car car);
	
	/**
	* 查询可以维修的车辆
	* @param page
	* @param car
	* @return
	* @return Page<Car>
	*/
	Page<Car> queryCarToRepair(Page<Car> page, Car car);
	
	/**
	 * 查询id在ids里面的car
	 * @param ids
	 * @return
	 */
	List<Car> searchCarIn(String ids);
	
	/**
	 * 查询id不在ids里面的car
	 * @param ids
	 * @return
	 */
	List<Car> searchCarNotIn(String ids);
	
}
