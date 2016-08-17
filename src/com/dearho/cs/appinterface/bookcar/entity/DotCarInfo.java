package com.dearho.cs.appinterface.bookcar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DotCarInfo implements Serializable{

	private static final long serialVersionUID = 4915839192941885784L;

	private String carId;
	private String carImg;
	private String vehiclePlateId;
	private String brandModel;
	private String carType;
	private List<StrtgInfo> strtgInfos;
	private Double km;
	private Double SOC;
	
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarImg() {
		return carImg;
	}
	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}
	public String getVehiclePlateId() {
		return vehiclePlateId;
	}
	public void setVehiclePlateId(String vehiclePlateId) {
		this.vehiclePlateId = vehiclePlateId;
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
	public List<StrtgInfo> getStrtgInfos() {
		return strtgInfos;
	}
	public void setStrtgInfos(List<StrtgInfo> strtgInfos) {
		this.strtgInfos = strtgInfos;
	}
	public Double getKm() {
		return km;
	}
	public void setKm(Double km) {
		this.km = km;
	}
	public Double getSOC() {
		return SOC;
	}
	public void setSOC(Double sOC) {
		SOC = sOC;
	}
	
	public DotCarInfo() {
		strtgInfos = new ArrayList<StrtgInfo>();
	}
	
}
