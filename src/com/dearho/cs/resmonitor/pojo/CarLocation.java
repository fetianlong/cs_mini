package com.dearho.cs.resmonitor.pojo;

import java.io.Serializable;
import java.util.Date;


public class CarLocation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7093592571646260697L;
	
	private Integer id;
	private String deviceNo;
	private double lat;
	private double lng;
	private double speed;
	private Date ts;
	private String tsDate;
	
	public String getTsDate() {
		return tsDate;
	}
	public void setTsDate(String tsDate) {
		this.tsDate = tsDate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}

}
