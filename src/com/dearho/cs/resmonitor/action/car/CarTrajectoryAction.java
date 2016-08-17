package com.dearho.cs.resmonitor.action.car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.resmonitor.entity.OrdersTrajectory;
import com.dearho.cs.resmonitor.pojo.CarLocation;
import com.dearho.cs.resmonitor.service.CarLocationService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.GpsTransUtil;
import com.dearho.cs.util.Point;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;

public class CarTrajectoryAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 157092621713285408L;
	
	private OrdersService ordersService;
	private SubscriberService subscriberService;
	private CarLocationService carLocationService;
	private DeviceBindingService deviceBindingService;
	private BranchDotService branchDotService;

	private String id;
	private String startTime = DateUtil.getChar10DateString()+" 00:00";
	private String endTime = DateUtil.formatDate(null,"yyyy-MM-dd HH:mm");
	
	private String timeFormatStr = "yyyy-MM-dd HH:mm";
	private SimpleDateFormat sdf = new SimpleDateFormat(timeFormatStr);
	
	@Override
	public String process() {
		
		Date startDate = null;
		Date endDate = null;
		if(StringHelper.isNotEmpty(startTime)){
			try {
				startDate = sdf.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			try {
				endDate = sdf.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		List<DeviceBinding> deviceBindings = deviceBindingService.queryBindByCarId(id, 1);
		if(deviceBindings == null || deviceBindings.size() <= 0){
			return Action.ERROR;
		}
		//按照车机启停分组
//		List<CarLocation> locations = carLocationService.getLocationByParam(deviceBindings.get(0).getDeviceNo(), 
//				startDate, endDate);
//		List<List<CarLocation>> stageList = new ArrayList<List<CarLocation>>();
//		List<CarLocation> stageLocation = new ArrayList<CarLocation>();
//		boolean isRunning = false;
//		for (int i = 0; i < locations.size(); i++) {
//			if(i == 0){
//				stageLocation = new ArrayList<CarLocation>();
//				continue;
//			}
//			if(locations.get(i).getTs().getTime() 
//				- locations.get(i-1).getTs().getTime() 
//				< 2*60*1000 && isRunning == false){
//				stageLocation.add(locations.get(i-1));
//				isRunning = true;
//			}
//			if(locations.get(i).getTs().getTime() 
//					- locations.get(i-1).getTs().getTime() 
//					> 2*60*1000){
//				isRunning = false;
//			}
//			if(stageLocation.size() > 0 && i == locations.size() - 1){
//				isRunning = false;
//			}
//			
//			if(isRunning){
//				stageLocation.add(locations.get(i));
//			}
//			else if(stageLocation.size() > 0){
//				stageList.add(stageLocation);
//				stageLocation = new ArrayList<CarLocation>();
//			}
//		}
		
		//按照订单分组
		String hql = "select a.id from Orders a where a.state='4' and a.carId= '"+id+"' and a.beginTime > '"+
					startTime+"' and a.endTime < '"+endTime+"' order by a.ts";
		List<Orders> ordersList = ordersService.queryOrders(hql);
		List<OrdersTrajectory> stageList = new ArrayList<OrdersTrajectory>();
		
		if(ordersList != null && ordersList.size() > 0){
			for (Orders orders : ordersList) {
				List<CarLocation> locations = carLocationService.getLocationByParam(deviceBindings.get(0).getDeviceNo(), 
						orders.getOrdersTime(), orders.getOrdersBackTime());
				if(locations == null || locations.size() <= 0){
					continue;
				}
				OrdersTrajectory ordersTrajectory = new OrdersTrajectory();
				ordersTrajectory.setCarLocations(locations);
				orders.setOrdersTimeStr(DateUtil.formatDate(orders.getOrdersTime(), timeFormatStr));
				orders.setOrdersBackTimeStr(DateUtil.formatDate(orders.getOrdersBackTime(), timeFormatStr));
				Subscriber subscriber = subscriberService.querySubscriberById(orders.getMemberId());
				orders.setMemberName(subscriber.getName());
				if(!StringHelper.isRealNull(orders.getBeginSiteId())){
					BranchDot beginDot = branchDotService.getBranchDotById(orders.getBeginSiteId());
					orders.setBeginSiteId(beginDot.getName());
				}
				if(!StringHelper.isRealNull(orders.getEndSiteId())){
					BranchDot endDot = branchDotService.getBranchDotById(orders.getEndSiteId());
					orders.setEndSiteId(endDot.getName());
				}
				if(orders.getEndTime() != null && orders.getBeginTime() != null ){
					Long time = orders.getEndTime().getTime() - orders.getBeginTime().getTime();
					Long minuteTime = (time/(1000*60))%60;
					orders.setOrdersDuration(Integer.parseInt(""+minuteTime));
					orders.setEndTimeStr(DateUtil.formatDate(orders.getEndTime(), "yyyy-MM-dd HH:mm"));
				}
				orders.setOrdersTimeStr(DateUtil.formatDate(orders.getOrdersTime(), "yyyy-MM-dd HH:mm"));
				ordersTrajectory.setOrders(orders);
				stageList.add(ordersTrajectory);
			}
		}
		
		for (OrdersTrajectory ordersTrajectory : stageList) {
			for (CarLocation carLocation : ordersTrajectory.getCarLocations()) {
				Point res = GpsTransUtil.transPoint(carLocation.getLat(), carLocation.getLng());
				carLocation.setLat(res.getLat());
				carLocation.setLng(res.getLng());
				carLocation.setTsDate(DateUtil.formatDate(carLocation.getTs(), timeFormatStr));
			}
		}
		result = Ajax.JSONResult(0, "", stageList);
		return Action.SUCCESS;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public CarLocationService getCarLocationService() {
		return carLocationService;
	}
	public void setCarLocationService(CarLocationService carLocationService) {
		this.carLocationService = carLocationService;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
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
	public OrdersService getOrdersService() {
		return ordersService;
	}
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
}
