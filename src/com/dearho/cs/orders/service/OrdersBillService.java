/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersBillService.java creation date: [2015年6月11日 下午3:47:48] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.service;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.OrdersBill;

/**
 * @author Carsharing03
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月11日
 */
public interface OrdersBillService {
	void addBill(OrdersBill bill);
	
	void updateBill(OrdersBill	bill);
	
	OrdersBill queryOrdersBill(String id);
	
	void deleteBill(String[] ids);

	Page<OrdersBill> queryOrdersBillPage(Page<OrdersBill> page,
			String state, String startTime, String endTime);
}
