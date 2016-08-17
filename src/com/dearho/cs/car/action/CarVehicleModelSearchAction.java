package com.dearho.cs.car.action;

import java.util.List;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.sys.action.AbstractAction;

public class CarVehicleModelSearchAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private Page<CarVehicleModel> page;
	
	private CarVehicleModel carVehicleModel;
	
	private CarVehicleModelService carVehicleModelService;
	
	private StrategyBaseService strategyBaseService;
	

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}



	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}



	public Page<CarVehicleModel> getPage() {
		return page;
	}



	public void setPage(Page<CarVehicleModel> page) {
		this.page = page;
	}



	public CarVehicleModel getCarVehicleModel() {
		return carVehicleModel;
	}



	public void setCarVehicleModel(CarVehicleModel carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}


	
	public CarVehicleModelSearchAction() {
		super();
		page=new Page<CarVehicleModel>();
		page.setCurrentPage(1);
		page.setCountField("a.id");
		carVehicleModel=new CarVehicleModel();
	}



	@Override
	public String process() {
		page=carVehicleModelService.queryPageModel(page, carVehicleModel);
		if(page != null && page.getResults() != null && page.getResults().size() > 0){
			List<CarVehicleModel> models = page.getResults();
			for (CarVehicleModel carVehicleModel : models) {
				StrategyBase rizuStrategy = strategyBaseService.searchStrategyBaseById(carVehicleModel.getRizuStrategy());
				carVehicleModel.setRizuStrategyName(rizuStrategy == null ? "" : rizuStrategy.getName());
				StrategyBase shizuStrategy = strategyBaseService.searchStrategyBaseById(carVehicleModel.getShizuStrategy());
				carVehicleModel.setShizuStrategyName(shizuStrategy == null ? "" : shizuStrategy.getName());
			}
		}
		return SUCCESS;
	}



	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}



	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}

}
