package com.dearho.cs.carservice.action.violation;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.carservice.service.CarViolationService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CarViolationSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarViolationService carViolationService;
	
	private Page<CarViolation> page = new Page<CarViolation>();
	
	private CarViolation carViolation;
	private AreaService areaService;
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	private CarService carService;
	
	private String queryType;
	
	public CarViolationSearchAction(){
		super();
		carViolation = new CarViolation();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			if(StringHelper.isNotEmpty(queryType)){
				if("near15days".equals(queryType)){
					page = carViolationService.searchCarViolationNear15Days(page, carViolation);
				}
			}
			else{
				page = carViolationService.searchCarViolation(page, carViolation);
			}
			
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					CarViolation cv = (CarViolation)obj;
					AdministrativeArea area = areaService.searchAreaById(cv.getCity());
					if(area != null ){
						cv.setCityName(area.getName());
					}
					Car c = carService.queryCarById(cv.getCarId());
					if(c != null){
						cv.setPlateNumber(c.getVehiclePlateId());
					}
					Orders orders = ordersService.queryOrdersById(cv.getOrderId());
					if(orders != null){
						cv.setOrderCode(orders.getOrdersNo());
					}
					Subscriber subscriber = subscriberService.querySubscriberAllInfoById(cv.getMemberId());
					if(subscriber != null){
						cv.setMemberName(subscriber.getName());
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
	public CarViolationService getCarViolationService() {
		return carViolationService;
	}

	public void setCarViolationService(CarViolationService carViolationService) {
		this.carViolationService = carViolationService;
	}

	public Page<CarViolation> getPage() {
		return page;
	}

	public void setPage(Page<CarViolation> page) {
		this.page = page;
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
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}


	
}
