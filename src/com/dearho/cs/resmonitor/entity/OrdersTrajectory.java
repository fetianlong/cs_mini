package com.dearho.cs.resmonitor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.resmonitor.pojo.CarLocation;

/**
 * 订单轨迹
 * @author GaoYunpeng
 *
 */
public class OrdersTrajectory implements Serializable{

	private static final long serialVersionUID = -522826439376911940L;

	private List<CarLocation> carLocations = new ArrayList<CarLocation>();
	private Orders orders;
	
	
	public List<CarLocation> getCarLocations() {
		return carLocations;
	}
	public void setCarLocations(List<CarLocation> carLocations) {
		this.carLocations = carLocations;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
