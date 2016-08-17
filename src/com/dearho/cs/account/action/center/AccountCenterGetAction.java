/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.action.center;

import java.util.GregorianCalendar;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountCenterGetAction extends AbstractAction {

	
	
	private static final long serialVersionUID = -4365773690582053808L;
	private AccountService accountService;
	private OrdersService ordersService;
	private SubscriberService subscriberService;
	private WechatUserInfoService wechatUserInfoService;
	private Account account;
	private Orders order;
	private Subscriber subscriber;
	private WechatUserInfo wechatUserInfo;
	
	private CarService carService;
	
	
	public AccountCenterGetAction(){
		super();
		account= new Account();
		order = new Orders();
		subscriber=new Subscriber();
		wechatUserInfo=new WechatUserInfo();
	}

	@Override
	public String process() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		account=accountService.getAccoutDetail(s.getId());
		
		order=ordersService.queryCurrentOrder(s.getId());
		subscriber=subscriberService.querySubscriberById(s.getId());
		getSession().setAttribute(Constants.SESSION_SUBSCRIBER, subscriber);
		
		if(order !=null &&!StringHelper.isRealNull(order.getCarId())){
			Car c = carService.queryCarById(order.getCarId());
			if(c != null){
				order.setPlateNumber(c.getVehiclePlateId());
				CarVehicleModel cvm = c.getCarVehicleModel();
				if(cvm != null){
					
					Dict d = DictUtil.getDictById(cvm.getBrand());
					order.setVehicleModelName(d.getCnName()+" "+cvm.getName());
				}
				
			}
		}
		
		GregorianCalendar ca = new GregorianCalendar();  
		getRequest().setAttribute("isPm", ca.get(GregorianCalendar.AM_PM));
		
		wechatUserInfo=wechatUserInfoService.getUserInfoBySubscriberId(s.getId());
		return SUCCESS;
	}
	
	/**
	 * 呼叫客服页面
	 * @return
	 */
	public String toCustomerService() {
		return SUCCESS;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}


	

	
	
}
