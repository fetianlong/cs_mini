package com.dearho.cs.orders.dao;

import java.util.List;

import com.dearho.cs.orders.pojo.OrdersDetail;

/**
 * 订单详情DAO 
 * @author wangjing
 *
 */
public interface OrdersDetailDao {

	public List<OrdersDetail> getOrdersDetailsByHql(String hql);
	
	public void addOrdDetail(OrdersDetail ordersDetail);
	
	public void updateOrdDetail(OrdersDetail ordersDetail);
	
	public void deleteOrdDetail(OrdersDetail ordersDetail);

	public List<OrdersDetail> queryOrdersDetail(String hql);
}
