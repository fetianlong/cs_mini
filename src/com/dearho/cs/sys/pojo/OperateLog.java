package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.car.pojo.Car;

public class OperateLog implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String modular;
	private String type;
	private String user;
	private String userId;
	private String dataState;
	private String carId;
	private Date ts;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModular() {
		return modular;
	}
	public void setModular(String modular) {
		this.modular = modular;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String carDifferentData( Car befor,Car after){
		
		return null;
	}
	
	
}
