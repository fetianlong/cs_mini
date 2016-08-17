package com.dearho.cs.device.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class DeviceBindingAddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private DeviceBindingService deviceBindingService;
	private CarService carService;
	
	private DeviceService deviceService;
	
	private DeviceBinding deviceBinding;
	
	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}


	public DeviceBinding getDeviceBinding() {
		return deviceBinding;
	}


	public void setDeviceBinding(DeviceBinding deviceBinding) {
		this.deviceBinding = deviceBinding;
	}

	public DeviceBindingAddAction() {
		super();
		deviceBinding=new DeviceBinding();
	}

	@Override
	public String process() {
		try{
			Car car=carService.queryCarById(deviceBinding.getCarId());
			if(car==null){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"车辆不存在");
				return ERROR;
			}
			Device device=deviceService.queryCarSetById(deviceBinding.getDeviceId());
			if(device==null){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"车机不存在！");
				return ERROR;
			}
			List<DeviceBinding> list=deviceBindingService.queryBindByCarId(deviceBinding.getCarId(),1);
			if(list!=null&&list.size()>0){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"该车辆已绑定车机 请先解绑 在绑定 ！");
				return ERROR;
			}
			List<DeviceBinding> devicelist=deviceBindingService.queryBindByDeviceId(deviceBinding.getDeviceId(),1);
			if(devicelist!=null&&devicelist.size()>0){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"该车机已绑定车辆 请先解绑 在绑定 ！");
				return ERROR;
			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user!=null){
				deviceBinding.setBindUserId(user.getId());
			}
			deviceBinding.setDeviceNo(device.getSetNo());//车机编号
			deviceBinding.setBindDate(new Date());
			deviceBinding.setCarPlateNo(car.getVehiclePlateId());
			deviceBindingService.addCarBind(deviceBinding);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"添加成功");
			
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("车机编号", device.getSetNo());
			contentMap.put("车牌号码", car.getVehiclePlateId());
			SystemOperateLogUtil.sysAddOperateLog(deviceBinding.getId(), user, "车机绑定管理", contentMap);
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"添加失败");
			return ERROR;
		}
		
		return SUCCESS;
	}

}
