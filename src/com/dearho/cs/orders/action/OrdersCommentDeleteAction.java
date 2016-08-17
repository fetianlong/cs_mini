package com.dearho.cs.orders.action;

import com.dearho.cs.orders.service.OrdersCommentService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

public class OrdersCommentDeleteAction   extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private OrdersCommentService ordersCommentService;
	
	private String id;
	
	@Override
	public String process() {
		String[] ids = {id};
		ordersCommentService.deleteComment(ids);
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "");
		return SUCCESS;
	}

	public OrdersCommentService getOrdersCommentService() {
		return ordersCommentService;
	}

	public void setOrdersCommentService(OrdersCommentService ordersCommentService) {
		this.ordersCommentService = ordersCommentService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
