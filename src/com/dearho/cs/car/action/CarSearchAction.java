package com.dearho.cs.car.action;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class CarSearchAction extends AbstractAction {
	
	
	private static final long serialVersionUID = 1L;
	
	private CarService carService;
	
	private Page<Car> page=new Page<Car>();
	
	private Car car;
	
	private String state;
	
	private String query;
	
	private String parkingPatrolId;
	
	private CarVehicleModelService carVehicleModelService;
	
	private ParkingPatrolService parkingPatrolService;
	
	private String queryType;
	
	private CarDotBindingService carDotBindingService;
	private BranchDotService branchDotService;
	
	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}
	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}
	
	public List<CarVehicleModel> getAllModel(String brand){
		CarVehicleModel model = null;
		if(!StringHelper.isRealNull(brand)){
			
			if(brand.equals("brand") && car != null && car.getCarVehicleModel() != null){
				model = car.getCarVehicleModel();
			}
			else{
				model = new CarVehicleModel();
				model.setBrand(brand);
			}
			
		}
		List<CarVehicleModel> carVehicleModels = carVehicleModelService.queryModelByCon(model);
		for (CarVehicleModel cm : carVehicleModels) {
			cm.setEngineTypeName(DictUtil.getCnNameByGroupCodeAndDictId("2", cm.getEngineType()));
		}
		return carVehicleModels;
	}
	
	public String getBelongDotName(String carId){
		CarDotBinding binding = carDotBindingService.getBindingByCarId(carId, 1);
		if(binding != null){
			BranchDot dot = branchDotService.getBranchDotById(binding.getDotId());
			if(dot != null){
				return dot.getName();
			}
		}
		return "";
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}


	public CarSearchAction() {
		super();
		car=new Car();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}


	public CarService getCarService() {
		return carService;
	}


	public void setCarService(CarService carService) {
		this.carService = carService;
	}


	public Page<Car> getPage() {
		return page;
	}

	public void setPage(Page<Car> page) {
		this.page = page;
	}
	
	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	@Override
	public String process() {
		
		if(StringHelper.isNotEmpty(state)){
			if(state.equals("page")){
				if("accident".equals(query)){
					page = carService.queryCarByPage(page, car);
					return "search";
				}
				else if("repair".equals(query)){
					page = carService.queryCarToRepair(page, car);
					return "search";
				}
				else if(!StringHelper.isRealNull(parkingPatrolId)){
					ParkingPatrol parkingPatrol = parkingPatrolService.searchParkingPatrolById(parkingPatrolId);
					if(parkingPatrol != null){
						page = carService.queryCarByPageDot(page, car,parkingPatrol.getDotId());
						return "search";
					}
				}
				else{
					page = carService.queryBindingCarByPage(page, car, 0);
					return "search";
				}
			}else if(state.equals("group")){
				page=carService.queryCarByPage(page, car);
				return "group";
			}
		}
		else{
			if(StringHelper.isNotEmpty(queryType)){
				if("hasViolation".equals(queryType)){
					page=carService.queryViolationCarByPage(page, car);
				}
			}
			else{
				page=carService.queryCarByPage(page, car);
			}
		}
		return SUCCESS;
	}
	public String getParkingPatrolId() {
		return parkingPatrolId;
	}
	public void setParkingPatrolId(String parkingPatrolId) {
		this.parkingPatrolId = parkingPatrolId;
	}

	public ParkingPatrolService getParkingPatrolService() {
		return parkingPatrolService;
	}
	public void setParkingPatrolService(
			ParkingPatrolService parkingPatrolService) {
		this.parkingPatrolService = parkingPatrolService;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
}
