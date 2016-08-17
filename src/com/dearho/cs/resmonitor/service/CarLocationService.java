package com.dearho.cs.resmonitor.service;

import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.resmonitor.pojo.CarLocation;

public interface CarLocationService {

	Page<CarLocation> getLocationsByDeviceNo(Page<CarLocation> page,String deviceNo);
	
	List<CarLocation> getLocationByParam(String deviceNo,Date startTime,Date endTime);

	void add(CarLocation cl);
}
