package com.dearho.cs.carservice.action.patrol.car;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.action.patrol.parking.ParkingPatrolSearchAction;
import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.CarPatrolService;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CarPatrolSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarPatrolService carPatrolService;
	
	private Page<CarPatrol> page = new Page<CarPatrol>();
	
	private CarPatrol carPatrol;
	
	private CarService carService;
	
	private BranchDotService branchDotService;
	
	private ParkingPatrolService parkingPatrolService;
	
	private String state;
	
	public CarPatrolSearchAction(){
		super();
		carPatrol = new CarPatrol();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			if(state != null && "inparking".equals(state)){
				if(carPatrol != null && StringHelper.isRealNull(carPatrol.getParkingPatrolId())){
					carPatrol.setParkingPatrolId("-1");
				}
			}
			page = carPatrolService.searchCarPatrol(page, carPatrol);
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					CarPatrol cm = (CarPatrol)obj;
					Car c = carService.queryCarById(cm.getCarId());
					if(c != null){
						cm.setPlateNumber(c.getVehiclePlateId());
					}
					
					ParkingPatrol parkingPatrol = parkingPatrolService.searchParkingPatrolById(cm.getParkingPatrolId());
					if(parkingPatrol != null){
						cm.setParkingPatrolCode(parkingPatrol.getCode());
						BranchDot dot = branchDotService.getBranchDotById(parkingPatrol.getDotId());
						if(dot != null){
							cm.setDotName(dot.getName());
							cm.setDotId(dot.getId());
						}
					}
				}
			}
			if(state != null && "inparking".equals(state)){
				return "inparking";
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public CarPatrolService getCarPatrolService() {
		return carPatrolService;
	}

	public void setCarPatrolService(CarPatrolService carPatrolService) {
		this.carPatrolService = carPatrolService;
	}

	public Page<CarPatrol> getPage() {
		return page;
	}

	public void setPage(Page<CarPatrol> page) {
		this.page = page;
	}

	public CarPatrol getCarPatrol() {
		return carPatrol;
	}

	public void setCarPatrol(CarPatrol carPatrol) {
		this.carPatrol = carPatrol;
	}

	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	public ParkingPatrolService getParkingPatrolService() {
		return parkingPatrolService;
	}
	public void setParkingPatrolService(
			ParkingPatrolService parkingPatrolService) {
		this.parkingPatrolService = parkingPatrolService;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
}
