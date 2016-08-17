package com.dearho.cs.appinterface.bookcar.entity;

import java.io.Serializable;

import com.dearho.cs.place.pojo.BranchDot;

public class BranchDotVo implements Serializable{

	private static final long serialVersionUID = 3871007858931723624L;

	private String id;
	private String name;
	private String address;
	private Double lat;
	private Double lng;
	private Integer carCount;
	private Integer totalParkingCount;
	
	
	public static BranchDotVo toVo(BranchDot dot) {
		BranchDotVo vo = new BranchDotVo();
		vo.setId(dot.getId());
		vo.setName(dot.getName());
		vo.setAddress(dot.getAddress());
		vo.setLat(dot.getLat());
		vo.setLng(dot.getLng());
		vo.setCarCount(dot.getCarCount());
		return vo;
	}
	
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
	public Integer getCarCount() {
		return carCount;
	}
	public void setCarCount(Integer carCount) {
		this.carCount = carCount;
	}

	public Integer getTotalParkingCount() {
		return totalParkingCount;
	}

	public void setTotalParkingCount(Integer totalParkingCount) {
		this.totalParkingCount = totalParkingCount;
	}
	
	
	

}
