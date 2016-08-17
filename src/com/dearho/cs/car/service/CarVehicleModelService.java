package com.dearho.cs.car.service;

import java.util.List;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.pojo.StrategyBase;

public interface CarVehicleModelService {
	
	
	void addVeicleModel(CarVehicleModel vehicleModel);
	
	void updateVeicleModel(CarVehicleModel vehicleModel);
	
	void deleteModelByIds(String[] ids);
	
	Page<CarVehicleModel> queryPageModel(Page<CarVehicleModel> page,CarVehicleModel model);
	
	CarVehicleModel queryModelById(String id);
	/**
	 * 查询车型信息
	 * @param hql
	 * @return
	 */
	List<CarVehicleModel> queryModelByCon(CarVehicleModel model);

	List<CarVehicleModel> queryModelByName(CarVehicleModel carVehicleModel);

	/**
	 * 将车型更换策略
	 * @param oldstrategyBase
	 * @param strategyBase
	 */
	boolean updateModelStrategy(StrategyBase oldstrategyBase,
			StrategyBase strategyBase);
}
