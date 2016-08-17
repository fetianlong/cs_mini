package com.dearho.cs.carservice.action.patrol.car;



import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.CarPatrolService;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class CarPatrolGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CarPatrolService carPatrolService;
	
	private CarPatrol carPatrol;
	
	private CarService carService;
	
	private BranchDotService branchDotService;
	
	private ParkingPatrolService parkingPatrolService;
	
	private String parkingPatrolId;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			carPatrol = new CarPatrol();
			if(!StringHelper.isRealNull(parkingPatrolId)){
				carPatrol.setParkingPatrolId(parkingPatrolId);
				ParkingPatrol parkingPatrol = parkingPatrolService.searchParkingPatrolById(parkingPatrolId);
				carPatrol.setParkingPatrolCode(parkingPatrol.getCode());
			}
		}else{
			CarPatrol eEntity = carPatrolService.searchCarPatrolById(id);
			if (eEntity == null){
				carPatrol = new CarPatrol();
				if(!StringHelper.isRealNull(parkingPatrolId)){
					carPatrol.setParkingPatrolId(parkingPatrolId);
					ParkingPatrol parkingPatrol = parkingPatrolService.searchParkingPatrolById(parkingPatrolId);
					carPatrol.setParkingPatrolCode(parkingPatrol.getCode());
				}
			}
			else {
				carPatrol = eEntity;
				Car c = carService.queryCarById(carPatrol.getCarId());
				if(c != null){
					carPatrol.setPlateNumber(c.getVehiclePlateId());
				}
				ParkingPatrol parkingPatrol = parkingPatrolService.searchParkingPatrolById(carPatrol.getParkingPatrolId());
				if(parkingPatrol != null){
					carPatrol.setParkingPatrolCode(parkingPatrol.getCode());
					BranchDot dot = branchDotService.getBranchDotById(parkingPatrol.getDotId());
					if(dot != null){
						carPatrol.setDotName(dot.getName());
						carPatrol.setDotId(dot.getId());
					}
				}
			}
			
		}
		return SUCCESS;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarPatrolService getCarPatrolService() {
		return carPatrolService;
	}

	public void setCarPatrolService(CarPatrolService carPatrolService) {
		this.carPatrolService = carPatrolService;
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
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
	public void setParkingPatrolId(String parkingPatrolId) {
		this.parkingPatrolId = parkingPatrolId;
	}
	public String getParkingPatrolId() {
		return parkingPatrolId;
	}
	
}
