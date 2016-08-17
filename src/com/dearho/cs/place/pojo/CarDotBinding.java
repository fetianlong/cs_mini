package com.dearho.cs.place.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆网点绑定表
 * @author GaoYunpeng
 *
 */
public class CarDotBinding implements Serializable{

	private static final long serialVersionUID = -638232925015680281L;

	private String id;
	private String carId;
	private String dotId;
	private String floorNo;
	private String parkingNo;
	private Integer isUsed;
	private Date createTime;
	private String creator;
	private Date updateTime;
	
	
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
	public String getDotId() {
		return dotId;
	}
	public void setDotId(String dotId) {
		this.dotId = dotId;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getParkingNo() {
		return parkingNo;
	}
	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
