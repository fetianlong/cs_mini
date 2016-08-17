package com.dearho.cs.device.service;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.Device;

public interface DeviceService {
	/**
	 * 添加
	 * @param carSet
	 */
	void addCarSet(Device device);
	/**
	 * 更新
	 * @param carSet
	 */
	void updateCarSet(Device device);
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByArrays(String[] ids);
	/**
	 * 获取单个信息
	 * @param id
	 * @return
	 */
	Device queryCarSetById(String id);
	/**
	 * 按条件分页查询
	 * @param page
	 * @param carSet
	 * @return
	 */
	Page<Device> queryPageCarSet(Page<Device> page,Device carSet);
	
	
	/**
	 * 按条件分页查询是否绑定的车机
	 * @param page
	 * @param carSet
	 * @return
	 */
	Page<Device> queryPageBindCarSet(Page<Device> page,Device carSet,int bindType);
}
