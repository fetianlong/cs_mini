package com.dearho.cs.orders.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * 取消订单动作
 * @author Carsharing05
 *
 */
public class OrdersCancelAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	public static final Log logger = LogFactory.getLog(OrdersCancelAction.class);
	
	private OrdersService ordersService;
	
	private SubscriberService subscriberService;
	
	private AccountService accountService;
	
	private Page<Orders> page=new Page<Orders>();
	
	private CarService carService;
	
	private String id;
	
	@Override
	public String process() {
		try {
			ordersService.cancelOrder(id,true);
		} catch (Exception e) {
			logger.error(e);
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "取消失败："+e.getMessage());
			return SUCCESS;
		}

		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "取消成功");
		return SUCCESS;
	}
	public String mobileCancel(){
		try {
			ordersService.cancelOrder(id,false);
		} catch (Exception e) {
			logger.error(e);
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "取消失败："+e.getMessage());
			return SUCCESS;
		}

		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "取消成功");
		return SUCCESS;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public Page<Orders> getPage() {
		return page;
	}

	public void setPage(Page<Orders> page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}

}
