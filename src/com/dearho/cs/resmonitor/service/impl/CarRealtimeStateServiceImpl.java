package com.dearho.cs.resmonitor.service.impl;

import java.util.List;

import com.dearho.cs.resmonitor.dao.CarRealtimeStateDao;
import com.dearho.cs.resmonitor.entity.CarInfoEntiry;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;


public class CarRealtimeStateServiceImpl implements CarRealtimeStateService {

	private CarRealtimeStateDao carRealtimeStateDao;
	
	@Override
	public List<CarInfoEntiry> getCarInfo(List<String> ids) {
		return carRealtimeStateDao.getCarInfo(ids);
	}

	public CarRealtimeStateDao getCarRealtimeStateDao() {
		return carRealtimeStateDao;
	}

	public void setCarRealtimeStateDao(CarRealtimeStateDao carRealtimeStateDao) {
		this.carRealtimeStateDao = carRealtimeStateDao;
	}

	@Override
	public void add(CarRealtimeState crs) {
		carRealtimeStateDao.save(crs);
		
	}

	@Override
	public CarRealtimeState getCarRealTimeState(String id) {
		return carRealtimeStateDao.getCarRealTimeStateById(id);
	}

	@Override
	public void update(CarRealtimeState crs) {
		carRealtimeStateDao.update(crs);
	}
	

	
}
