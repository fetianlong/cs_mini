package com.dearho.cs.resmonitor.entity;

import java.io.Serializable;

import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;


/**
 * @author GaoYunpeng
 * @Description 车辆基本信息类
 * @version 1.0 2015年4月24日 上午11:11:12
 */
public class CarInfoEntiry extends CarRealtimeState implements Serializable{
	
	public CarInfoEntiry(String id,String plateNumber,double lat,double lng,String status,String bizStatus) {
		this.id = id;
		this.plateNumber = plateNumber;
		this.lat = lat;
		this.lng = lng;
		this.status = status;
		this.bizStatus = bizStatus;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -805259379266111260L;
	
	private String id;
	/**
	 * 车牌号
	 */
	private String plateNumber;
	/**
	 * 车辆状态
	 */
	private String status;
	/**
	 * 经度
	 */
	private double lng;
	/**
	 * 纬度
	 */
	private double lat;
	
	/**
	 * 业务状态
	 */
	private String bizStatus;
	
	/**
	 * 品牌型号 
	 */
	private String brandModel;
	
	/**
	 * 动力类型
	 */
	private String engineTypeName;
	
	/**
	 * 车辆档类别
	 */
	private String gearboxType;
	
	/**
	 * 车机信息更新时间
	 */
	private String obdTime;
	
	/**
	 * 订单编号
	 */
	private String ordersCode;
	
	/**
	 * 订单用户名
	 */
	private String ordersUserName;
	
	private String ordersStartTime;
	
	private String ordersEndTime;
	
	private BranchDot ordersStartDot;
	
	private BranchDot ordersEndDot;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getBizStatus() {
		return bizStatus;
	}
	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}
	public String getBrandModel() {
		return brandModel;
	}
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}
	public String getEngineTypeName() {
		return engineTypeName;
	}
	public void setEngineTypeName(String engineTypeName) {
		this.engineTypeName = engineTypeName;
	}
	public String getGearboxType() {
		return gearboxType;
	}
	public void setGearboxType(String gearboxType) {
		this.gearboxType = gearboxType;
	}
	public String getObdTime() {
		return obdTime;
	}
	public void setObdTime(String obdTime) {
		this.obdTime = obdTime;
	}
	public String getOrdersCode() {
		return ordersCode;
	}
	public void setOrdersCode(String ordersCode) {
		this.ordersCode = ordersCode;
	}
	public String getOrdersUserName() {
		return ordersUserName;
	}
	public void setOrdersUserName(String ordersUserName) {
		this.ordersUserName = ordersUserName;
	}
	public String getOrdersStartTime() {
		return ordersStartTime;
	}
	public void setOrdersStartTime(String ordersStartTime) {
		this.ordersStartTime = ordersStartTime;
	}
	public String getOrdersEndTime() {
		return ordersEndTime;
	}
	public void setOrdersEndTime(String ordersEndTime) {
		this.ordersEndTime = ordersEndTime;
	}
	public BranchDot getOrdersStartDot() {
		return ordersStartDot;
	}
	public void setOrdersStartDot(BranchDot ordersStartDot) {
		this.ordersStartDot = ordersStartDot;
	}
	public BranchDot getOrdersEndDot() {
		return ordersEndDot;
	}
	public void setOrdersEndDot(BranchDot ordersEndDot) {
		this.ordersEndDot = ordersEndDot;
	}
	
	
}
