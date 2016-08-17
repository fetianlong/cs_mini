package com.dearho.cs.place.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.charge.service.ChargeStationService;
import com.dearho.cs.orders.pojo.BookCarInfoEntity;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;

public class DotInfoSearchAction extends AbstractAction{

	private static final long serialVersionUID = 5964998817405558950L;
	private BranchDotService branchDotService;
	private ChargeStationService chargeStationService;
	private CarDotBindingService carDotBindingService;
	private CarService carService;
	private CarRealtimeStateService carRealtimeStateService;
	
	@Override
	public String process() {
		List<BranchDot> branchDots = branchDotService.searchBranchDot(1);
		if(branchDots != null && branchDots.size() > 0){
			for (BranchDot branchDot : branchDots) {
				List<CarDotBinding> bindings = carDotBindingService.searchBindingByDotId(branchDot.getId(), 1);
				if(bindings != null && bindings.size() > 0){
					List<BookCarInfoEntity> entitys = new ArrayList<BookCarInfoEntity>();
					for (CarDotBinding carDotBinding : bindings) {
						Car car = carService.queryCarById(carDotBinding.getCarId());
						BookCarInfoEntity entity = new BookCarInfoEntity();
						entity.setCar(car);
						CarRealtimeState rtstate = carRealtimeStateService.getCarRealTimeState(car.getId());
						entity.setSOC(rtstate.getSOC());
						entitys.add(entity);
					}
					branchDot.setCarInfos(entitys);
				}
				
			}
		}
		List<ChargeStation> chargingStations = chargeStationService.searchChargeStationByCode(null);
		Map<String, List> resMap = new HashMap<String, List>();
		resMap.put("dots", branchDots);
		resMap.put("chargestations", chargingStations);
		result = Ajax.JSONResult(0, "", resMap);
		return SUCCESS;
	}

	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public ChargeStationService getChargeStationService() {
		return chargeStationService;
	}

	public void setChargeStationService(ChargeStationService chargeStationService) {
		this.chargeStationService = chargeStationService;
	}

	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}

	public void setCarDotBindingService(CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}

	public void setCarRealtimeStateService(CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}

	
}
