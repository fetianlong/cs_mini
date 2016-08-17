/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersDao.java creation date: [2015年5月28日 上午9:47:20] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.dao;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.sys.pojo.AdministrativeArea;

/**
 * @author lvlq
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年5月28日
 */
public interface OrdersDao {
	
	List<AdministrativeArea> queryAreaByCon(String hql);
	
	List<BranchDot> queryDotByHql(String hql);
	
	List<Car> queryCarByHql(String hql);
	
	Page<Car> queryCarPages(Page<Car> page,String hql);
	
	Page<CarRealtimeState> queryCarRealPage(Page<CarRealtimeState> page,String hql);
	
	CarRealtimeState queryCarRealStateById(String id);
	
	void addOrders(Orders orders);
	
	void updateOrders(Orders orders);
	
	Page<Orders> queryOrdersPage(Page<Orders> page,String hql);
	
	List<Orders> queryOrders(String hql);
	
	Orders queryOrdersById(String id);
	
	BranchDot queryDotById(String dotId);
	
	CarRealtimeState queryCarRealtimeState(String id);
	
	void updateCarRealTimeState(CarRealtimeState carRealtimeState);
	
	List<CarRealtimeState> queryCarRealtimeList(String hql);

	List<Orders> queryOrderByHql(String string);
	
	
	
	
}
