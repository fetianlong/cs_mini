package com.dearho.cs.car.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.OperateLogService;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

public class CarUpdateAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private Car car;
	
	private CarService carService;
	
	private OperateLogService operateLogService;
	
	private String belongDotId;
	
	private CarDotBindingService carDotBindingService;
	
	private String carfoor;
	
	private String carnumber;
	
	public Car getCar() {
		return car;
	}
	

	public CarUpdateAction() {
		super();
		car=new Car();
	}


	public void setCar(Car car) {
		this.car = car;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	@Override
	public String process() {
		try{
			Car beforObj = carService.queryCarById(car.getId());
			car.setTs(new Date());
			car.setCreateDate(beforObj.getCreateDate());
			car.setCreatorId(beforObj.getCreatorId());
			car.setBizState(beforObj.getBizState());
			carService.updateCar(car);
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			
			if(!StringHelper.isEmpty(belongDotId)){
				
				CarDotBinding binding = carDotBindingService.getBindingByCarId(car.getId(), 1);
				if(null ==binding || !binding.getDotId().equals(belongDotId)){
					
					if(null != binding)
					{
						binding.setIsUsed(0);
						binding.setUpdateTime(new Date());
						carDotBindingService.update(binding);
					}
					binding = new CarDotBinding();
					binding.setCarId(car.getId());
					binding.setCreateTime(new Date());
					binding.setCreator(user.getId());
					binding.setDotId(belongDotId);
					binding.setIsUsed(1);
					binding.setFloorNo(carfoor);
					binding.setParkingNo(carnumber);
					carDotBindingService.add(binding);
				}else{
					binding.setFloorNo(carfoor);
					binding.setParkingNo(carnumber);
					binding.setUpdateTime(new Date());
					carDotBindingService.update(binding);
				}
				
			}
			
			
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "更新成功！");
			Car afterObj = carService.queryCarById(car.getId());
			SystemOperateLogUtil.sysUpdateOperateLog(beforObj, afterObj, user, "车辆管理", afterObj.getVehiclePlateId());
		}catch(Exception e){
			e.printStackTrace();
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String getBelongDotId() {
		return belongDotId;
	}
	public void setBelongDotId(String belongDotId) {
		this.belongDotId = belongDotId;
	}
	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}
	public void setCarDotBindingService(
			CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}


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

	
}
