package com.dearho.cs.orders.pojo;

import java.io.Serializable;
import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.feestrategy.entity.StrategyCarShowInfo;
import com.dearho.cs.place.pojo.BranchDot;

public class BookCarInfoEntity implements Serializable{

	private static final long serialVersionUID = 8466289347692570201L;

	private Car car;
	private String brandModel;
	private Double fee;
	private String carType;
	private Double commentScore;
	private Double km;
	private Double SOC;
	private String carImg;
	
	private List<StrategyCarShowInfo> strategyCarShowInfos;  //策略显示信息
	
	private List<BranchDot> returnDots;
	
	private String dotId;
	private String dotName;
	private String address;
	private Double lat;
	private Double lng;
	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public String getBrandModel() {
		return brandModel;
	}
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getFee() {
		return fee;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Double getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(Double commentScore) {
		this.commentScore = commentScore;
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
	public String getCarImg() {
		return carImg;
	}
	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}
	public List<BranchDot> getReturnDots() {
		return returnDots;
	}
	public void setReturnDots(List<BranchDot> returnDots) {
		this.returnDots = returnDots;
	}
	public String getDotId() {
		return dotId;
	}
	public void setDotId(String dotId) {
		this.dotId = dotId;
	}
	public String getDotName() {
		return dotName;
	}
	public void setDotName(String dotName) {
		this.dotName = dotName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public List<StrategyCarShowInfo> getStrategyCarShowInfos() {
		return strategyCarShowInfos;
	}
	public void setStrategyCarShowInfos(List<StrategyCarShowInfo> strategyCarShowInfos) {
		this.strategyCarShowInfos = strategyCarShowInfos;
	}
	
}
