package com.dearho.cs.carservice.action.repair;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.carservice.service.CarRepairService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CarRepairSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarRepairService carRepairService;
	
	private Page<CarRepair> page = new Page<CarRepair>();
	
	private CarRepair carRepair;
	
	private AreaService areaService;
	
	private CarService carService;
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	
	private String statusCode;
	
	public CarRepairSearchAction(){
		super();
		carRepair = new CarRepair();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			if(StringHelper.isNotEmpty(statusCode)){
				Dict d = DictUtil.getDictByCodes("repairStatus", statusCode);
				if(d != null){
					carRepair.setStatus(d.getId());
				}
			}
			page = carRepairService.searchCarRepair(page, carRepair);
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					CarRepair cm = (CarRepair)obj;
					AdministrativeArea area = areaService.searchAreaById(cm.getCity());
					if(area != null ){
						cm.setCityName(area.getName());
					}
					Car c = carService.queryCarById(cm.getCarId());
					if(c != null){
						cm.setPlateNumber(c.getVehiclePlateId());
					}
					Orders orders = ordersService.queryOrdersById(cm.getOrderId());
					if(orders != null){
						cm.setOrderCode(orders.getOrdersNo());
					}
					Subscriber subscriber = subscriberService.querySubscriberAllInfoById(cm.getMemberId());
					if(subscriber != null){
						cm.setMemberName(subscriber.getName());
					}
				}
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode(null);
	}
	public CarRepairService getCarRepairService() {
		return carRepairService;
	}

	public void setCarRepairService(CarRepairService carRepairService) {
		this.carRepairService = carRepairService;
	}

	public Page<CarRepair> getPage() {
		return page;
	}

	public void setPage(Page<CarRepair> page) {
		this.page = page;
	}

	public CarRepair getCarRepair() {
		return carRepair;
	}

	public void setCarRepair(CarRepair carRepair) {
		this.carRepair = carRepair;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
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

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
