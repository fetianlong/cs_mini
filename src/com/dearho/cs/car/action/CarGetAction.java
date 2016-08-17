package com.dearho.cs.car.action;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;

public class CarGetAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private CarService carService;
	private CarVehicleModelService carVehicleModelService;
	private String id;
	private Car car;
	private String state;
	private List<CarVehicleModel> carVehicleModels;
	
	private CarDotBindingService carDotBindingService;
	private BranchDotService branchDotService;
	private String belongDotId;
	private String belongDotName;
	
	
	private String carfoor;
	
	private String carnumber;
	
	
	public String getCarfoor() {
		return carfoor;
	}

	public void setCarfoor(String carfoor) {
		this.carfoor = carfoor;
	}

	public String getCarnumber() {
		return carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CarGetAction() {
		super();
		car=new Car();
	}

	public List<CarVehicleModel> getCarVehicleModels() {
		return carVehicleModels;
	}

	public void setCarVehicleModels(List<CarVehicleModel> carVehicleModels) {
		this.carVehicleModels = carVehicleModels;
	}

	@Override
	public String process() {
		carVehicleModels=carVehicleModelService.queryModelByCon(null);
		if(StringHelper.isNotEmpty(id)){
			car=carService.queryCarById(id);
			if(null==car){
				car=new Car();
			}
			else{
				CarDotBinding binding = carDotBindingService.getBindingByCarId(car.getId(), 1);
				if(binding != null){
					BranchDot dot = branchDotService.getBranchDotById(binding.getDotId());
					if(dot != null){
						belongDotId = dot.getId();
						belongDotName = dot.getName();
						carfoor = binding.getFloorNo();
						carnumber = binding.getParkingNo();
					}
				}
				
			}
		}
		return state;
	}

	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}

	public void setCarDotBindingService(CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}

	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public String getBelongDotId() {
		return belongDotId;
	}

	public void setBelongDotId(String belongDotId) {
		this.belongDotId = belongDotId;
	}

	public String getBelongDotName() {
		return belongDotName;
	}

	public void setBelongDotName(String belongDotName) {
		this.belongDotName = belongDotName;
	}

}
