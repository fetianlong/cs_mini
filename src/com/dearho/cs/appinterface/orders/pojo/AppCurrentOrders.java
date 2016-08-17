package com.dearho.cs.appinterface.orders.pojo;

import java.math.BigDecimal;
import java.util.List;

import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;

/**
 * APP当前订单
 * @author wangjing
 *
 */
public class AppCurrentOrders {

	private Orders orders;
	
	private String ordersId;
	
	private BigDecimal currentFee;
	
	private List<OrdersDetail> ordersDetail;
	
	private Integer soc;
	
	private Integer km;
	
	private Double longitude;//车辆当前经度
	
	private Double latitude;//车辆当前纬度
	
	public List<OrdersDetail> getOrdersDetail() {
		return ordersDetail;
	}

	public void setOrdersDetail(List<OrdersDetail> ordersDetail) {
		this.ordersDetail = ordersDetail;
	}

	public Integer getSoc() {
		return soc;
	}

	public void setSoc(Integer soc) {
		this.soc = soc;
	}

	public Integer getKm() {
		return km;
	}

	public void setKm(Integer km) {
		this.km = km;
	}

	public BigDecimal getCurrentFee() {
		return currentFee;
	}

	public void setCurrentFee(BigDecimal currentFee) {
		this.currentFee = currentFee;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
