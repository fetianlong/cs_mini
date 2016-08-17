package com.dearho.cs.car.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

public class CarAddAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private CarService carService;
	private Car car;
	private CarRealtimeStateService carRealtimeStateService;
	private String belongDotId;
	private CarDotBindingService carDotBindingService;
	
	
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

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public CarAddAction() {
		super();
		car=new Car();
	}
	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}
	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}

	@Override
	public String process() {
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user!=null){
				car.setCreatorId(user.getId());
			}
			
			Dict d = DictUtil.getDictByCodes("carBizState", "3");
			car.setBizState(d.getId());
			car.setCreateDate(new Date());
			car.setTs(new Date());
			carService.addCar(car);
			if(!StringHelper.isEmpty(belongDotId)){
				CarDotBinding binding = new CarDotBinding();
				binding.setCarId(car.getId());
				binding.setCreateTime(new Date());
				binding.setCreator(user.getId());
				binding.setDotId(belongDotId);
				binding.setIsUsed(1);
				binding.setFloorNo(carfoor);
				binding.setParkingNo(carnumber);
				carDotBindingService.add(binding);
			}
			Dict wzlDict = DictUtil.getDictByCodes("carBizState", "0");
			if(wzlDict != null && wzlDict.getId().equals(car.getBizState())){
				CarRealtimeState crs = new CarRealtimeState();
				crs.setId(car.getId());
				crs.setTs(new Date());
				carRealtimeStateService.add(crs);
			}
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "添加成功！");
			
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("车牌号码", car.getVehiclePlateId());
			SystemOperateLogUtil.sysAddOperateLog(car.getId(), user, "车辆管理", contentMap);
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "添加失败!");
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

	public void setCarDotBindingService(CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}

}
