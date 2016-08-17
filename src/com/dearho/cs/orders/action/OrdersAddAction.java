/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersAddAction.java creation date: [2015年6月2日 下午5:11:53] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersBill;
import com.dearho.cs.orders.service.OrdersBillService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.dearho.cs.util.orders.FileEveryDaySerialNumber;
import com.dearho.cs.util.orders.SerialNumber;
import com.opensymphony.webwork.ServletActionContext;

/**
 * @author lvlq
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月2日
 */
public class OrdersAddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private Orders orders;
	private OrdersBill bill;
	
	private OrdersService ordersService;
	private String ordersTime;
	
	private String backTime;
	
	private OrdersBillService ordersBillService;
	
	private CarService carService;
	
	
	public OrdersBillService getOrdersBillService() {
		return ordersBillService;
	}


	public void setOrdersBillService(OrdersBillService ordersBillService) {
		this.ordersBillService = ordersBillService;
	}


	public String getBackTime() {
		return backTime;
	}


	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}


	public Orders getOrders() {
		return orders;
	}


	public void setOrders(Orders orders) {
		this.orders = orders;
	}


	public OrdersService getOrdersService() {
		return ordersService;
	}


	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}


	public String getOrdersTime() {
		return ordersTime;
	}


	public void setOrdersTime(String ordersTime) {
		this.ordersTime = ordersTime;
	}


	public OrdersBill getBill() {
		return bill;
	}


	public void setBill(OrdersBill bill) {
		this.bill = bill;
	}
	
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	public OrdersAddAction() {
		super();
		orders=new Orders();
		bill=new OrdersBill();
	}


	@Override
	public String process() {
		
		SerialNumber serial = new FileEveryDaySerialNumber(5, "EveryDaySerialNumber1.dat");  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		try {
			Date date=sdf.parse(ordersTime);
			if(date.getTime()<new Date().getTime()){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "预定时间小于当前时间");
				return ERROR;
			}
			
			//验证车辆当前状态
			Car car = carService.queryCarById(orders.getCarId());
			if(car!=null){
				Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
				if(!car.getBizState().equals(ydDict.getId())){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "当前车辆已预定");
					return ERROR;
				}
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "车辆不存在！");
				return ERROR;
			}
			if(backTime!=null&&!backTime.equals("")){
				Date back=sdf.parse(backTime);
				orders.setOrdersBackTime(back);
			}
			orders.setOrdersTime(date);
			orders.setCreateDate(new Date());
			orders.setTs(new Date());
			if(s!=null&&StringHelper.isNotEmpty(s.getId())){
				orders.setMemberId(s.getId());
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "登录超时");
				return ERROR;
			}
			if(orders.getIsBill()==1){
				ordersBillService.addBill(bill);
			}
			orders.setState("1");
			orders.setOrdersNo(serial.getSerialNumber());
			ordersService.addOrders(orders);
			//更新车辆状态
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "1");
			car.setBizState(ydDict.getId());
			carService.updateCar(car);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "",orders);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "预定失败");
			return ERROR;
		}
		
	}

}
