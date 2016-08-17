package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 车辆保养
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface CarMaintenanceDAO {
	
	List<CarMaintenance> searchCarMaintenance(String hql);
	
	void addCarMaintenance(CarMaintenance carMaintenance);
	void updateCarMaintenance(CarMaintenance carMaintenance);
	void deleteCarMaintenance(final String[] checkdel);
	
	Page<CarMaintenance> searchCarMaintenance(Page<CarMaintenance> page,String hql);
	
	CarMaintenance searchCarMaintenanceById(String id);
}
