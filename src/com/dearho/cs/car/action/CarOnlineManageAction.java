package com.dearho.cs.car.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.SystemOperateLogService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

public class CarOnlineManageAction extends AbstractAction {
	
	
	private static final long serialVersionUID = 1L;
	
	private CarService carService;
	
	private Page<Car> page=new Page<Car>();
	
	private Car car;
	
	private CarVehicleModelService carVehicleModelService;
	
	private String reason;
	
	private SystemOperateLogService systemOperateLogService;
	
	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}
	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}
	
	public List<CarVehicleModel> getAllModel(){
		List<CarVehicleModel> carVehicleModels = carVehicleModelService.queryModelByCon(null);
		for (CarVehicleModel cm : carVehicleModels) {
			cm.setEngineTypeName(DictUtil.getCnNameByGroupCodeAndDictId("2", cm.getEngineType()));
		}
		return carVehicleModels;
	}
	

	public CarOnlineManageAction() {
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
		return SUCCESS;
	}
	
	public String search(){
		page=carService.queryCarByPage(page, car);
		if(page != null && page.getResults().size() > 0){
			List<Car> cars = page.getResults();
			for (Car car : cars) {
				Dict dict = DictUtil.getDictById(car.getBizState());
				car.setBizStateCode(dict.getCode());
			}
		}
		return SUCCESS;
	}
	
	public String carOnline(){
		if(car == null || StringHelper.isEmpty(car.getId())){
			result = Ajax.JSONResult(1, "上线失败，找不到对应车辆");
			return SUCCESS;
		}
		car = carService.queryCarById(car.getId());
		if(car == null){
			result = Ajax.JSONResult(1, "上线失败，找不到对应车辆");
			return SUCCESS;
		}
		SysOperateLogRecord log = new SysOperateLogRecord();
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("user");
		Date date = new Date();
		log.setOperatorId(user.getId());
		log.setOperatorName(user.getName());
		log.setOperateDate(date);
		log.setOperateRemark(SystemOperateLogUtil.UPDATE_OPERATION);
		log.setModelName("上线");
		log.setRecordId(car.getId());
		log.setOperateContent("将"+car.getVehiclePlateId()+"上线，下线原因："+reason);

		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
		
		Dict dict = DictUtil.getDictByCodes("carBizState", "0");
		car.setBizState(dict.getId());
		carService.updateCar(car);
		result = Ajax.JSONResult(0, "");
		return SUCCESS;
	}
	
	public String carUnOnline(){
		if(car == null || StringHelper.isEmpty(car.getId())){
			result = Ajax.JSONResult(1, "下线失败，找不到对应车辆");
			return SUCCESS;
		}
		car = carService.queryCarById(car.getId());
		if(car == null){
			result = Ajax.JSONResult(1, "下线失败，找不到对应车辆");
			return SUCCESS;
		}
		if(StringHelper.isEmpty(reason)){
			result = Ajax.JSONResult(1, "请填写下线原因");
			return SUCCESS;
		}
		Dict wzjdict = DictUtil.getDictByCodes("carBizState", "0");
		Dict zjzdict = DictUtil.getDictByCodes("carBizState", "1");
		SysOperateLogRecord log = new SysOperateLogRecord();
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("user");
		Date date = new Date();
		log.setOperatorId(user.getId());
		log.setOperatorName(user.getName());
		log.setOperateDate(date);
		log.setOperateRemark(SystemOperateLogUtil.UPDATE_OPERATION);
		log.setModelName("下线");
		log.setRecordId(car.getId());
		log.setOperateContent("将"+car.getVehiclePlateId()+"下线，下线原因："+reason);

		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
		if(car.getBizState().equals(wzjdict.getId())){
			Dict tzsydict = DictUtil.getDictByCodes("carBizState", "3");
			car.setBizState(tzsydict.getId());
			carService.updateCar(car);
			result = Ajax.JSONResult(0, "下线成功");
			return SUCCESS;
		}
		else if(car.getBizState().equals(zjzdict.getId())){
			Dict yxxdict = DictUtil.getDictByCodes("carBizState", "6");
			car.setBizState(yxxdict.getId());
			carService.updateCar(car);
			result = Ajax.JSONResult(0, "车辆在使用中，暂时改为预下线");
			return SUCCESS;
		}
		result = Ajax.JSONResult(1, "下线失败，车辆暂时不能下线");
		return SUCCESS;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public SystemOperateLogService getSystemOperateLogService() {
		return systemOperateLogService;
	}
	public void setSystemOperateLogService(
			SystemOperateLogService systemOperateLogService) {
		this.systemOperateLogService = systemOperateLogService;
	}
}
