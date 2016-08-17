package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 车辆巡检管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CarPatrolService {
	
	List<CarPatrol> searchCarPatrol(CarPatrol carPatrol);
	
	void addCarPatrol(CarPatrol carPatrol);
	void updateCarPatrol(CarPatrol carPatrol);
	void deleteCarPatrol(final String[] checkdel);
	
	Page<CarPatrol> searchCarPatrol(Page<CarPatrol> page,CarPatrol carPatrol);

	CarPatrol searchCarPatrolById(String id);
}
