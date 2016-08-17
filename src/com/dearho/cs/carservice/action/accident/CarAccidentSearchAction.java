package com.dearho.cs.carservice.action.accident;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.carservice.service.CarAccidentService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CarAccidentSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarAccidentService carAccidentService;
	
	private Page<CarAccident> page = new Page<CarAccident>();
	
	private CarAccident carAccident;
	
	private AreaService areaService;
	
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	private CarService carService;
	
	public CarAccidentSearchAction(){
		super();
		carAccident = new CarAccident();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = carAccidentService.searchCarAccident(page, carAccident);
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					CarAccident ca = (CarAccident)obj;
					AdministrativeArea area = areaService.searchAreaById(ca.getCity());
					if(area != null ){
						ca.setCityName(area.getName());
					}
					Car c = carService.queryCarById(ca.getCarId());
					if(c != null){
						ca.setPlateNumber(c.getVehiclePlateId());
					}
					Orders orders = ordersService.queryOrdersById(ca.getOrderId());
					if(orders != null){
						ca.setOrderCode(orders.getOrdersNo());
					}
					Subscriber subscriber = subscriberService.querySubscriberAllInfoById(ca.getMemberId());
					if(subscriber != null){
						ca.setMemberName(subscriber.getName());
					}
				}
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
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
	
	public CarAccidentService getCarAccidentService() {
		return carAccidentService;
	}

	public void setCarAccidentService(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}

	public Page<CarAccident> getPage() {
		return page;
	}

	public void setPage(Page<CarAccident> page) {
		this.page = page;
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
