/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersBillDao.java creation date: [2015年6月11日 下午3:30:57] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.dao;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.OrdersBill;

/**
 * @author Carsharing03
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月11日
 */
public interface OrdersBillDao {
	
	void addBill(OrdersBill bill);
	
	void updateBill(OrdersBill bill);
	
	void deleteBillByIds(String[] ids);
	
	OrdersBill queryBillById(String id);

	Page<OrdersBill> queryOrdersPage(Page<OrdersBill> page, String string);

}
