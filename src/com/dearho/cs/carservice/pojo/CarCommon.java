package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

public class CarCommon implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//会员id
	private String subscriberId;
	//订单id
	private String orderId;
	//汽车id
	private String carId;
	//是否顺利
	private Integer isOk;
	//车辆外观
	private String carFace;
	//车内卫生
	private String carClean;
	//图片
	private String carImg;
	//创建时间
	private Date ts;
	//会员名字
	private String subscriberName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Integer getIsOk() {
		return isOk;
	}
	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}
	public String getCarFace() {
		return carFace;
	}
	public void setCarFace(String carFace) {
		this.carFace = carFace;
	}
	public String getCarClean() {
		return carClean;
	}
	public void setCarClean(String carClean) {
		this.carClean = carClean;
	}
	public String getCarImg() {
		return carImg;
	}
	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	 
	
}
