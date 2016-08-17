package com.dearho.cs.place.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dearho.cs.orders.pojo.BookCarInfoEntity;

/**
 * @author GaoYunpeng
 * @Description 网点
 * @version 1.0 2015年5月27日 下午4:35:20
 */
public class BranchDot implements Serializable{

	private static final long serialVersionUID = -1869121092836417290L;

	private String id;
	private String code;
	private String areaId;
	private String name;
	private String address;
	private Double lat;
	private Double lng;
	private Integer isActive;
	private String creatorId;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	private String returnbackDot;
	private String returnbackDotName;
	
	private List<BookCarInfoEntity> carInfos;
	private Integer carCount;
	
	private Integer totalParkingPlace;
	
	private int quickCount; //快充数量
	private int slowCount;  //慢充数量
	
	private int notWorkingCount;  //空闲数量
	
	private Integer remainderParkingPlace;//剩余车位
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
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
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
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
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getReturnbackDot() {
		return returnbackDot;
	}
	public void setReturnbackDot(String returnbackDot) {
		this.returnbackDot = returnbackDot;
	}
	public String getReturnbackDotName() {
		return returnbackDotName;
	}
	public void setReturnbackDotName(String returnbackDotName) {
		this.returnbackDotName = returnbackDotName;
	}
	public List<BookCarInfoEntity> getCarInfos() {
		return carInfos;
	}
	public void setCarInfos(List<BookCarInfoEntity> carInfos) {
		this.carInfos = carInfos;
	}
	public Integer getCarCount() {
		return carCount;
	}
	public void setCarCount(Integer carCount) {
		this.carCount = carCount;
	}
	public Integer getTotalParkingPlace() {
		return totalParkingPlace;
	}
	public void setTotalParkingPlace(Integer totalParkingPlace) {
		this.totalParkingPlace = totalParkingPlace;
	}
	public int getQuickCount() {
		return quickCount;
	}
	public void setQuickCount(int quickCount) {
		this.quickCount = quickCount;
	}
	public int getSlowCount() {
		return slowCount;
	}
	public void setSlowCount(int slowCount) {
		this.slowCount = slowCount;
	}
	public int getNotWorkingCount() {
		return notWorkingCount;
	}
	public void setNotWorkingCount(int notWorkingCount) {
		this.notWorkingCount = notWorkingCount;
	}
	public Integer getRemainderParkingPlace() {
		return remainderParkingPlace;
	}
	public void setRemainderParkingPlace(Integer remainderParkingPlace) {
		this.remainderParkingPlace = remainderParkingPlace;
	}
	
}
