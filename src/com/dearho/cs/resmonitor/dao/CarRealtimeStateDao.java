package com.dearho.cs.resmonitor.dao;

import java.util.List;

import com.dearho.cs.resmonitor.entity.CarInfoEntiry;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;



public interface CarRealtimeStateDao {

	List<CarInfoEntiry> getCarInfo(List<String> ids);

	void save(CarRealtimeState crs);

	CarRealtimeState getCarRealTimeStateById(String id);

	void update(CarRealtimeState crs);
}
