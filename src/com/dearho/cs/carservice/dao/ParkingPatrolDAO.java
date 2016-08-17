package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 网点巡检
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface ParkingPatrolDAO {
	
	List<ParkingPatrol> searchParkingPatrol(String hql);
	
	void addParkingPatrol(ParkingPatrol parkingPatrol);
	void updateParkingPatrol(ParkingPatrol parkingPatrol);
	void deleteParkingPatrol(final String[] checkdel);
	
	Page<ParkingPatrol> searchParkingPatrol(Page<ParkingPatrol> page,String hql);
	
	ParkingPatrol searchParkingPatrolById(String id);

	boolean checkHasChildPatrol(String pid);
}
