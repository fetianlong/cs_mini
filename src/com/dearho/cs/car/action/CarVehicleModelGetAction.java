package com.dearho.cs.car.action;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;

public class CarVehicleModelGetAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private CarVehicleModel carVehicleModel;
	
	private CarVehicleModelService carVehicleModelService;
	
	private StrategyBaseService strategyBaseService;
	
	private String id;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarVehicleModel getCarVehicleModel() {
		return carVehicleModel;
	}

	public void setCarVehicleModel(CarVehicleModel carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}
	
	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}
	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}

	@Override
	public String process() {
		if(StringHelper.isNotEmpty(id)){
			carVehicleModel=carVehicleModelService.queryModelById(id);
			if(carVehicleModel==null){
				carVehicleModel=new CarVehicleModel();
			}
			else{
				StrategyBase rizuStrategy = strategyBaseService.searchStrategyBaseById(carVehicleModel.getRizuStrategy());
				carVehicleModel.setRizuStrategyName(rizuStrategy == null ? "" : rizuStrategy.getName());
				StrategyBase shizuStrategy = strategyBaseService.searchStrategyBaseById(carVehicleModel.getShizuStrategy());
				carVehicleModel.setShizuStrategyName(shizuStrategy == null ? "" : shizuStrategy.getName());
			}
		}else{
			carVehicleModel=new CarVehicleModel();
		}
		return SUCCESS;
	}

}
