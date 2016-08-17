package com.dearho.cs.car.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class CarVehicleModelDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	private CarVehicleModelService carVehicleModelService;
	private CarService carService;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	
	@Override
	public String process() {
		try{
			if((checkdel == null || checkdel.length <= 0)&& id != null){
				checkdel = new String[]{id};
			}
			for (int i = 0; i < checkdel.length; i++) {
				List<Car> list=carService.queryCarListByModelId(checkdel[i]);
				if(list!=null&&list.size()>0){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "车型下有车辆 不能删除！");
					return ERROR;
				}
			}
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			
			Map<String, String> contentMap = new HashMap<String, String>();
			for (int i = 0; i < checkdel.length; i++) {
				CarVehicleModel carVehicleModel = carVehicleModelService.queryModelById(checkdel[i]);
				contentMap.put("车辆型号", carVehicleModel.getName());
			}
			SystemOperateLogUtil.sysDeleteOperateLog(user, "车型管理", contentMap);
			
			carVehicleModelService.deleteModelByIds(checkdel);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return ERROR;
		}
		return SUCCESS;
	}

}
