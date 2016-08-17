package com.dearho.cs.device.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.DeviceBinding;

public interface DeviceBindingService {
	
	void addCarBind(DeviceBinding carBind);
	
	void updateCarBind(DeviceBinding carBind);
	
	void deleteCarBindByIds(String[] ids);
	
	Page<DeviceBinding> queryCarBindPages(Page<DeviceBinding> page,DeviceBinding carBind);
	
	DeviceBinding queryCarBindById(String id);
	
	/**
	 * 根据车辆id查询绑定信息
	 * @param carId 车辆Id
	 * @param state  绑定状态 1为绑定 0为解绑
	 * @return
	 */
	List<DeviceBinding> queryBindByCarId(String carId,Integer state);
	/**
	 * 根据车机Id查询车辆信息
	 * @param deviceId 车机Id
	 * @param state 绑定状态 1为绑定 0为解绑
	 * @return
	 */
	List<DeviceBinding> queryBindByDeviceId(String deviceId,Integer state);
	/**
	 * 根据车机编号查找绑定信息
	 * @param deviceNo 车机编号
	 * @param state 绑定状态 1为绑定 0为解绑
	 * @return
	 */
	List<DeviceBinding> queryBindByDeviceNo(String deviceNo,Integer state);
}
