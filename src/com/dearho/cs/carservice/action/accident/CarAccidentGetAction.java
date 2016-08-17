package com.dearho.cs.carservice.action.accident;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.carservice.service.CarAccidentService;
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
public class CarAccidentGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CarAccidentService carAccidentService;
	
	private CarAccident carAccident;
	
	private AreaService areaService;
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	private CarService carService;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			carAccident = new CarAccident();
		}else{
			CarAccident eEntity = carAccidentService.searchCarAccidentById(id);
			if (eEntity == null){
				carAccident = new CarAccident();
			}
			else {
				carAccident = eEntity;
				Car c = carService.queryCarById(carAccident.getCarId());
				if(c != null){
					carAccident.setPlateNumber(c.getVehiclePlateId());
				}
				Orders orders = ordersService.queryOrdersById(carAccident.getOrderId());
				if(orders != null){
					carAccident.setOrderCode(orders.getOrdersNo());
				}
				Subscriber subscriber = subscriberService.querySubscriberAllInfoById(carAccident.getMemberId());
				if(subscriber != null){
					carAccident.setMemberName(subscriber.getName());
				}
			}
			
		}
		return SUCCESS;
	}

	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode("");
	}
	
	public AreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarAccidentService getCarAccidentService() {
		return carAccidentService;
	}

	public void setCarAccidentService(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}

	public CarAccident getArea() {
		return carAccident;
	}

	public void setArea(CarAccident carAccident) {
		this.carAccident = carAccident;
	}
	public CarAccident getCarAccident() {
		return carAccident;
	}
	public void setCarAccident(CarAccident carAccident) {
		this.carAccident = carAccident;
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

	
}
