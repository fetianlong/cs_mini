package com.dearho.cs.orders.pojo;

import java.io.Serializable;
import java.util.Date;

public class ChargingLog implements Serializable{

	private static final long serialVersionUID = 9115031129341293239L;

	private String id;
	private String teldPileCode;
	private String subscriberId;
	private String ordersId;
	private Date startTime;
	private Date endTime;
	private Double elec; //充电总电量
	private Double fee;  //总费用
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTeldPileCode() {
		return teldPileCode;
	}
	public void setTeldPileCode(String teldPileCode) {
		this.teldPileCode = teldPileCode;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Double getElec() {
		return elec;
	}
	public void setElec(Double elec) {
		this.elec = elec;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	
}
