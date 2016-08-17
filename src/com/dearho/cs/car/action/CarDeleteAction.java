package com.dearho.cs.car.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class CarDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private CarService carService;
	private DeviceBindingService deviceBindingService;
	
	private String[] checkdel;
	
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
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

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	@Override
	public String process() {
		try{
			if(checkdel!=null&&checkdel.length>0){
				for (int i = 0; i < checkdel.length; i++) {
					List<DeviceBinding> list=deviceBindingService.queryBindByCarId(checkdel[i],null);
					if(list!=null&&list.size()>0){
						result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败,该车辆有车机绑定信息");
						return ERROR;
					}
				}
				HttpSession session = ServletActionContext.getRequest().getSession();
				User user=(User) session.getAttribute("user");
				Map<String, String> contentMap = new HashMap<String, String>();
				for (int i = 0; i < checkdel.length; i++) {
					Car car = carService.queryCarById(checkdel[i]);
					contentMap.put("车牌号码", car.getVehiclePlateId());
				}
				SystemOperateLogUtil.sysDeleteOperateLog(user, "车辆管理", contentMap);
				
				carService.deleteCarByIds(checkdel);
			}else if(id != null){
				List<DeviceBinding> list=deviceBindingService.queryBindByCarId(id,null);
				if(list!=null&&list.size()>0){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败,该车辆有车机绑定信息");
					return ERROR;
				}
				
				String[] ids = new String[]{id};
				carService.deleteCarByIds(ids);
			}
			
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功");
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败");
			return ERROR;
		}
		return SUCCESS;
	}

}
