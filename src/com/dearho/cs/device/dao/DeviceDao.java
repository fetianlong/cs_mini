package com.dearho.cs.device.dao;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.Device;

public interface DeviceDao {
	/**
	 * 添加车机信息
	 * @param carSet
	 */
	void addCarSet(Device device);
	/**
	 * 更新车机信息
	 * @param carSet
	 */
	void updateCarSet(Device device);
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteCarSetByIds(String [] ids);
	
	/**
	 * 分页查询
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<Device> queryPageCarSet(Page<Device> page,String hql);
	/**
	 * 根据Id 单个查询
	 * @param id
	 * @return
	 */
	Device queryCarSetById(String id);
}
