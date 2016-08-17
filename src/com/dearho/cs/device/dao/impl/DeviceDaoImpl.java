package com.dearho.cs.device.dao.impl;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.dao.DeviceDao;
import com.dearho.cs.device.pojo.Device;

public class DeviceDaoImpl extends AbstractDaoSupport implements DeviceDao {

	@Override
	public void addCarSet(Device device) {
		addEntity(device);

	}

	@Override
	public void updateCarSet(Device device) {
		updateEntity(device);
	}

	@Override
	public void deleteCarSetByIds(String[] ids) {
		final String str="delete Device a where a.id in (:ids)";
		deleteEntity(Device.class, str, ids);
	}

	@Override
	public Page<Device> queryPageCarSet(Page<Device> page, String hql) {
		Page<Device> resultPage=pageCache(Device.class, page, hql);
		resultPage.setResults(idToObj(Device.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public Device queryCarSetById(String id) {
		return get(Device.class, id);
	}

}
