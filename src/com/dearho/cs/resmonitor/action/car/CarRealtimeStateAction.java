package com.dearho.cs.resmonitor.action.car;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.resmonitor.entity.CarInfoEntiry;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.GpsTransUtil;
import com.dearho.cs.util.Point;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;

public class CarRealtimeStateAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2542785804293770661L;
	
	private String selectType;
	private String selectValue;
	
	private CarRealtimeStateService carRealtimeStateService;
	
	private List<CarInfoEntiry> carInfoEntiries;
	
	private DeviceBindingService deviceBindingService;
	
	private CarService carService;
	
	@Override
	public String process() {
		List<String> ids = new ArrayList<String>();
		boolean searchFlag = true;
		if(StringHelper.isNotEmpty(selectValue)){
			if("plateNumber".equals(selectType)){
				Car car = carService.queryCarByVehiclePlateId(selectValue.trim());
				if(car != null){
					ids.add(car.getId());
				}
				else{
					searchFlag = false;
				}
			}
			else if("deviceNo".equals(selectType)){
				List<DeviceBinding> deviceBindings = deviceBindingService.queryBindByDeviceNo(selectValue.trim(), 1);
				if(deviceBindings != null && deviceBindings.size() > 0){
					for (DeviceBinding deviceBinding : deviceBindings) {
						ids.add(deviceBinding.getCarId());
					}
				}
				else{
					searchFlag = false;
				}
			}
		}
		if(!searchFlag){
			result = Ajax.JSONResult(0, "", carInfoEntiries);
			return Action.SUCCESS;
		}
		carInfoEntiries = carRealtimeStateService.getCarInfo(ids);
//		carInfoEntiries = new ArrayList<CarInfoEntiry>();
		
//		CarInfoEntiry carInfo = new CarInfoEntiry("BJQU0X73","北汽E150",39.811142,116.507736,"不在充电");
//		carInfoEntiries.add(carInfo);
//		
//		CarInfoEntiry carInfo2 = new CarInfoEntiry("BJQG9G31","北汽E150",39.822242,116.407736,"不在充电");
//		carInfoEntiries.add(carInfo2);
		for (CarInfoEntiry entiry : carInfoEntiries) {
			Point res = GpsTransUtil.transPoint(entiry.getLat(), entiry.getLng());
			entiry.setLat(res.getLat());
			entiry.setLng(res.getLng());
		}
		result = Ajax.JSONResult(0, "", carInfoEntiries);
		//carRealtimeStateService.getCarInfo(null)
		return Action.SUCCESS;
	}




	public String getSelectType() {
		return selectType;
	}




	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}




	public String getSelectValue() {
		return selectValue;
	}




	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}




	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}


	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}




	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}




	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}




	public CarService getCarService() {
		return carService;
	}




	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	
}
