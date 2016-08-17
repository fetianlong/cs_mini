package com.dearho.cs.carservice.action.repair;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.carservice.service.CarRepairService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.NumberUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class CarRepairAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarRepairService carRepairService;
	
	private CarService carService;
	
	private CarRepair carRepair;
	
	public CarRepairAddAction(){
		super();
		carRepair = new CarRepair();
	}
	
	public String process(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				carRepair.setCreatorId(user.getId());
			}
			carRepair.setIsDiscard(0);
			carRepair.setCreateTime(new Date());
			carRepair.setTs(new Date());
			
			List<CarRepair> list = carRepairService.searchCarRepairCurrentDate();
			Dict dict = DictUtil.getDictByCodes("carManageHeadCode", "carRepair");
			String headCode = NumberUtil.createFormNo(dict.getCnName(), list.size());
			carRepair.setCode(headCode);
			
			carRepairService.addCarRepair(carRepair);
			
			//更新该车辆的状态
			Car car = carService.queryCarById(carRepair.getCarId());
			car.setBizState(DictUtil.getDictByCodes("carBizState" ,"2").getId());
			carService.updateCar(car);
			
			//记录日志
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("编号", carRepair.getCode());
			SystemOperateLogUtil.sysAddOperateLog(carRepair.getId(), user, "车辆维修管理", contentMap);
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public CarRepairService getCarRepairService() {
		return carRepairService;
	}

	public void setCarRepairService(CarRepairService carRepairService) {
		this.carRepairService = carRepairService;
	}

	public CarRepair getCarRepair() {
		return carRepair;
	}

	public void setCarRepair(CarRepair carRepair) {
		this.carRepair = carRepair;
	}
	
	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}



}
