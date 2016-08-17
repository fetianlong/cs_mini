package com.dearho.cs.carservice.action.insurance;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.carservice.service.CarInsuranceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class CarInsuranceGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CarInsuranceService carInsuranceService;
	
	private CarInsurance carInsurance;
	
	private AreaService areaService;
	private CarService carService;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			carInsurance = new CarInsurance();
		}else{
			CarInsurance eEntity = carInsuranceService.searchCarInsuranceById(id);
			if (eEntity == null){
				carInsurance = new CarInsurance();
			}
			else {
				carInsurance = eEntity;
				Car c = carService.queryCarById(carInsurance.getCarId());
				if(c != null){
					carInsurance.setPlateNumber(c.getVehiclePlateId());
					carInsurance.setCarEngineNumber(c.getEngineNumber());
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

	public CarInsuranceService getCarInsuranceService() {
		return carInsuranceService;
	}

	public void setCarInsuranceService(CarInsuranceService carInsuranceService) {
		this.carInsuranceService = carInsuranceService;
	}
	public CarInsurance getCarInsurance() {
		return carInsurance;
	}
	public void setCarInsurance(CarInsurance carInsurance) {
		this.carInsurance = carInsurance;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	

	
}
