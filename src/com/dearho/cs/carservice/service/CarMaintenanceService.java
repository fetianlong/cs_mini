package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 车辆保养管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CarMaintenanceService {
	
	List<CarMaintenance> searchCarMaintenance(CarMaintenance carMaintenance);
	
	void addCarMaintenance(CarMaintenance carMaintenance);
	void updateCarMaintenance(CarMaintenance carMaintenance);
	void deleteCarMaintenance(final String[] checkdel);
	
	Page<CarMaintenance> searchCarMaintenance(Page<CarMaintenance> page,CarMaintenance carMaintenance);

	CarMaintenance searchCarMaintenanceById(String id);

	/**
	* 查询需要保养得车辆
	* @param page
	* @param carMaintenance
	* @return
	* @return Page<CarMaintenance>
	*/
	Page<CarMaintenance> searchCarMaintenanceNear7Days(
			Page<CarMaintenance> page, CarMaintenance carMaintenance);

	List<CarMaintenance> searchCarMaintenanceCurrentDate();

	/**
	 * 查询需要保养的车辆
	 * @param page
	 * @param carMaintenance
	 * @return
	 */
	Page<CarMaintenance> searchCarMaintenanceWarn(Page<CarMaintenance> page,
			CarMaintenance carMaintenance);
}
