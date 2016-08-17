package com.dearho.cs.appinterface.bookcar.entity;

import java.io.Serializable;

public class ChargeStationVo implements Serializable{

	private static final long serialVersionUID = -8372192361941566536L;

	private String name;
	private String address;
	private Integer acableNum;
	private Integer dcableNum;
	private Double lat;
	private Double lng;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAcableNum() {
		return acableNum;
	}
	public void setAcableNum(Integer acableNum) {
		this.acableNum = acableNum;
	}
	public Integer getDcableNum() {
		return dcableNum;
	}
	public void setDcableNum(Integer dcableNum) {
		this.dcableNum = dcableNum;
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
	
	
}
