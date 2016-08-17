package com.dearho.cs.device.service.impl;

import java.util.List;

import com.dearho.cs.car.dao.CarDao;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.dao.DeviceBindingDao;
import com.dearho.cs.device.dao.DeviceDao;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.util.StringHelper;

public class DeviceBindingServiceImpl implements DeviceBindingService {
	
	private DeviceBindingDao deviceBindingDao;
	private CarDao carDao;
	private DeviceDao deviceDao;

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}

	public void setDeviceBindingDao(DeviceBindingDao deviceBindingDao) {
		this.deviceBindingDao = deviceBindingDao;
	}

	@Override
	public void addCarBind(DeviceBinding carBind) {
		deviceBindingDao.addCarBind(carBind);
	}

	@Override
	public void updateCarBind(DeviceBinding carBind) {
		deviceBindingDao.updateCarBind(carBind);
	}

	@Override
	public void deleteCarBindByIds(String[] ids) {
		deviceBindingDao.deleteCarBind(ids);
	}

	@Override
	public Page<DeviceBinding> queryCarBindPages(Page<DeviceBinding> page, DeviceBinding carBind) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from DeviceBinding a where 1=1");
		if(carBind!=null){
			if(StringHelper.isNotEmpty(carBind.getDeviceNo())){
				sb.append(" and a.deviceNo like '%").append(carBind.getDeviceNo()).append("%'");
			}
			if(carBind.getCar()!=null){
				if(StringHelper.isNotEmpty(carBind.getCar().getVehiclePlateId())){
					sb.append(" and a.carId in(select c.id from Car c where 1=1");
					sb.append(" and c.vehiclePlateId like '%").append(carBind.getCar().getVehiclePlateId()).append("%'");
					sb.append(")");
				}
			}
			if(carBind.getDevice()!=null){
				if(StringHelper.isNotEmpty(carBind.getDevice().getSimId())){
					sb.append(" and a.deviceId in(select s.id from Device s where 1=1");
					sb.append(" and s.simId like '%").append(carBind.getDevice().getSimId()).append("%'");
					sb.append(")");
				}
			}
			if(carBind.getBindType()!=null){
				sb.append(" and a.bindType=").append(carBind.getBindType());
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.bindDate desc " : 
			"order by a."+page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=deviceBindingDao.queryPageCarBind(page, sb.toString());
		return page;
	}

	@Override
	public DeviceBinding queryCarBindById(String id) {
		DeviceBinding binding=deviceBindingDao.queryBindById(id);
		if(StringHelper.isNotEmpty(binding.getCarId())){
			binding.setCar(carDao.queryCarById(binding.getCarId()));
		}
		if(StringHelper.isNotEmpty(binding.getDeviceId())){
			binding.setDevice(deviceDao.queryCarSetById(binding.getDeviceId()));
		}
		return binding;
	}


	@Override
	public List<DeviceBinding> queryBindByCarId(String carId,Integer state) {
		String hql="select id from DeviceBinding where carId='"+carId+"'";
		if(state!=null){
			hql+=" and bindType="+state;
		}
		return deviceBindingDao.queryBindList(hql);
	}

	@Override
	public List<DeviceBinding> queryBindByDeviceId(String deviceId,Integer state) {
		String hql="select id from DeviceBinding where deviceId='"+deviceId+"'";
		if(state!=null){
			hql+=" and bindType="+state;
		}
		return deviceBindingDao.queryBindList(hql);
	}

	@Override
	public List<DeviceBinding> queryBindByDeviceNo(String deviceNo,
			Integer state) {
		String hql="select id from DeviceBinding where deviceNo='"+deviceNo+"'";
		if(state!=null){
			hql+=" and bindType="+state;
		}
		return deviceBindingDao.queryBindList(hql);
	}

}
