/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersBillServiceImpl.java creation date: [2015年6月11日 下午3:49:41] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.OrdersBillDao;
import com.dearho.cs.orders.pojo.OrdersBill;
import com.dearho.cs.orders.service.OrdersBillService;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

/**
 * @author Carsharing03
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月11日
 */
public class OrdersBillServiceImpl implements OrdersBillService {
	
	private OrdersBillDao ordersBillDao;
	

	public void setOrdersBillDao(OrdersBillDao ordersBillDao) {
		this.ordersBillDao = ordersBillDao;
	}

	@Override
	public void addBill(OrdersBill bill) {
		ordersBillDao.addBill(bill);
	}

	@Override
	public void updateBill(OrdersBill bill) {
		ordersBillDao.updateBill(bill);

	}

	@Override
	public OrdersBill queryOrdersBill(String id) {
		return ordersBillDao.queryBillById(id);
	}

	@Override
	public void deleteBill(String[] ids) {
		ordersBillDao.deleteBillByIds(ids);
	}

	@Override
	public Page<OrdersBill> queryOrdersBillPage(Page<OrdersBill> page,
			String state, String startTime, String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersBill a where 1=1");
		if(!StringUtils.isEmpty(state)){
			sb.append(" and a.state = '").append(state).append("'");
		}
		if(StringHelper.isNotEmpty(startTime)){
			Date startDate = null;
			try {
				startDate = DateUtil.parseDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.createDate >= '").append(DateUtil.getChar19DateString(startDate)).append("'");
			} catch (Exception e) {
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			Date endDate = null;
			try {
				endDate = DateUtil.parseDate(endTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.createDate < '").append(DateUtil.getChar19DateString(endDate)).append("'");
			} catch (Exception e) {
			}
		}
		sb.append(" order by a.createDate desc");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=ordersBillDao.queryOrdersPage(page, sb.toString());
		return page;
	}

}
