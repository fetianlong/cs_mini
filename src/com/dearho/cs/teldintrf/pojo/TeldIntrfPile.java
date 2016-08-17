package com.dearho.cs.teldintrf.pojo;

import java.io.Serializable;
import java.util.Date;

public class TeldIntrfPile implements Serializable{

	private static final long serialVersionUID = 4138653762018781056L;
	
	private String stationCode;  //电站编码

	private String pileCode; //终端编号
	private String pileName; //终端名称
	private String pileType;    //终端类型分七种 :( 直流  交流   直流乘用车 直流大巴车   
										//交流单相  交流三相  交流无导引)
	private String pileState;    //终端状态分八种：(离网  空闲   已插枪  已充满   暂停  充电中 涓流充   故障)
	
	private Date createTime;
	private Date updateTime;
	
	private String voltage;   //电压
	private String galvanic;   //电流
	private String power;   //功率
	private String electricPrice;  //电费单价（含税）
	private String servicePrice;   //服务费单价（含税）
	private String timeRange;   //时间段
	
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getPileCode() {
		return pileCode;
	}
	public void setPileCode(String pileCode) {
		this.pileCode = pileCode;
	}
	public String getPileName() {
		return pileName;
	}
	public void setPileName(String pileName) {
		this.pileName = pileName;
	}
	public String getPileType() {
		return pileType;
	}
	public void setPileType(String pileType) {
		this.pileType = pileType;
	}
	public String getPileState() {
		return pileState;
	}
	public void setPileState(String pileState) {
		this.pileState = pileState;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getGalvanic() {
		return galvanic;
	}
	public void setGalvanic(String galvanic) {
		this.galvanic = galvanic;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getElectricPrice() {
		return electricPrice;
	}
	public void setElectricPrice(String electricPrice) {
		this.electricPrice = electricPrice;
	}
	public String getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
