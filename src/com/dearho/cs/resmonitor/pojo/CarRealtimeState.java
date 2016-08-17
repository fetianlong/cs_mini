package com.dearho.cs.resmonitor.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.place.pojo.BranchDot;

public class CarRealtimeState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2028380143313617119L;

	private String id;
	private Double speed;             
	private Double lat;
	private Double lng;
	private Date trackTime;
	private Double totalVoltage;  //总电压
	private Double totalCurrent;  //总电流
	private Double SOC;  //总电量
	private Double currentMileage; //当前里程
	private String currentGear;  //当前档位
	private Double throttleTrip; //油门里程值
	private Double brakeTrip;   //制动里程值
	private Integer motorSpeed;  //电机转数
	private Double motorTemperature; //电机温度
	private Double motorCurrent;  //电机电流
	private Date OBDTime;   //OBD上传时间
	private Integer state;    //车身状态         1正在充电  2行驶中  3熄火 
	private Date stateTime; //车身状态更改时间
//	private Integer bizState;  //运营业务状态    1租赁使用中    2未租赁   3预定中   4下线 
//	private Date bizStateTime;  //运营业务状态更改时间
	private Date ts;  //更新时间
	
	private Double batteryPower; //蓄电池电量
	private Double lifeMileage;  //续航里程
	
	private Double outTimperature;   //环境温度
	private String corneringLampFlag; //转向灯信号
	
	
	private Car car;//车辆
	
	
	private BranchDot dot=new BranchDot();
	
	public BranchDot getDot() {
		return dot;
	}
	public void setDot(BranchDot dot) {
		this.dot = dot;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getStateTime() {
		return stateTime;
	}
	public void setStateTime(Date stateTime) {
		this.stateTime = stateTime;
	}
//	public Integer getBizState() {
//		return bizState;
//	}
//	public void setBizState(Integer bizState) {
//		this.bizState = bizState;
//	}
//	public Date getBizStateTime() {
//		return bizStateTime;
//	}
//	public void setBizStateTime(Date bizStateTime) {
//		this.bizStateTime = bizStateTime;
//	}
	public Double getBatteryPower() {
		return batteryPower;
	}
	public void setBatteryPower(Double batteryPower) {
		this.batteryPower = batteryPower;
	}
	public Double getLifeMileage() {
		return lifeMileage;
	}
	public void setLifeMileage(Double lifeMileage) {
		this.lifeMileage = lifeMileage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
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
	public Date getTrackTime() {
		return trackTime;
	}
	public void setTrackTime(Date trackTime) {
		this.trackTime = trackTime;
	}
	public Double getTotalVoltage() {
		return totalVoltage;
	}
	public void setTotalVoltage(Double totalVoltage) {
		this.totalVoltage = totalVoltage;
	}
	public Double getTotalCurrent() {
		return totalCurrent;
	}
	public void setTotalCurrent(Double totalCurrent) {
		this.totalCurrent = totalCurrent;
	}
	public Double getSOC() {
		return SOC;
	}
	public void setSOC(Double sOC) {
		SOC = sOC;
	}
	public Double getCurrentMileage() {
		return currentMileage;
	}
	public void setCurrentMileage(Double currentMileage) {
		this.currentMileage = currentMileage;
	}
	public String getCurrentGear() {
		return currentGear;
	}
	public void setCurrentGear(String currentGear) {
		this.currentGear = currentGear;
	}
	public Double getThrottleTrip() {
		return throttleTrip;
	}
	public void setThrottleTrip(Double throttleTrip) {
		this.throttleTrip = throttleTrip;
	}
	public Double getBrakeTrip() {
		return brakeTrip;
	}
	public void setBrakeTrip(Double brakeTrip) {
		this.brakeTrip = brakeTrip;
	}
	public Integer getMotorSpeed() {
		return motorSpeed;
	}
	public void setMotorSpeed(Integer motorSpeed) {
		this.motorSpeed = motorSpeed;
	}
	public Double getMotorTemperature() {
		return motorTemperature;
	}
	public void setMotorTemperature(Double motorTemperature) {
		this.motorTemperature = motorTemperature;
	}
	public Double getMotorCurrent() {
		return motorCurrent;
	}
	public void setMotorCurrent(Double motorCurrent) {
		this.motorCurrent = motorCurrent;
	}
	public Date getOBDTime() {
		return OBDTime;
	}
	public void setOBDTime(Date oBDTime) {
		OBDTime = oBDTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Double getOutTimperature() {
		return outTimperature;
	}
	public void setOutTimperature(Double outTimperature) {
		this.outTimperature = outTimperature;
	}
	public String getCorneringLampFlag() {
		return corneringLampFlag;
	}
	public void setCorneringLampFlag(String corneringLampFlag) {
		this.corneringLampFlag = corneringLampFlag;
	}
	
	
}
