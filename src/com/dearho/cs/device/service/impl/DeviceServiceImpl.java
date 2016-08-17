package com.dearho.cs.device.service.impl;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.dao.DeviceDao;
import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.util.StringHelper;

public class DeviceServiceImpl implements DeviceService {
	
	private DeviceDao deviceDao;
	



	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Override
	public void addCarSet(Device device) {
		deviceDao.addCarSet(device);
	}

	@Override
	public void updateCarSet(Device device) {
		deviceDao.updateCarSet(device);

	}

	@Override
	public void deleteByArrays(String[] ids) {
		deviceDao.deleteCarSetByIds(ids);
	}

	@Override
	public Device queryCarSetById(String id) {
		return deviceDao.queryCarSetById(id);
	}

	@Override
	public Page<Device> queryPageCarSet(Page<Device> page, Device carSet) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Device a where 1=1");
		if(carSet!=null){
			if(StringHelper.isNotEmpty(carSet.getSetName())){
				sb.append(" and a.setName like '%").append(carSet.getSetName()).append("%'");
			}
			if(StringHelper.isNotEmpty(carSet.getSetType())){
				sb.append(" and a.setType like '%").append(carSet.getSetType()).append("%'");
			}
			if(StringHelper.isNotEmpty(carSet.getSimId())){
				sb.append(" and a.simId like '%").append(carSet.getSimId()).append("%'");
			}
			if(StringHelper.isNotEmpty(carSet.getSetNo())){
				sb.append(" and a.setNo like '%").append(carSet.getSetNo()).append("%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : 
			" order by a."+ page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=deviceDao.queryPageCarSet(page, sb.toString());
		return page;
	}

	@Override
	public Page<Device> queryPageBindCarSet(Page<Device> page, Device carSet,
			int bindType) {
		StringBuffer sb=new StringBuffer();
		//查询未绑定的
		if(bindType == 0){
			sb.append("select a.id from Device a where a.id not in (select d.deviceId from DeviceBinding d where d.bindType = 1) ");
		}
		//查询绑定的
		else if(bindType == 1){
			sb.append("select a.id from Device a,DeviceBinding d where a.id = d.deviceId and d.bindType = 1");
		}
		
		if(carSet!=null){
			if(StringHelper.isNotEmpty(carSet.getSetName())){
				sb.append(" and a.setName like '%").append(carSet.getSetName()).append("%'");
			}
			if(StringHelper.isNotEmpty(carSet.getSetNo())){
				sb.append(" and a.setNo like '%").append(carSet.getSetNo()).append("%'");
			}
			if(StringHelper.isNotEmpty(carSet.getSetType())){
				sb.append(" and a.carSetType like '%").append(carSet.getSetType()).append("%'");
			}
			if(StringHelper.isNotEmpty(carSet.getSimId())){
				sb.append(" and a.simId like '%").append(carSet.getSimId()).append("%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc " :
			" order by a."+ page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=deviceDao.queryPageCarSet(page, sb.toString());
		return page;
	}

}
