package com.dearho.cs.device.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.sys.pojo.FieldComment;

public class DeviceBinding implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**主键*/
	private String id;
	/**车辆ID*/
	private String carId;
	/**车机Id*/
	private String deviceId;
	/**车机编码*/
	@FieldComment("车机编号") 
	private String deviceNo;
	/**绑定类型*/
	private Integer bindType;
	
	@FieldComment("绑定状态") 
	private String bindTypeName;
	
	/**绑定时间*/
	private Date bindDate;
	/**绑定人*/
	private String bindUserId;
	
	private Car car;
	
	private Device device;
	
	@FieldComment("车牌号")
	private String carPlateNo;
	

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public String getBindUserId() {
		return bindUserId;
	}

	public void setBindUserId(String bindUserId) {
		this.bindUserId = bindUserId;
	}

	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public DeviceBinding() {
		super();
	}
	public String getCarPlateNo() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		CarService carService = (CarService) webApplicationContext.getBean("carService");
		Car car = carService.queryCarById(carId);
		return car.getVehiclePlateId();
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	@Override
	public String toString() {
		return "DeviceBinding [id=" + id + ", carId=" + carId + ", deviceId="
				+ deviceId + ", deviceNo=" + deviceNo + ", bindType="
				+ bindType + ", bindDate=" + bindDate + ", bindUserId="
				+ bindUserId + ", car=" + car + ", device=" + device + "]";
	}

	public String getBindTypeName() {
		if(bindType==0){
			return "未绑定";
		}else{
			return "已绑定";
		}
	}

	public void setBindTypeName(String bindTypeName) {
		this.bindTypeName = bindTypeName;
	}


}
