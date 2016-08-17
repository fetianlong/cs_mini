package com.dearho.cs.orders.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.orders.pojo.OrdersComment;
import com.dearho.cs.orders.service.OrdersCommentService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class OrdersCommentAuditAction   extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private OrdersCommentService ordersCommentService;
	
	private String id;
	private String auditType;
	
	@Override
	public String process() {
		OrdersComment comment = ordersCommentService.getCommentById(id);//获得该评论信息
		if(comment == null){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "审核失败！");
			return ERROR;
		}
		if("ok".equals(auditType)){
			comment.setAuditState("1");
		}
		else if("faile".equals(auditType)){
			comment.setAuditState("2");
		}
		else{
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "审核失败！");
			return ERROR;
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			comment.setAuditor(user.getId());
			comment.setAuditorName(user.getName());
		}
		comment.setAuditTime(new Date());
		
		ordersCommentService.update(comment);
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "审核成功！");
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
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

}
