/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersBillDaoImpl.java creation date: [2015年6月11日 下午3:36:11] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.dao.impl;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.OrdersBillDao;
import com.dearho.cs.orders.pojo.OrdersBill;

/**
 * @author Carsharing03
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月11日
 */
public class OrdersBillDaoImpl extends AbstractDaoSupport implements
		OrdersBillDao {

	public void addBill(OrdersBill bill) {
		addEntity(bill);
	}

	@Override
	public void updateBill(OrdersBill bill) {
		updateEntity(bill);
	}

	@Override
	public void deleteBillByIds(String[] ids) {
		final String hql="delete OrdersBill where id in (:ids)";
		deleteEntity(OrdersBill.class, hql, ids);
	}

	@Override
	public OrdersBill queryBillById(String id) {
		return get(OrdersBill.class, id);
	}

	@Override
	public Page<OrdersBill> queryOrdersPage(Page<OrdersBill> page, String hql) {
		Page<OrdersBill> resultPage=pageCache(OrdersBill.class, page, hql);
		resultPage.setResults(idToObj(OrdersBill.class, resultPage.getmResults()));
		return resultPage;
	}

}
