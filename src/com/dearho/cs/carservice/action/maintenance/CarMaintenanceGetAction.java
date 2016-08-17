package com.dearho.cs.carservice.action.maintenance;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.carservice.service.CarMaintenanceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class CarMaintenanceGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CarMaintenanceService carMaintenanceService;
	
	private CarMaintenance carMaintenance;
	
	private AreaService areaService;
	private CarService carService;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			carMaintenance = new CarMaintenance();
		}else{
			CarMaintenance eEntity = carMaintenanceService.searchCarMaintenanceById(id);
			if (eEntity == null){
				carMaintenance = new CarMaintenance();
			}
			else {
				carMaintenance = eEntity;
				Car c = carService.queryCarById(carMaintenance.getCarId());
				if(c != null){
					carMaintenance.setPlateNumber(c.getVehiclePlateId());
					carMaintenance.setVehicleModel(c.getCarVehicleModel().getName());
				}
			}
			
		}
		return SUCCESS;
	}
	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode(null);
	}
	public AreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarMaintenanceService getCarMaintenanceService() {
		return carMaintenanceService;
	}

	public void setCarMaintenanceService(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
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
