package com.dearho.cs.carservice.action.violation;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.carservice.service.CarViolationService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class CarViolationGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CarViolationService carViolationService;
	
	private CarViolation carViolation;
	private AreaService areaService;
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	private CarService carService;
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			carViolation = new CarViolation();
		}else{
			CarViolation eEntity = carViolationService.searchCarViolationById(id);
			if (eEntity == null){
				carViolation = new CarViolation();
			}
			else {
				carViolation = eEntity;
				Car c = carService.queryCarById(carViolation.getCarId());
				if(c != null){
					carViolation.setPlateNumber(c.getVehiclePlateId());
				}
				Orders orders = ordersService.queryOrdersById(carViolation.getOrderId());
				if(orders != null){
					carViolation.setOrderCode(orders.getOrdersNo());
				}
				Subscriber subscriber = subscriberService.querySubscriberAllInfoById(carViolation.getMemberId());
				if(subscriber != null){
					carViolation.setMemberName(subscriber.getName());
				}
			}
			
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarViolationService getCarViolationService() {
		return carViolationService;
	}

	public void setCarViolationService(CarViolationService carViolationService) {
		this.carViolationService = carViolationService;
	}

	public CarViolation getCarViolation() {
		return carViolation;
	}

	public void setCarViolation(CarViolation carViolation) {
		this.carViolation = carViolation;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode("");
	}

	
}
