package com.dearho.cs.orders.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.orders.dao.OrdersDetailDao;
import com.dearho.cs.orders.pojo.OrdersDetail;

/**
 * 订单详情DAO 实现类
 * @author wangjing
 *
 */
public class OrdersDetailDaoImpl  extends AbstractDaoSupport implements OrdersDetailDao{

	@Override
	public List<OrdersDetail> getOrdersDetailsByHql(String hql) {
		return getList(OrdersDetail.class, queryFList(hql));
	}

	@Override
	public void updateOrdDetail(OrdersDetail ordersDetail) {
		updateEntity(ordersDetail);
	}

	@Override
	public void addOrdDetail(OrdersDetail ordersDetail) {
		addEntity(ordersDetail);
	}

	@Override
	public List<OrdersDetail> queryOrdersDetail(String hql) {
		return getList(OrdersDetail.class, queryFList(hql));
	}

	@Override
	public void deleteOrdDetail(OrdersDetail ordersDetail) {
		remove(ordersDetail);
	}

}
