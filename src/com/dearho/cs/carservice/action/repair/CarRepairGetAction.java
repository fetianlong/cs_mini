package com.dearho.cs.carservice.action.repair;


import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.carservice.service.CarRepairService;
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
public class CarRepairGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CarRepairService carRepairService;
	
	private CarRepair carRepair;
	
	private AreaService areaService;
	private CarService carService;
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			carRepair = new CarRepair();
		}else{
			CarRepair eEntity = carRepairService.searchCarRepairById(id);
			if (eEntity == null){
				carRepair = new CarRepair();
			}
			else {
				carRepair = eEntity;
				Car c = carService.queryCarById(carRepair.getCarId());
				if(c != null){
					carRepair.setPlateNumber(c.getVehiclePlateId());
				}
				Orders orders = ordersService.queryOrdersById(carRepair.getOrderId());
				if(orders != null){
					carRepair.setOrderCode(orders.getOrdersNo());
				}
				Subscriber subscriber = subscriberService.querySubscriberAllInfoById(carRepair.getMemberId());
				if(subscriber != null){
					carRepair.setMemberName(subscriber.getName());
				}
			}
		}
		return SUCCESS;
	}
	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode(null);
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

	public CarRepairService getCarRepairService() {
		return carRepairService;
	}

	public void setCarRepairService(CarRepairService carRepairService) {
		this.carRepairService = carRepairService;
	}
	public CarRepair getCarRepair() {
		return carRepair;
	}
	public void setCarRepair(CarRepair carRepair) {
		this.carRepair = carRepair;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
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
	

	
}
