package com.dearho.cs.appinterface.orders.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;

/**
 * APP 订单详情实体对象
 * @author wangjing
 *
 */
public class AppOrdersDetail {

	private String stateName;
	
	private String ordersNo;
	
	private String plateNumber;
	
	private String vehicleModelName;
	
	private Integer casesNum;
	
	private Date beginTime;
	
	private Date endTime;
	
	private String levelName;//车辆等级
	
	private BigDecimal currentFee;
	
//	private Orders orders;
	
	private List<OrdersDetail> ordersDetail;

//	public Orders getOrders() {
//		return orders;
//	}
//
//	public void setOrders(Orders orders) {
//		this.orders = orders;
//	}

	public List<OrdersDetail> getOrdersDetail() {
		return ordersDetail;
	}

	public void setOrdersDetail(List<OrdersDetail> ordersDetail) {
		this.ordersDetail = ordersDetail;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getOrdersNo() {
		return ordersNo;
	}

	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getVehicleModelName() {
		return vehicleModelName;
	}

	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}

	public Integer getCasesNum() {
		return casesNum;
	}

	public void setCasesNum(Integer casesNum) {
		this.casesNum = casesNum;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public BigDecimal getCurrentFee() {
		return currentFee;
	}

	public void setCurrentFee(BigDecimal currentFee) {
		this.currentFee = currentFee;
	}

}
