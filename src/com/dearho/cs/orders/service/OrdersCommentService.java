package com.dearho.cs.orders.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.OrdersComment;
import com.dearho.cs.subscriber.pojo.Subscriber;

public interface OrdersCommentService {

	/**
	 * 
	 * @Title queryCommentPage
	 * @Description 按条件分页查询评价信息
	 * @param page
	 * @param comment
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws
	 */
	Page<OrdersComment> queryCommentPage(Page<OrdersComment> page,OrdersComment comment,String startTime,String endTime);

	/**
	 * 
	 * @Title getCommentById
	 * @Description 根据ID获取评论
	 * @param id
	 * @return
	 * @throws
	 */
	OrdersComment getCommentById(String id);

	/**
	 * 删除对象
	 * @Title getCommentById
	 * @Description 删除对象
	 * @param id
	 * @return
	 * @throws
	 */
	void deleteComment(String[] commentIds);

	/**
	* 更新
	* @param comment
	* @return void
	*/
	void update(OrdersComment comment);

	/**
	 * 新增
	 * @param comment
	 */
	void addComment(OrdersComment comment);

	double getAvgScore(String carId);

	List<OrdersComment> queryComment(String type, String carId,
			String lastCommentId);

	Integer getCommentCount(String carId);
	
	
}
