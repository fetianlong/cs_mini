package com.dearho.cs.appinterface.orders.pojo;

import java.util.List;

import com.dearho.cs.feestrategy.entity.StrategyCarShowInfo;
import com.dearho.cs.feestrategy.pojo.StrategyBase;

/**
 * 车辆预定页面
 * @author wangjing
 *
 */
public class AppBookingInfo {

	private String carId;
	
	private String vehiclePlateId;
	
	private String carImg;
	
	private String brandModel;
	
	private String carType;
	
	private String dotName;
	
	private Double km;
	
	private Double soc;
	
	private List<StrategyCarShowInfo> strategyCarShowInfos;
	
	private StrategyBase shizuStrategyBase;
	
	private StrategyBase rizuStrategyBase;

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getVehiclePlateId() {
		return vehiclePlateId;
	}

	public void setVehiclePlateId(String vehiclePlateId) {
		this.vehiclePlateId = vehiclePlateId;
	}

	public String getCarImg() {
		return carImg;
	}

	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getDotName() {
		return dotName;
	}

	public void setDotName(String dotName) {
		this.dotName = dotName;
	}


	public List<StrategyCarShowInfo> getStrategyCarShowInfos() {
		return strategyCarShowInfos;
	}

	public void setStrategyCarShowInfos(
			List<StrategyCarShowInfo> strategyCarShowInfos) {
		this.strategyCarShowInfos = strategyCarShowInfos;
	}

	public StrategyBase getShizuStrategyBase() {
		return shizuStrategyBase;
	}

	public void setShizuStrategyBase(StrategyBase shizuStrategyBase) {
		this.shizuStrategyBase = shizuStrategyBase;
	}

	public StrategyBase getRizuStrategyBase() {
		return rizuStrategyBase;
	}

	public void setRizuStrategyBase(StrategyBase rizuStrategyBase) {
		this.rizuStrategyBase = rizuStrategyBase;
	}

	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}

	public Double getSoc() {
		return soc;
	}

	public void setSoc(Double soc) {
		this.soc = soc;
	}
	
	
}
