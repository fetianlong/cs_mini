/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersService.java creation date: [2015年5月28日 上午9:50:28] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.pojo.AdministrativeArea;

/**
 * @author Carsharing03
 * @Description 
 * @Version 1.0, 2015年5月28日
 */
public interface OrdersService {
	
	/**
	 * 查询在线城市
	 * @Title: searchAreaByCityLevel
	 * @Description:
	 * @return
	 * @throws
	 */
	List<AdministrativeArea> queryAreaByCity(String code);
	
	
	/**
	 * 查询可用的区域
	 * @Title queryAreaByCon
	 * @Description:(方法描述)
	 * @param area
	 * @return
	 * @throws
	 */
	List<AdministrativeArea> queryAreaByCon(AdministrativeArea area);
	
	/**
	 * 根据条件查询网点
	 * @Title queryDotByCon
	 * @Description:(方法描述)
	 * @param dot
	 * @return
	 * @throws
	 */
	List<BranchDot> queryDotByCon(BranchDot dot);
	
	/**
	 * 根据网点id查询车辆信息
	 * @Title queryCarsByCon
	 * @Description:(方法描述)
	 * @param dotId 网点ID
	 * @return
	 * @throws
	 */
	List<Car> queryCarsByCon(String dotId);
	
	/**
	 * 分页查询车辆信息
	 * @Title queryCarPages
	 * @Description:(方法描述)
	 * @param dotId
	 * @return
	 * @throws
	 */
	Page<Car> queryCarPages(Page<Car> page,String dotId);
	
	/**
	 * 查询车辆实时数据
	 * @Title queryCarRealPages
	 * @Description:(方法描述)
	 * @param page
	 * @param dotId
	 * @return
	 * @throws
	 */
	Page<CarRealtimeState> queryCarRealPages(Page<CarRealtimeState> page,String dotId);
	
	Page<CarRealtimeState> queryCarRealStatePages(Page<CarRealtimeState> page,BranchDot dot,CarRealtimeState realtimeState,String carBizState);
	
	CarRealtimeState queryCarRealtimeStateById(String id);
	/**
	 * 根据ID查询网点信息
	 * @Title queryDotById
	 * @Description:(方法描述)
	 * @param id
	 * @return
	 * @throws
	 */
	BranchDot queryDotById(String id);
	
	/**
	 * 查询车辆的当前状态
	 * @Title queryCarRealState
	 * @Description:(方法描述)
	 * @param id
	 * @return
	 * @throws
	 */
	CarRealtimeState queryCarRealState(String id);
	
	/**
	 * 更新车辆实时状态
	 * @Title updateCarRealtimeState
	 * @Description:(方法描述)
	 * @param carRealtimeState
	 * @throws
	 */
	void updateCarRealtimeState(CarRealtimeState carRealtimeState);
	
	List<CarRealtimeState> queryCarRealList(String dotId);
	
	/**
	 * 添加订单
	 * @Title addOrders
	 * @Description:(方法描述)
	 * @param orders
	 * @throws
	 */
	void addOrders(Orders orders);
	
	/***
	 * 更新订单
	 * @Title updateOrders
	 * @Description:(方法描述)
	 * @param orders
	 * @throws
	 */
	void updateOrders(Orders orders);
	
	/**
	 * 按条件分页查询订单信息
	 * @Title queryOrdersPage
	 * @Description:(方法描述)
	 * @param page
	 * @param orders
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws
	 */
	Page<Orders> queryOrdersPage(Page<Orders> page,Orders orders,String startTime,String endTime);
	
	List<Orders> queryOrders(String hql);
	
	/**
	 * 按条件查询订单信息
	 * @Title queryOrdersByCon
	 * @Description:(方法描述)
	 * @param orders
	 * @return
	 * @throws
	 */
	List<Orders> queryOrdersByCon(Orders orders);
	
	/**
	 * 根据id查询订单信息
	 * @Title queryOrdersById
	 * @Description:(方法描述)
	 * @param id
	 * @return
	 * @throws
	 */
	Orders queryOrdersById(String id);
	


	/**
	 * @Title:queryOrdersPageNotAccident
	 * @Description:查询无事故的订单
	 * @param page
	 * @param orders
	 * 
	 * @return
	 * @throws
	 */
	Page<Orders> queryOrdersPageNotAccident(Page<Orders> page, Orders orders);
	
	Orders queryOrdersByOrderNo(String orderNo) ;


	/**
	 * 前台查询订单
	 * @param page
	 * @param begintime 起始时间
	 * @param endTime   终止时间
	 * @param searchType 查询类型
	 * @param lasttimetype 最近时间类型
	 * @param orderState  订单状态
	 * @param s
	 * @return
	 */
	Page<Orders> queryPortalOrdersPage(Page<Orders> page, String begintime,
			String endTime, String searchType, String lasttimetype,
			String orderState,Subscriber s);
	
	/**
	 * 当前订单
	 * @param subscriberId
	 * @return
	 */
	public Orders queryCurrentOrder(String subscriberId);


	/**
	 * 根据条件查询订单列表
	 * @param lastOrderTime //上次查询最近的订单时间
	 * @param currentNum  限制数量
	 * @param begintime   
	 * @param endtime
	 * @param searchType
	 * @param lasttimetype
	 * @param orderState
	 * @param s
	 * @return
	 */
	List<Orders> queryOrdersList(String type,String lastOrderTime,Integer currentNum, String begintime,
			String endtime, String searchType, String lasttimetype,
			String orderState, Subscriber s);


	/**
	 * 获取结束5分钟内订单
	 * @param id
	 * @return
	 */
	Orders queryLast5MinutesOrders(String subId);
	
	
	Orders createOrder(Orders order,Integer channel)throws Exception;
	
	
	void cancelOrder(String orderId,Boolean isAdmin)throws Exception;


	/**
	 * 增加订单并修改车辆状态
	 * @param orders
	 * @param car
	 * @return
	 */
	Orders bookCarAndAddOrder(Orders orders, Car car);


	/**
	 * 完成订单
	 * @param orders   主订单
	 * @param ordersDetailId 子订单id
	 * @return
	 */
	boolean completeOrder( String ordersDetailId,String ordersDetailNo,Integer payType,HttpServletRequest request,HttpServletResponse response);


	/**
	 * 根据子订单id查询主订单
	 * @param ordersDetailId
	 * @return
	 */
	Orders queryOrdersByDetailId(String ordersDetailId);
	
	
	
	public Page<Orders> queryOrdersPageByEndTime(Page<Orders> page, Orders orders,String startTime,String endTime) ;


	public List<Orders> querySingleDayOrder(Date currentDate);

	
	/**
	 * 15天内是否有成功完成的订单
	 * @param subscriberId
	 * @return
	 */
	public List<Orders> queryEndOrdersBy15Days(String subscriberId) ;

	/**
	 * 得到没有开发票的订单
	 * @param subscriberId
	 * @return
	 */
	public List<Orders> getNoOpenBillOrders(String subscriberId);
}
