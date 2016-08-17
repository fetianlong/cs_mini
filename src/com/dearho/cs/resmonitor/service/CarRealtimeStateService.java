package com.dearho.cs.resmonitor.service;

import java.util.List;

import com.dearho.cs.resmonitor.entity.CarInfoEntiry;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;



public interface CarRealtimeStateService {

	List<CarInfoEntiry> getCarInfo(List<String> ids);

	void add(CarRealtimeState crs);

	CarRealtimeState getCarRealTimeState(String id);

	void update(CarRealtimeState crs);
}
