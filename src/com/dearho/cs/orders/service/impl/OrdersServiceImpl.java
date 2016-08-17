/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersServiceImpl.java creation date: [2015年5月28日 上午9:52:34] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.dearho.cs.account.dao.AccountTradeRecordDao;
import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.car.dao.CarDao;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.dao.OrdersBillDao;
import com.dearho.cs.orders.dao.OrdersDao;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersBill;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

/**
 * @author lvlq
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年5月28日
 */
public class OrdersServiceImpl implements OrdersService {
	
	private OrdersDao ordersDao;
	private CarDao carDao;
	private OrdersBillDao ordersBillDao;
	private CarService carService;
	
	private AccountService accountService;
	private OrdersDetailService ordersDetailService;
	
	private StrategyBaseService strategyBaseService;
	
	private SubscriberService subscriberService;
	
	private CarDotBindingService carDotBindingService;
	
	
	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;
	
	private AccountTradeRecordDao accountTradeRecordDao;
	
	private static final Log log = LogFactory.getLog(OrdersServiceImpl.class);
	

	@Override
	public List<AdministrativeArea> queryAreaByCon(AdministrativeArea area) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from AdministrativeArea a where a.id in (select o.areaId from BranchDot o)");
		if(area!=null){
			if(StringHelper.isNotEmpty(area.getName())){
				sb.append(" and a.parentCode in (select r.code from AdministrativeArea r where r.name='"+area.getName()+"' )");
			}
		}
		return ordersDao.queryAreaByCon(sb.toString());
	}

	@Override
	public List<BranchDot> queryDotByCon(BranchDot dot) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from BranchDot a where 1=1");
		if(dot!=null){
			if(StringHelper.isNotEmpty(dot.getAreaId())){
				sb.append(" and a.areaId='").append(dot.getAreaId()).append("'");
			}
			if(StringHelper.isNotEmpty(dot.getId())){
				sb.append(" and a.id in(select dotId from Parking where bizComp in (select bizComp from Parking where dotId='").append(dot.getId()).append("' group by bizComp) group by dotId)");
			}
		}
		return ordersDao.queryDotByHql(sb.toString());
	}

	@Override
	public List<Car> queryCarsByCon(String dotId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Car a where 1=1");
		sb.append(" and a.id in (select p.carId from CarParkingBind p where p.parkingId in (select id from Parking where dotId='");
		sb.append(dotId);
		sb.append("'))");
		return ordersDao.queryCarByHql(sb.toString());
	}

	@Override
	public Page<Car> queryCarPages(Page<Car> page,String dotId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Car a where 1=1");
		if(StringHelper.isNotEmpty(dotId)){
			sb.append(" and a.id in ( select id from CarRealtimeState where id in (select p.carId from CarParkingBind p where p.parkingId in (select id from Parking where dotId='");
			sb.append(dotId);
			sb.append("')))");
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : page.getOrderByString());
		
		page=ordersDao.queryCarPages(page, sb.toString());
		return page;
	}
	@Override
	public Page<CarRealtimeState> queryCarRealPages(
			Page<CarRealtimeState> page, String dotId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from CarRealtimeState a where 1=1");
		if(StringHelper.isNotEmpty(dotId)){
			sb.append(" and a.id in (select p.carId from CarParkingBind p where p.parkingId in (select id from Parking where dotId='");
			sb.append(dotId);
			sb.append("'))");
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=ordersDao.queryCarRealPage(page, sb.toString());
		return page;
	}

	@Override
	public void addOrders(Orders orders) {
		ordersDao.addOrders(orders);
	}

	@Override
	public void updateOrders(Orders orders) {
		ordersDao.updateOrders(orders);
	}

	@Override
	public Page<Orders> queryOrdersPage(Page<Orders> page, Orders orders,String startTime,String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");
		if(orders!=null){
			if(StringHelper.isNotEmpty(orders.getOrdersNo())){
				sb.append(" and a.ordersNo like '").append(orders.getOrdersNo().trim()).append("%'");
			}
			if(StringHelper.isNotEmpty(orders.getState())){
				sb.append(" and a.state = '").append(orders.getState()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getMemberId())){
				sb.append(" and a.memberId = '").append(orders.getMemberId()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getCarId())){
				sb.append(" and a.carId = '").append(orders.getCarId()).append("'");
			}
		}
		if(StringHelper.isNotEmpty(startTime)){
			Date startDate = null;
			try {
				startDate = DateUtil.parseDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.createDate >= '").append(DateUtil.getChar19DateString(startDate)).append("'");
			} catch (Exception e) {
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			Date endDate = null;
			try {
				endDate = DateUtil.parseDate(endTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.createDate < '").append(DateUtil.getChar19DateString(endDate)).append("'");
			} catch (Exception e) {
			}
		}
		sb.append(" order by ordersNo desc");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=ordersDao.queryOrdersPage(page, sb.toString());
		return page;
	}
	
	
	
	public Page<Orders> queryOrdersPageByEndTime(Page<Orders> page, Orders orders,String startTime,String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");
		if(orders!=null){
			if(StringHelper.isNotEmpty(orders.getOrdersNo())){
				sb.append(" and a.ordersNo like '").append(orders.getOrdersNo().trim()).append("%'");
			}
			if(StringHelper.isNotEmpty(orders.getState())){
				sb.append(" and a.state = ").append(orders.getState());
			}
			if(StringHelper.isNotEmpty(orders.getMemberId())){
				sb.append(" and a.memberId = '").append(orders.getMemberId()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getCarId())){
				sb.append(" and a.carId = '").append(orders.getCarId()).append("'");
			}
		}
		if(StringHelper.isNotEmpty(startTime)){
			Date startDate = null;
			try {
				startDate = DateUtil.parseDate(startTime+" 00:00:00", "yyyyMMdd HH:mm:ss");
				sb.append(" and a.endTime > '").append(DateUtil.getChar19DateString(startDate)).append("'");
			} catch (Exception e) {
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			Date endDate = null;
			try {
				endDate = DateUtil.parseDate(endTime+" 00:00:00", "yyyyMMdd HH:mm:ss");
				sb.append(" and a.endTime < '").append(DateUtil.getChar19DateString(endDate)).append("'");
			} catch (Exception e) {
			}
		}
		sb.append(" order by ordersNo desc");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=ordersDao.queryOrdersPage(page, sb.toString());
		return page;
	}

	@Override
	public Page<CarRealtimeState> queryCarRealStatePages(
			Page<CarRealtimeState> page, BranchDot dot,CarRealtimeState realtimeState,String carBizState) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from CarRealtimeState a,Car b where a.id = b.id ");
		if(StringHelper.isNotEmpty(carBizState)){
			sb.append(" and b.bizState='"+carBizState+"'");
		}
		if(dot!=null){
			StringBuffer sb1=new StringBuffer();
			sb1.append("select o.id from BranchDot o where 1=1");
			if(StringHelper.isNotEmpty(dot.getAreaId())){
				sb1.append(" and o.areaId='").append(dot.getAreaId()).append("'");
			}
			if(StringHelper.isNotEmpty(dot.getId())){
				sb1.append(" and o.id='").append(dot.getId()).append("'");
			}
			if(!sb1.toString().equals("select o.id from BranchDot o where 1=1")){
				String str="select p.id from Parking p where p.dotId in ("+sb1.toString()+")";
				sb.append(" and a.id in (select b.carId from CarParkingBind b where b.parkingId in (").append(str).append("))");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=ordersDao.queryCarRealPage(page, sb.toString());
		return page;
	}


	@Override
	public BranchDot queryDotById(String id) {
		return ordersDao.queryDotById(id);
	}

	@Override
	public CarRealtimeState queryCarRealState(String id) {
		return ordersDao.queryCarRealtimeState(id);
	}

	@Override
	public void updateCarRealtimeState(CarRealtimeState carRealtimeState) {
		ordersDao.updateCarRealTimeState(carRealtimeState);
	}

	@Override
	public CarRealtimeState queryCarRealtimeStateById(String id) {
		return ordersDao.queryCarRealStateById(id);
	}

	@Override
	public List<Orders> queryOrders(String hql) {
		return ordersDao.queryOrders(hql);
	}

	@Override
	public List<CarRealtimeState> queryCarRealList(String dotId) {
		String hql="select a.id from CarRealtimeState a,Car b where a.id = b.id";
		Dict d = DictUtil.getDictByCodes("carBizState", "0");
		hql+=" and b.bizState='"+d.getId()+"'";
		String str="select p.id from Parking p where p.dotId ='"+dotId+"'";
		hql+=" and a.id in (select b.carId from CarParkingBind b where b.parkingId in ("+str+") )";
		return ordersDao.queryCarRealtimeList(hql);
	}

	
	@Override
	public List<AdministrativeArea> queryAreaByCity(String id) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from AdministrativeArea a where a.parentCode is null or length(a.parentCode)=0");
		if(StringHelper.isNotEmpty(id)){
			sb.append(" and a.id ='"+id+"'");
		}
		return ordersDao.queryAreaByCon(sb.toString());
		
	}

	@Override
	public List<Orders> queryOrdersByCon(Orders orders) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");
		if(orders!=null){
			if(StringHelper.isNotEmpty(orders.getCarId())){
				sb.append(" and a.carId ='").append(orders.getCarId()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getMemberId())){
				sb.append(" and a.memberId ='").append(orders.getMemberId()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getOrdersNo())){
				sb.append(" and a.ordersNo ='").append(orders.getOrdersNo()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getPayStyle())){
				sb.append(" and a.payStyle ='").append(orders.getPayStyle()).append("'");
			}
			if(StringHelper.isNotEmpty(orders.getState())){
				sb.append(" and a.state =").append(orders.getState());
			}
		}
		sb.append(" order by createDate desc");//排序条件勿动
		return ordersDao.queryOrders(sb.toString());
	}
	
	
	public List<Orders> queryEndOrdersBy15Days(String subscriberId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");
		sb.append(" and a.memberId ='").append(subscriberId).append("'");
	    sb.append("  and a.state = ").append(Orders.STATE_ORDER_END).append("");//租车结束
		
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE, -Constants.REFUND_LATELY_COMPLETION_DAY);
		
		sb.append(" and endTime>= '").append(DateUtil.getChar19DateString(calendar.getTime())).append("'");
			
		sb.append(" order by createDate desc");
		return ordersDao.queryOrders(sb.toString());
	}
	
	@Override
	public List<Orders> getNoOpenBillOrders(String subscriberId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");
		sb.append(" and a.memberId ='").append(subscriberId).append("'");
	    sb.append("  and a.state = ").append(Orders.STATE_ORDER_END).append("");//租车结束
	    sb.append("  and (a.isOpenBill is null or a.isOpenBill = '0')");//未开发票
	    sb.append(" order by createDate desc");
	    return ordersDao.queryOrders(sb.toString());
	}
	
	@Override
	public Orders queryOrdersById(String id) {
		return ordersDao.queryOrdersById(id);
	}
	
	public Orders queryOrdersByOrderNo(String orderNo) {
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from Orders a where a.ordersNo ='"+orderNo+"'");
		List<Orders> list= ordersDao.queryOrders(hql.toString());
		if(list ==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
		
	}

	@Override
	public Page<Orders> queryOrdersPageNotAccident(Page<Orders> page,
			Orders orders) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where a.id not in (select ao.orderId from CarAccident ao) ");
		if(orders != null){
			if(StringHelper.isNotEmpty(orders.getOrdersNo())){
				sb.append(" and a.orderNo like '").append(orders.getOrdersNo()).append("%'");
			}
			if(StringHelper.isNotEmpty(orders.getState())){
				sb.append(" and a.state = '").append(orders.getState()).append("'");
			}
		}
		sb.append(" order by ordersNo desc");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=ordersDao.queryOrdersPage(page, sb.toString());
		return page;
	}

	@Override
	public Page<Orders> queryPortalOrdersPage(Page<Orders> page,
			String begintime, String endTime, String searchType,
			String lasttimetype, String orderState,Subscriber s) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where a.memberId ='").append(s.getId()).append("'");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if(StringHelper.isEmpty(searchType)){
			c.add(Calendar.MONTH, -1);
			sb.append(" and a.createDate > '");
			sb.append(DateUtil.getChar19DateString(c.getTime())).append("'");
			
		}
		else if("lasttime".equals(searchType)){
			int num = 0;
			if("1".equals(lasttimetype)){
				num = -1;
			}
			else if("2".equals(lasttimetype)){
				num = -2;
			}
			else if("3".equals(lasttimetype)){
				num = -3;
			}
			else if("4".equals(lasttimetype)){
				num = -6;
			}
			else if("5".equals(lasttimetype)){
				num = -12;
			}
			c.add(Calendar.MONTH, num);
			sb.append(" and a.createDate > '");
			sb.append(DateUtil.getChar19DateString(c.getTime())).append("'");
		}
		else if("rangetime".equals(searchType)){
			if(StringHelper.isNotEmpty(begintime)){
				sb.append(" and a.createDate > '");
				try {
					sb.append(DateUtil.getChar19DateString(DateUtil.parseDate(begintime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"))).append("'");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(StringHelper.isNotEmpty(endTime)){
				sb.append(" and a.createDate < '");
				try {
					sb.append(DateUtil.getChar19DateString(DateUtil.parseDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss"))).append("'");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		if(StringHelper.isNotEmpty(orderState)){
			if("1".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "1");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("2".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "3");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("3".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "4");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("4".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "0");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("5".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "2");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
		}
		sb.append(" order by a.createDate desc");
		page=ordersDao.queryOrdersPage(page, sb.toString());
		return page;
	}
	
	/**
	 * 当前订单
	 * @param subscriberId
	 * @return
	 */
	public Orders queryCurrentOrder(String subscriberId){
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");

		sb.append(" and a.memberId ='").append(subscriberId).append("'");
		sb.append("  and a.state != '").append(Orders.STATE_BOOKING_TIMEOUT).append("'");//预约超时
		sb.append("  and a.state != '").append(Orders.STATE_BOOKING_CANCEL).append("'");//预约取消 
		sb.append("  and a.state != '").append(Orders.STATE_ORDER_END).append("'");//租车结束 

		List<Orders> list=ordersDao.queryOrders(sb.toString());
		if(list==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
		
	}

	@Override
	public List<Orders> queryOrdersList(String type,String lastOrderId,Integer currentNum, String begintime,
			String endTime, String searchType, String lasttimetype,
			String orderState, Subscriber s) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where a.memberId ='").append(s.getId()).append("'");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if(StringHelper.isEmpty(searchType)){
			c.add(Calendar.MONTH, -1);
			sb.append(" and a.createDate > '");
			sb.append(DateUtil.getChar19DateString(c.getTime())).append("'");
			
		}
		else if("lasttime".equals(searchType)){
			int num = 0;
			if("1".equals(lasttimetype)){
				num = -1;
			}
			else if("2".equals(lasttimetype)){
				num = -2;
			}
			else if("3".equals(lasttimetype)){
				num = -3;
			}
			else if("4".equals(lasttimetype)){
				num = -6;
			}
			else if("5".equals(lasttimetype)){
				num = -12;
			}
			c.add(Calendar.MONTH, num);
			sb.append(" and a.createDate > '");
			sb.append(DateUtil.getChar19DateString(c.getTime())).append("'");
		}
		else if("rangetime".equals(searchType)){
			if(StringHelper.isNotEmpty(begintime)){
				sb.append(" and a.createDate > '");
				try {
					sb.append(DateUtil.getChar19DateString(DateUtil.parseDate(begintime+" 00:00:00", "yyyy-MM-dd HH:mm:ss"))).append("'");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(StringHelper.isNotEmpty(endTime)){
				sb.append(" and a.createDate < '");
				try {
					sb.append(DateUtil.getChar19DateString(DateUtil.parseDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss"))).append("'");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		if(StringHelper.isNotEmpty(orderState)){
			if("1".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "1");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("2".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "3");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("3".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "4");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("4".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "0");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
			if("5".equals(orderState)){
				Dict d = DictUtil.getDictByCodes("14", "2");
				if(d  != null){
					sb.append(" and a.state ='").append(d.getCode()).append("'");
				}
			}
		}
		if(type != null && "add".equals(type) && StringHelper.isNotEmpty(lastOrderId)){
			Orders order = ordersDao.queryOrdersById(lastOrderId);
			sb.append(" and a.createDate < '").append(DateUtil.getChar19DateString(order.getCreateDate())).append("'");
		}
		sb.append(" order by a.createDate desc ");
		
		Page<Orders> page = new Page<Orders>();
		page.setCountField("a.id");
		page.setCurrentPage(0);
		page.setPageSize(5);
		
		page=ordersDao.queryOrdersPage(page, sb.toString());
		List<Orders> orders = page.getResults();
		return page.getResults();
	}

	@Override
	public Orders queryLast5MinutesOrders(String subId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1");
		sb.append(" and a.memberId ='").append(subId).append("'");
		sb.append("  and a.state = '").append(Orders.STATE_ORDER_END).append("'");//租车结束
		Date d = new Date();
		d.setTime(d.getTime()-1000*60*5);
		sb.append(" and a.endTime > '").append(DateUtil.getChar19DateString(d)).append("'");
		List<Orders> list=ordersDao.queryOrders(sb.toString());
		if(list==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * 创建订单
	 * @param order 
	 * @param channel 下单渠道
	 */
	public Orders createOrder(Orders order,Integer channel)throws Exception{
		
		if(order==null){
			throw new Exception("订单为空！");
		}
		if(order.getCarId()==null){
			throw new Exception("车辆为空！");
		}
		if(channel==null){
			throw new Exception("渠道为空!");
		}
		
		//查询车辆信息
		Car car=carDao.queryCarById(order.getCarId());
		//车辆状态校验
		if(car!=null){
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
			if(!car.getBizState().equals(ydDict.getId())){
				throw new Exception("当前车辆已预定！");
			}
		}else{
			throw new Exception("车辆不存在！");
		}
		//更新车辆状态
		Dict ydDict = DictUtil.getDictByCodes("carBizState", "1");
		car.setBizState(ydDict.getId());
		
		carDao.updateCar(car);

		//添加发票信息
		if(order.getIsBill() != null && Orders.IS_BILL_TRUE.equals(order.getIsBill())){
			OrdersBill ordersBill=order.getOrdersBill();
			ordersBillDao.addBill(ordersBill);
			order.setBillId(ordersBill.getId());
		}
		
		
		//添加订单
		order.setChannel(channel);
		ordersDao.addOrders(order);
		return order;
	}

	

	@Override
	@Transactional
	public Orders bookCarAndAddOrder(Orders orders, Car car) {
		try {
			addOrders(orders);
			// 更新车辆状态
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "1");
			car.setBizState(ydDict.getId());
			carService.updateCar(car);
		} catch (Exception e) {
		}
		return orders;
	}
	
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	@Override
	public void cancelOrder(String orderId,Boolean isAdmin) throws Exception {
		if(StringUtils.isEmpty(orderId)){
			throw new Exception("订单编号为空！");
		}
		Orders orders=ordersDao.queryOrdersById(orderId);
		if(orders==null){
			throw new Exception("根据订单id,未查询到订单");
		}
		
		//只有预订成功状态订单能取消
		if(!"1".equals(orders.getState()) && !"3".equals(orders.getState())){
			throw new Exception("订单不为预定成功状态，不能取消!");
		}
		Date date = new Date();
		Car car = carService.queryCarById(orders.getCarId());
		//更新订单状态
		orders.setState("0");
		orders.setEndTime(date);
//		if(!isAdmin){
//			orders.setTotalFee(new BigDecimal(0));
//		}else{
//			orders.setTotalFee(new BigDecimal(0));
//		}
		ordersDao.updateOrders(orders);
		
		OrdersDetail currentOrdersDetail = ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());
		if(currentOrdersDetail != null){
			currentOrdersDetail.setIsRunning("0");
			currentOrdersDetail.setIsOver("1");
			currentOrdersDetail.setEndTime(date);
			currentOrdersDetail.setTicketsFee(new BigDecimal(0.00));
			ordersDetailService.updateOrdersDetail(currentOrdersDetail);
		}
		
		//更新车辆状态
		//看看是否是预下线
		Dict yxxDict = DictUtil.getDictByCodes("carBizState", "6");
		if(yxxDict.getId().equals(car.getBizState())){
			//如果是预下线，改成下线状态
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "3");
			car.setBizState(ydDict.getId());
		}
		else{
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
			car.setBizState(ydDict.getId());
		}
		carService.updateCar(car);
		
//		if(!isAdmin){
//			//扣除最低消费
//			accountService.orderPayment(orders,0d,0);
//		}
		
		Subscriber subscriber=subscriberService.querySubscriberById(orders.getMemberId());
		String str="尊敬的"+subscriber.getName()+"，您的订单"+orders.getOrdersNo()+"已取消。【乐途出行】";
		
		SMSUtil.sendSMS(subscriber.getPhoneNo(), str, SMSRecord.TYPE_ORDER);
	}
	
	@Override
	public Orders queryOrdersByDetailId(String ordersDetailId) {
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from Orders a,OrdersDetail od where a.ordersNo=od.ordersNo and od.id ='"+ordersDetailId+"'");
		List<Orders> list= ordersDao.queryOrders(hql.toString());
		if(list ==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	private Orders queryOrdersByDetailNo(String ordersDetailNo) {
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from Orders a,OrdersDetail od where a.ordersNo=od.ordersNo and od.ordersDetailNo ='"+ordersDetailNo+"'");
		List<Orders> list= ordersDao.queryOrders(hql.toString());
		if(list ==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	@Override
	@Transactional
	public boolean completeOrder( String ordersDetailId,String ordersDetailNo,Integer payType,HttpServletRequest request,HttpServletResponse response) {
		Orders orders = null;
		OrdersDetail ordersDetail = null;
		if(!StringHelper.isEmpty(ordersDetailId)){
			orders = queryOrdersByDetailId(ordersDetailId);
			ordersDetail = ordersDetailService.getOrdersDetailById(ordersDetailId);
		}
		else if(!StringHelper.isEmpty(ordersDetailNo)){
			orders = queryOrdersByDetailNo(ordersDetailNo);
			ordersDetail = ordersDetailService.getOrdersDetailByNo(ordersDetailNo);
		}
		else {
			return false;
		}
		
		if(orders == null || ordersDetail == null){
			return false;
		}
		Car car = carService.queryCarById(orders.getCarId());
		ordersDetail.setPayType(payType);
		//如果未支付
		if(!"1".equals(ordersDetail.getIsPaid())){
			log.info("ordersDetail complete.  OrdersDetail id is " + ordersDetail.getId());
			ordersDetail.setIsPaid("1");
			if(ordersDetail.getEndTime() == null){
				ordersDetail.setEndTime(new Date());
			}
			if(orders.getTotalFee() == null){
				BigDecimal totalFee = ordersDetail.getTotalFee();
				if(ordersDetail.getTicketsFee() != null){
					totalFee = totalFee.add(ordersDetail.getTicketsFee());
				}
				orders.setTotalFee(totalFee);
			}
			else{
				//未支付的主订单加上子订单费用
				BigDecimal totalFee = orders.getTotalFee();
				totalFee = totalFee.add(ordersDetail.getTotalFee());
				if(ordersDetail.getTicketsFee() != null){
					totalFee = totalFee.add(ordersDetail.getTicketsFee());
				}
				orders.setTotalFee(totalFee);
			}
		}
		
		
		ordersDetail.setIsOver("1");
		ordersDetail.setIsRunning("0");
		if(ordersDetail.getEndTime() != null){
			ordersDetail.setEndTime(new Date());
		}
		ordersDetailService.updateOrdersDetail(ordersDetail);
		
		orders.setState("4");
		if(orders.getEndTime() == null){
			orders.setEndTime(new Date());
		}
		
		updateOrders(orders);
		log.info("orders complete. Orders id is " + orders.getId());
		
		//更新车辆状态
		//看看是否是预下线
		Dict yxxDict = DictUtil.getDictByCodes("carBizState", "6");
		if(yxxDict.getId().equals(car.getBizState())){
			//如果是预下线，改成下线状态
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "3");
			car.setBizState(ydDict.getId());
		}
		else{
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
			car.setBizState(ydDict.getId());
		}
		carService.updateCar(car);
		
		//更新车辆和网点的绑定状态
		CarDotBinding carDotBinding = carDotBindingService.getBindingByCarId(orders.getCarId(), 1);
		carDotBinding.setIsUsed(0);
		carDotBindingService.update(carDotBinding);
		
		carDotBinding = new CarDotBinding();
		carDotBinding.setCarId(orders.getCarId());
		carDotBinding.setCreateTime(new Date());
		carDotBinding.setCreator(orders.getMemberId());
		carDotBinding.setDotId(orders.getEndSiteId());
		carDotBinding.setIsUsed(1);
		carDotBindingService.add(carDotBinding);
		
		Subscriber subscriber=subscriberService.querySubscriberById(orders.getMemberId());
		String str="尊敬的"+subscriber.getName()+"，您已还车成功，本次消费"+orders.getTotalFee()+"元,欢迎您再次使用。如需查看订单，请您到微信公众号中的【历史订单】中查询。【乐途出行】";
		try {
			SMSUtil.sendSMS(subscriber.getPhoneNo(), str, SMSRecord.TYPE_ORDER);
		} catch (Exception e1) {
			log.error("短信发送失败:"+e1.getMessage());
		}
		
		return true;
	}
	
	@Override
	public List<Orders> querySingleDayOrder(Date currentDate) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Orders a where 1=1 ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String startTime = sdf.format(currentDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, 1);
		String endTime = sdf.format(cal.getTime());
		
		if(StringHelper.isNotEmpty(startTime)){
			Date startDate = null;
			try {
				startDate = DateUtil.parseDate(startTime+" 00:00:00", "yyyyMMdd HH:mm:ss");
				sb.append(" and a.createDate >= '").append(DateUtil.getChar19DateString(startDate)).append("' ");
			} catch (Exception e) {
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			Date endDate = null;
			try {
				endDate = DateUtil.parseDate(endTime+" 00:00:00", "yyyyMMdd HH:mm:ss");
				sb.append(" and a.createDate <= '").append(DateUtil.getChar19DateString(endDate)).append("' ");
			} catch (Exception e) {
			}
		}
		
		List<Orders> list=ordersDao.queryOrders(sb.toString());
		return list;
	}
	
	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	public CarDao getCarDao() {
		return carDao;
	}
	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}
	public OrdersBillDao getOrdersBillDao() {
		return ordersBillDao;
	}
	public void setOrdersBillDao(OrdersBillDao ordersBillDao) {
		this.ordersBillDao = ordersBillDao;
	}
	public OrdersDao getOrdersDao() {
		return ordersDao;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
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
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}

	public void setCarDotBindingService(CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}


	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDao() {
		return accountTradeRecordDetailDao;
	}

	public void setAccountTradeRecordDetailDao(AccountTradeRecordDetailDao accountTradeRecordDetailDao) {
		this.accountTradeRecordDetailDao = accountTradeRecordDetailDao;
	}

	public AccountTradeRecordDao getAccountTradeRecordDao() {
		return accountTradeRecordDao;
	}

	public void setAccountTradeRecordDao(AccountTradeRecordDao accountTradeRecordDao) {
		this.accountTradeRecordDao = accountTradeRecordDao;
	}

}
