package com.dearho.cs.carservice.action.maintenance;


import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.carservice.service.CarMaintenanceService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CarMaintenanceWarningAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarMaintenanceService carMaintenanceService;
	
	private Page<CarMaintenance> page = new Page<CarMaintenance>();
	
	private CarMaintenance carMaintenance;
	
	
	private CarService carService;
	
	public CarMaintenanceWarningAction(){
		super();
		carMaintenance = new CarMaintenance();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		page = carMaintenanceService.searchCarMaintenanceWarn(page, carMaintenance);
		if(page != null && page.getResults() != null){
			for (Object obj : page.getResults()) {
				CarMaintenance cm = (CarMaintenance)obj;
				Car c = carService.queryCarById(cm.getCarId());
				if(c != null){
					cm.setPlateNumber(c.getVehiclePlateId());
					cm.setVehicleModel(c.getCarVehicleModel().getName());
				}
			}
		}
		return Action.SUCCESS;
	}
	public CarMaintenanceService getCarMaintenanceService() {
		return carMaintenanceService;
	}

	public void setCarMaintenanceService(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	public Page<CarMaintenance> getPage() {
		return page;
	}

	public void setPage(Page<CarMaintenance> page) {
		this.page = page;
	}

	public CarMaintenance getCarMaintenance() {
		return carMaintenance;
	}

	public void setCarMaintenance(CarMaintenance carMaintenance) {
		this.carMaintenance = carMaintenance;
	}


	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
}
