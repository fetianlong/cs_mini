/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersDaoImpl.java creation date: [2015年5月28日 上午9:49:02] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.dao.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.OrdersDao;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.util.StringHelper;

/**
 * @author lvlq
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年5月28日
 */
public class OrdersDaoImpl extends AbstractDaoSupport implements OrdersDao {

	@Override
	public List<AdministrativeArea> queryAreaByCon(String hql) {
		return getList(AdministrativeArea.class, queryFList(hql));
	}

	@Override
	public List<BranchDot> queryDotByHql(String hql) {
		return getList(BranchDot.class, queryFList(hql));
	}

	@Override
	public List<Car> queryCarByHql(String hql) {
		return getList(Car.class, queryFList(hql));
	}

	@Override
	public Page<Car> queryCarPages(Page<Car> page, String hql) {
		Page<Car> resultPage=pageCache(Car.class, page, hql);
		resultPage.setResults(idToObj(Car.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for (int i = 0; i < resultPage.getResults().size(); i++) {
				Car car=(Car) resultPage.getResults().get(i);
				if(StringHelper.isNotEmpty(car.getModelId())){
					car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId().trim()));
				}
			}
		}
		
		return resultPage;
	}

	@Override
	public Page<CarRealtimeState> queryCarRealPage(Page<CarRealtimeState> page,
			String hql) {
		Page<CarRealtimeState> resultPage=pageCache(CarRealtimeState.class, page, hql);
		resultPage.setResults(idToObj(CarRealtimeState.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for (int i = 0; i < resultPage.getResults().size(); i++) {
				CarRealtimeState real=(CarRealtimeState) resultPage.getResults().get(i);
				Car car=get(Car.class, real.getId().trim());
				real.setCar(car);
				if(StringHelper.isNotEmpty(car.getModelId())){
					car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId().trim()));
				}
				
			}
		}
		
		return resultPage;
	}

	@Override
	public void addOrders(Orders orders) {
		addEntity(orders);
	}

	@Override
	public void updateOrders(Orders orders) {
		updateEntity(orders);
	}

	@Override
	public Page<Orders> queryOrdersPage(Page<Orders> page, String hql) {
		Page<Orders> resultPage=pageCache(Orders.class, page, hql);
		resultPage.setResults(idToObj(Orders.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public BranchDot queryDotById(String dotId) {
		return get(BranchDot.class, dotId);
	}

	@Override
	public CarRealtimeState queryCarRealtimeState(String id) {
		return get(CarRealtimeState.class, id);
	}

	@Override
	public void updateCarRealTimeState(CarRealtimeState carRealtimeState) {
		updateEntity(carRealtimeState);
	}

	@Override
	public CarRealtimeState queryCarRealStateById(String id) {
		CarRealtimeState carRealtimeState=get(CarRealtimeState.class, id);
		if(carRealtimeState!=null&&StringHelper.isNotEmpty(carRealtimeState.getId())){
			Car car=get(Car.class, carRealtimeState.getId().trim());
			if(null!=car&&StringHelper.isNotEmpty(car.getModelId())){
				car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId().trim()));
			}
			carRealtimeState.setCar(car);
		}
		return carRealtimeState;
	}

	@Override
	public List<Orders> queryOrders(String hql) {
		return getList(Orders.class, queryFList(hql));
	}

	@Override
	public List<CarRealtimeState> queryCarRealtimeList(String hql) {
		List<CarRealtimeState> list=getList(CarRealtimeState.class, queryFList(hql));
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				CarRealtimeState carRealtimeState=list.get(i);
				Car car=get(Car.class, carRealtimeState.getId().trim());
				if(null!=car&&StringHelper.isNotEmpty(car.getModelId())){
					car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId().trim()));
				}
				carRealtimeState.setCar(car);
			}
		}
		return list;
	}

	@Override
	public Orders queryOrdersById(String id) {
		Orders order=get(Orders.class, id);
		if(order != null && !StringUtils.isEmpty(order.getCarId())){
			order.setCar(get(Car.class, order.getCarId()));
		}
		return order;
	}

	@Override
	public List<Orders> queryOrderByHql(String hql) {
		return getHibernateTemplate().find(hql);
	}

}
