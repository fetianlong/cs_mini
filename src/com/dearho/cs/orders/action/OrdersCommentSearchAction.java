package com.dearho.cs.orders.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersComment;
import com.dearho.cs.orders.service.OrdersCommentService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class OrdersCommentSearchAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private OrdersCommentService ordersCommentService;
	
	private OrdersService ordersService;
	
	private SubscriberService subscriberService;
	
	private UserService userService;
	
	private OrdersComment ordersComment;
	
	private Page<OrdersComment> page=new Page<OrdersComment>();
	
	private String startTime;

	private String endTime;
	
	private String ordersno;
	
	public OrdersCommentSearchAction(){
		super();
		page.setCountField("a.id");
		page.setCurrentPage(1);
	}
	
	@Override
	public String process() {
		page=ordersCommentService.queryCommentPage(page, ordersComment,startTime,endTime);
		return SUCCESS;
	}

	/**
	 * 前台用户评论
	 * @return
	 */
	public String portalGoOrdersComment() {
		ordersno = (String) getRequest().getParameter("ordersno");
		return SUCCESS;
	}
	
	/**
	 * 更新前台用户评论
	 * @return
	 */
	public String portalUpdateOrdersComment() {
		if (getSession().getAttribute(Constants.SESSION_SUBSCRIBER) == null) {
			return LOGIN;
		}
		Subscriber s = (Subscriber) getSession().getAttribute(
				Constants.SESSION_SUBSCRIBER);
		
		Orders orders = ordersService.queryOrdersByOrderNo(ordersComment.getOrdersNo());
		orders.setIsAppraise(1);//订单状态改为已评论， 0 为未评价 1为评价
		ordersService.updateOrders(orders);
		
		ordersComment.setOrdersId(orders.getId());
		ordersComment.setCommentPerson(s.getId());
		ordersComment.setCommentTime(new Date());
		ordersComment.setAuditState("0");
		ordersCommentService.addComment(ordersComment);
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "修改成功!");
		return SUCCESS;
	}
	
	
	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public OrdersComment getOrdersComment() {
		return ordersComment;
	}

	public void setOrdersComment(OrdersComment ordersComment) {
		this.ordersComment = ordersComment;
	}

	public Page<OrdersComment> getPage() {
		return page;
	}

	public void setPage(Page<OrdersComment> page) {
		this.page = page;
	}

	public OrdersCommentService getOrdersCommentService() {
		return ordersCommentService;
	}

	public void setOrdersCommentService(OrdersCommentService ordersCommentService) {
		this.ordersCommentService = ordersCommentService;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrdersno() {
		return ordersno;
	}

	public void setOrdersno(String ordersno) {
		this.ordersno = ordersno;
	}

}
