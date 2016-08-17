package com.dearho.cs.orders.dao;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.OrdersComment;

public interface OrdersCommentDao {

	Page<OrdersComment> queryCommentPage(Page<OrdersComment> page,String hql);

	OrdersComment getCommentById(String id);

	void update(OrdersComment comment);
	
	void addComment(OrdersComment comment);

}
