package com.dearho.cs.resmonitor.service.impl;

import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.resmonitor.dao.CarLocationDAO;
import com.dearho.cs.resmonitor.pojo.CarLocation;
import com.dearho.cs.resmonitor.service.CarLocationService;
import com.dearho.cs.util.StringHelper;

public class CarLocationImpl implements CarLocationService {

	private CarLocationDAO carLocationDAO;
	
	
	@Override
	public Page<CarLocation> getLocationsByDeviceNo(Page<CarLocation> page,String deviceNo) {
		String hql = "select a.id from CarLocation a";
		if(StringHelper.isNotEmpty(deviceNo)){
			hql += " where a.deviceNo = '"+deviceNo+"'";
		}
		hql += StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString();
		return carLocationDAO.getLocationByDeviceNo(page,hql);
	}

	
	public CarLocationDAO getCarLocationDAO() {
		return carLocationDAO;
	}
	public void setCarLocationDAO(CarLocationDAO carLocationDAO) {
		this.carLocationDAO = carLocationDAO;
	}


	@Override
	public List<CarLocation> getLocationByParam(String deviceNo,
			Date startTime, Date endTime) {
		return carLocationDAO.getLocationByParam(deviceNo,startTime,endTime);
	}


	@Override
	public void add(CarLocation cl) {
		carLocationDAO.add(cl);
	}
	
}
