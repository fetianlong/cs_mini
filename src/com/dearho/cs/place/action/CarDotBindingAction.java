package com.dearho.cs.place.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.OperateLog;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.OperateLogService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

public class CarDotBindingAction extends AbstractAction{

	private static final long serialVersionUID = -1563762771071079824L;
	
	private BranchDotService branchDotService;
	
	private Page<BranchDot> page = new Page<BranchDot>();
	
	private BranchDot branchDot;
	
	private CarDotBindingService carDotBindingService;
	
	private List<BranchDot> branchDots;
	
	private String[] checkdel;
	
	private String dotId;
	
	private Car car;
	
	private String type;
	
	private String vehiclePlateId;
	
	private CarService carService;
	
	private Page<Car> carPage = new Page<Car>();
	
	private OperateLogService operateLogService;
	
	public List<BranchDot> getBranchDots(){
		branchDots = branchDotService.searchBranchDot(1);
		return branchDots;
	}
	
	public String getDotName(String id){
		if(branchDots != null && branchDots.size() > 0){
			for (BranchDot dot : branchDots) {
				if(dot.getId().equals(id)){
					return dot.getName();
				}
			}
		}
		return "";
	}
	
	
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
	
	public CarDotBindingAction() {
		super();
		branchDot = new BranchDot();
		page.setCurrentPage(1);
		page.setCountField("a.id");
		carPage.setCurrentPage(1);
		carPage.setCountField("a.id");
	}

	public String process(){
		return SUCCESS;
	}
	
	public String searchCarDotBinding() {
		try {
			if(branchDot != null && branchDot.getIsActive() == null){
				branchDot.setIsActive(1);
			}
			page = branchDotService.searchBranchDot(page, branchDot);
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					BranchDot p = (BranchDot)obj;
					List<CarDotBinding> bindings = carDotBindingService.searchBindingByDotId(p.getId(), 1);
					int unUsedCarNum = 0;
					if(bindings != null){
						for (CarDotBinding carDotBinding : bindings) {
							Car car = carService.queryCarById(carDotBinding.getCarId());
							Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
							if(ydDict.getId().equals(car.getBizState())){
								unUsedCarNum++;
							}
						}
					}
					p.setCarCount(bindings == null ? 0 : bindings.size());
					p.setRemainderParkingPlace(p.getTotalParkingPlace() - unUsedCarNum);
				}
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public String searchCars(){
		try {
			carPage = carDotBindingService.searchCars(carPage, car, dotId,type,vehiclePlateId,1);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}
	public String addCarDotBinding(){
		if(checkdel == null || checkdel.length <= 0){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "没有车辆数据！");
			return Action.ERROR;
		}
		BranchDot branchDot = branchDotService.getBranchDotById(dotId);
		List<CarDotBinding> bindings = carDotBindingService.searchBindingByDotId(dotId, 1);
		int unUsedCarNum = 0;
		if(bindings != null){
			for (CarDotBinding carDotBinding : bindings) {
				Car car = carService.queryCarById(carDotBinding.getCarId());
				Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
				if(ydDict.getId().equals(car.getBizState())){
					unUsedCarNum++;
				}
			}
		}
		
		if(branchDot == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "没有网点数据！");
			return Action.ERROR;
		}
		else if(branchDot.getTotalParkingPlace() < unUsedCarNum + checkdel.length){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "没有更多的停车位！");
			return Action.ERROR;
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("user");
		String carPlateNums = "";
		String carIds = "";
		for (String carId : checkdel) {
			CarDotBinding carDotBinding = new CarDotBinding();
			carDotBinding.setCarId(carId);
			carDotBinding.setDotId(dotId);
			carDotBinding.setCreateTime(new Date());
			carDotBinding.setIsUsed(1);
			if(user!=null){
				carDotBinding.setCreator(user.getId());
			}
			carDotBindingService.add(carDotBinding);
			Car car = carService.queryCarById(carId);
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
			car.setBizState(ydDict.getId());
			carService.updateCar(car);
			carPlateNums += car.getVehiclePlateId()+",";
			carIds += carId+",";
		}
		OperateLog operateLog = new OperateLog();
		operateLog.setName("车辆调度，向"+branchDot.getName()+"调入"+checkdel.length+"辆车");
		operateLog.setModular("车辆调度");
		operateLog.setType("车辆调度");
		operateLog.setUser(user.getName());
		operateLog.setUserId(user.getId());
		operateLog.setDataState("车牌号："+carPlateNums+"车辆id:"+carIds);
		operateLogService.addOperateLog(operateLog);
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "绑定成功！");
		return Action.SUCCESS;
		
	}
	
	public String deleteCarDotBinding(){
		if(checkdel == null || checkdel.length <= 0){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请选择车辆数据！");
			return Action.ERROR;
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("user");
		String carPlateNums = "";
		String carIds = "";
		for (String carId : checkdel) {
			CarDotBinding carDotBinding = carDotBindingService.getBindingByCarId(carId,1);
			if(carDotBinding != null){
				dotId = carDotBinding.getDotId();
				carDotBinding.setIsUsed(0);
				carDotBinding.setUpdateTime(new Date());
				carDotBindingService.update(carDotBinding);
			}
			Car car = carService.queryCarById(carId);
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "5");
			car.setBizState(ydDict.getId());
			carService.updateCar(car);
			carPlateNums += car.getVehiclePlateId()+",";
			carIds += carId+",";
		}
		BranchDot branchDot = branchDotService.getBranchDotById(dotId);
		OperateLog operateLog = new OperateLog();
		operateLog.setName("车辆调度，从"+branchDot.getName()+"调出"+checkdel.length+"辆车");
		operateLog.setModular("车辆调度");
		operateLog.setType("车辆调度");
		operateLog.setUser(user.getName());
		operateLog.setUserId(user.getId());
		operateLog.setDataState("车牌号："+carPlateNums+"车辆id:"+carIds);
		operateLogService.addOperateLog(operateLog);
		
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
		return Action.SUCCESS;
	}

	public Page<BranchDot> getPage() {
		return page;
	}

	public void setPage(Page<BranchDot> page) {
		this.page = page;
	}

	public BranchDot getBranchDot() {
		return branchDot;
	}

	public void setBranchDot(BranchDot branchDot) {
		this.branchDot = branchDot;
	}

	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}

	public void setCarDotBindingService(CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	public String getDotId() {
		return dotId;
	}

	public void setDotId(String dotId) {
		this.dotId = dotId;
	}

	public void setBranchDots(List<BranchDot> branchDots) {
		this.branchDots = branchDots;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getVehiclePlateId() {
		return vehiclePlateId;
	}
	public void setVehiclePlateId(String vehiclePlateId) {
		this.vehiclePlateId = vehiclePlateId;
	}
	public Page<Car> getCarPage() {
		return carPage;
	}
	public void setCarPage(Page<Car> carPage) {
		this.carPage = carPage;
	}

	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	
}
