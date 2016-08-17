package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 网点巡检管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface ParkingPatrolService {
	
	List<ParkingPatrol> searchParkingPatrol(ParkingPatrol parkingPatrol);
	
	void addParkingPatrol(ParkingPatrol parkingPatrol);
	void updateParkingPatrol(ParkingPatrol parkingPatrol);
	void deleteParkingPatrol(final String[] checkdel);
	
	Page<ParkingPatrol> searchParkingPatrol(Page<ParkingPatrol> page,ParkingPatrol parkingPatrol);

	ParkingPatrol searchParkingPatrolById(String id);

	boolean checkHasChildPatrol(String pid);
}
