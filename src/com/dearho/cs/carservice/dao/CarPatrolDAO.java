package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 车辆巡检
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface CarPatrolDAO {
	
	List<CarPatrol> searchCarPatrol(String hql);
	
	void addCarPatrol(CarPatrol carPatrol);
	void updateCarPatrol(CarPatrol carPatrol);
	void deleteCarPatrol(final String[] checkdel);
	
	Page<CarPatrol> searchCarPatrol(Page<CarPatrol> page,String hql);
	
	CarPatrol searchCarPatrolById(String id);
}
