package com.dearho.cs.resmonitor.dao;


import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.resmonitor.pojo.CarLocation;

public interface CarLocationDAO {

	Page<CarLocation> getLocationByDeviceNo(Page<CarLocation> page,String hql);

	List<CarLocation> getLocationByParam(String deviceNo, Date startTime,
			Date endTime);

	void add(CarLocation cl);
}
