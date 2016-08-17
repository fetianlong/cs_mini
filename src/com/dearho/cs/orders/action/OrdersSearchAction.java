/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersSearchAction.java creation date: [2015年6月4日 下午1:32:12] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

/**
 * @author lvlq
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月4日
 */
public class OrdersSearchAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private OrdersService ordersService;
	
	private SubscriberService subscriberService;
	
	private BranchDotService branchDotService;
	
	private Page<Orders> page=new Page<Orders>();
	
	private CarService carService;
	
	private Orders orders;
	
	private String state;
	private String query;
	
	private String orderState;
	
	private String startTime;
	private String endTime;
	
	private List<BranchDot> dots=new ArrayList<BranchDot>();
	
	private List<OrdersDetail> ordersDetailList = new ArrayList<OrdersDetail>();
	
	private OrdersDetailService ordersDetailService;
	
	private StrategyBaseService strategyBaseService;
	
	public OrdersSearchAction() {
		super();
		orders=new Orders();
		page.setCountField("a.id");
		page.setCurrentPage(1);
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


	public Page<Orders> getPage() {
		return page;
	}


	public void setPage(Page<Orders> page) {
		this.page = page;
	}


	public Orders getOrders() {
		return orders;
	}


	public void setOrders(Orders orders) {
		this.orders = orders;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	@Override
	public String process() {
		String returnPage = "";
		if("page".equals(state)){
			if("accident".equals(query)){
				page=ordersService.queryOrdersPageNotAccident(page, orders);
				
			}
			returnPage = "search";
		}
		else{
			if(StringHelper.isNotEmpty(orderState)){
				Dict d = DictUtil.getDictByCodes("14", orderState);
				if(d  != null){
					orders.setState(d.getId());
				}
			}
			page=ordersService.queryOrdersPage(page, orders,startTime,endTime);
			returnPage = SUCCESS;
		}
		if(page != null && page.getResults() != null){
			for(Object obj : page.getResults()){
				Orders o = (Orders)obj;
				if(!StringHelper.isRealNull(o.getMemberId())){
					o.setMemberName(getMemberName(o.getMemberId()));
				}
				if(!StringHelper.isRealNull(o.getCarId())){
					Car c = carService.queryCarById(o.getCarId());
					if(c != null){
						o.setPlateNumber(c.getVehiclePlateId());
						CarVehicleModel cvm = c.getCarVehicleModel();
						if(cvm != null){
							
							Dict d = DictUtil.getDictById(cvm.getBrand());
							o.setVehicleModelName(d.getCnName()+" "+cvm.getName());
						}
						
					}
				}
			}
		}
		return returnPage;
	}
	
	public String getMemberName(String id){
		Subscriber subscriber=subscriberService.querySubscriberById(id);
		if(subscriber!=null){
			return subscriber.getName();
		}
		return null;
	}
	

	public String ordersDetail(){
		String id = getRequest().getParameter("id");
		orders = ordersService.queryOrdersById(id);
		
		Date endDate = orders.getEndTime();
		endDate = endDate == null?new Date():endDate;
		
		if(!StringHelper.isRealNull(orders.getMemberId())){
			orders.setMemberName(getMemberName(orders.getMemberId()));
		}
		if(!StringHelper.isRealNull(orders.getCarId())){
			Car c = carService.queryCarById(orders.getCarId());
			if(c != null){
				orders.setPlateNumber(c.getVehiclePlateId());
				CarVehicleModel cvm = c.getCarVehicleModel();
				if(cvm != null){
					Dict d = DictUtil.getDictById(cvm.getBrand());
					orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
				}
				
			}
		}
		
		dots=ordersService.queryDotByCon(null);
		Map<String,String> dotMap = new HashMap<String,String>();
			
		for(BranchDot dot:dots){
			dotMap.put(dot.getId(), dot.getName());
		}
		if(!StringHelper.isRealNull(orders.getBeginSiteId())){
			orders.setBeginSiteId(dotMap.get(orders.getBeginSiteId()));
		}
		if(!StringHelper.isRealNull(orders.getEndSiteId())){
			orders.setEndSiteId(dotMap.get(orders.getEndSiteId()));
		}
		if(!StringHelper.isRealNull(orders.getOrdersBackSiteId())){
			orders.setOrdersBackSiteId(dotMap.get(orders.getOrdersBackSiteId()));
		}
		if(orders.getEndTime() != null && orders.getBeginTime() != null ){
			Long time = orders.getEndTime().getTime() - orders.getBeginTime().getTime();
			Long minuteTime = (time/(1000*60))%60;
			orders.setOrdersDuration(Integer.parseInt(""+minuteTime));
		}
		
		ordersDetailList = ordersDetailService.getPaidOrdersDetailsByNo(orders.getOrdersNo());
		
		if(ordersDetailList!=null && ordersDetailList.size()!=0){
			for(int i=0;i<ordersDetailList.size();i++){
				OrdersDetail ordersDetail = ordersDetailList.get(i);
				BigDecimal mileFee = strategyBaseService.conMileFee(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),endDate,orders.getCarId());
				BigDecimal mile = strategyBaseService.conMile(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),endDate,orders.getCarId());
				BigDecimal timeFee = strategyBaseService.conTimeFee(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),endDate,orders.getCarId());
				
				ordersDetail.setMileFee(mileFee);
				ordersDetail.setMileage(mile);
				ordersDetail.setTimeFee(timeFee);
			}
		}
		
		orders.setOrdersDetail(ordersDetailList);
		return SUCCESS;
	}
	
	public String ordersDetailByNo(){
		String no = getRequest().getParameter("no");
		orders = ordersService.queryOrdersByOrderNo(no);
		if(!StringHelper.isRealNull(orders.getMemberId())){
			orders.setMemberName(getMemberName(orders.getMemberId()));
		}
		if(!StringHelper.isRealNull(orders.getCarId())){
			Car c = carService.queryCarById(orders.getCarId());
			if(c != null){
				orders.setPlateNumber(c.getVehiclePlateId());
				CarVehicleModel cvm = c.getCarVehicleModel();
				if(cvm != null){
					Dict d = DictUtil.getDictById(cvm.getBrand());
					orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
				}
				
			}
		}
		
		dots=ordersService.queryDotByCon(null);
		Map<String,String> dotMap = new HashMap<String,String>();
			
		for(BranchDot dot:dots){
			dotMap.put(dot.getId(), dot.getName());
		}
		if(!StringHelper.isRealNull(orders.getBeginSiteId())){
			orders.setBeginSiteId(dotMap.get(orders.getBeginSiteId()));
		}
		if(!StringHelper.isRealNull(orders.getEndSiteId())){
			orders.setEndSiteId(dotMap.get(orders.getEndSiteId()));
		}
		if(!StringHelper.isRealNull(orders.getOrdersBackSiteId())){
			orders.setOrdersBackSiteId(dotMap.get(orders.getOrdersBackSiteId()));
		}
		if(orders.getEndTime() != null && orders.getBeginTime() != null ){
			Long time = orders.getEndTime().getTime() - orders.getBeginTime().getTime();
			Long minuteTime = (time/(1000*60))%60;
			orders.setOrdersDuration(Integer.parseInt(""+minuteTime));
		}
		
		ordersDetailList = ordersDetailService.getPaidOrdersDetailsByNo(orders.getOrdersNo());
		
		orders.setOrdersDetail(ordersDetailList);
		return SUCCESS;
	}
	public String getDotNameById(String dotId){
		BranchDot bd = branchDotService.getBranchDotById(dotId);
		if(bd != null){
			return bd.getName();
		}
		return "";
	}


	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public List<BranchDot> getDots() {
		return dots;
	}

	public void setDots(List<BranchDot> dots) {
		this.dots = dots;
	}

	public List<OrdersDetail> getOrdersDetailList() {
		return ordersDetailList;
	}

	public void setOrdersDetailList(List<OrdersDetail> ordersDetailList) {
		this.ordersDetailList = ordersDetailList;
	}

	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}
	
	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}

	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}

	
}
