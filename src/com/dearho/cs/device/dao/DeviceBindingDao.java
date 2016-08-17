package com.dearho.cs.device.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.DeviceBinding;

public interface DeviceBindingDao {
	/**
	 * 添加
	 * @param bind
	 */
	void addCarBind(DeviceBinding bind);
	/**
	 * 更新
	 * @param bind
	 */
	void updateCarBind(DeviceBinding bind);
	/***
	 * 删除
	 * @param ids
	 */
	void deleteCarBind(String[] ids);
	/**
	 * 分页查询
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<DeviceBinding> queryPageCarBind(Page<DeviceBinding> page,String hql);
	/**
	 * 根据Id查询
	 * @param id
	 * @return
	 */
	DeviceBinding queryBindById(String id);
	
	
	/**
	 * 根据hql语句查询
	 * @param hql
	 * @return
	 */
	List<DeviceBinding> queryBindList(String hql);
}
