package com.dearho.cs.car.dao;

import java.util.List;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.page.Page;

public interface CarVehicleModelDao {
	
	void addModel(CarVehicleModel model);
	
	void updateModel(CarVehicleModel model);
	
	void deleteModelByIds(String[] ids);
	
	CarVehicleModel queryModelByID(String id);
	
	Page<CarVehicleModel> queryPageModel(Page<CarVehicleModel> page,String hql);
	
	/**
	 * 根据hql语句查询车型信息
	 * @param hql
	 * @return
	 */
	List<CarVehicleModel> queryModel(String hql);
}
