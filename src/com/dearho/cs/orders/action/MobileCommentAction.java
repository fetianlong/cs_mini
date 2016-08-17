package com.dearho.cs.orders.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersComment;
import com.dearho.cs.orders.service.OrdersCommentService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

public class MobileCommentAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private OrdersCommentService ordersCommentService;
	
	private OrdersService ordersService;
	
	private SubscriberService subscriberService;
	
	private UserService userService;
	
	private CarService carService;
	
	private OrdersComment ordersComment;
	
	private String ordersId;
	private Orders orders;
	private Double avgScore;
	private String lastCommentId;
	private String carId;
	private String type;
	private Integer commentCount;
	
	public MobileCommentAction(){
		super();
	}
	
	@Override
	public String process() {
		return SUCCESS;
	}

	/**
	 * 前台用户评论
	 * @return
	 */
	public String toOrdersCommentAdd() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		return SUCCESS;
	}
	public String toCommentList(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		if(StringHelper.isEmpty(ordersId)){
			return ERROR;
		}
		orders = ordersService.queryOrdersById(ordersId);
		if(orders == null || !s.getId().equals(orders.getMemberId())){
			return ERROR;
		}
		if(!StringHelper.isRealNull(orders.getCarId())){
			Car c = carService.queryCarById(orders.getCarId());
			if(c != null){
				orders.setPlateNumber(c.getVehiclePlateId());
				CarVehicleModel cvm = c.getCarVehicleModel();
				if(cvm != null){
					Dict d = DictUtil.getDictById(cvm.getBrand());
					orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
					c.setCarVehicleModel(cvm);
					orders.setCar(c);
				}
			}
		}
		avgScore = ordersCommentService.getAvgScore(orders.getCarId());
		commentCount = ordersCommentService.getCommentCount(orders.getCarId());
		return SUCCESS;
	}
	public String searchCommentList(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		List<OrdersComment> comments = ordersCommentService.queryComment(type,carId,lastCommentId);
		if(comments != null && comments.size() > 0){
			for (OrdersComment ordersComment : comments) {
				ordersComment.setCommentTimeStr(DateUtil.formatDate(ordersComment.getCommentTime(), "yyyy-MM-dd"));
				Subscriber commentPerson = subscriberService.querySubscriberById(ordersComment.getCommentPerson());
				if(commentPerson != null){
					ordersComment.setCommentPersonPhone(commentPerson.getPhoneNo().substring(0, 3)+"****"+commentPerson.getPhoneNo().substring(7));
				}
				
			}
		}
		result = Ajax.JSONResult(0, "", comments);
		return SUCCESS;
	}
	/**
	 * 新增评论
	 * @return
	 */
	public String addComment() {
		if (getSession().getAttribute(Constants.SESSION_SUBSCRIBER) == null) {
			return LOGIN;
		}
		Subscriber s = (Subscriber) getSession().getAttribute(
				Constants.SESSION_SUBSCRIBER);
		Orders orders = ordersService.queryOrdersById(ordersComment.getOrdersId());
		if(orders == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "查询不到订单!");
			return SUCCESS;
		}
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


	public OrdersCommentService getOrdersCommentService() {
		return ordersCommentService;
	}

	public void setOrdersCommentService(OrdersCommentService ordersCommentService) {
		this.ordersCommentService = ordersCommentService;
	}
	
	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	public Double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}

	public String getLastCommentId() {
		return lastCommentId;
	}

	public void setLastCommentId(String lastCommentId) {
		this.lastCommentId = lastCommentId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
}
