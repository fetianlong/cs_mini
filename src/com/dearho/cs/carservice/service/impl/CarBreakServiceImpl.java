package com.dearho.cs.carservice.service.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarBreakDao;
import com.dearho.cs.carservice.pojo.CarBreak;
import com.dearho.cs.carservice.service.CarBreakService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

public class CarBreakServiceImpl implements CarBreakService {
	private CarBreakDao carBreakDao;
 


	public CarBreakDao getCarBreakDao() {
		return carBreakDao;
	}



	public void setCarBreakDao(CarBreakDao carBreakDao) {
		this.carBreakDao = carBreakDao;
	}



	@Override
	public void addCarBreak(CarBreak carBreak) {
		carBreakDao.addCarBreak(carBreak);
	}



	@Override
	public void updateCarBreak(CarBreak carBreak) {
		carBreakDao.updateCarBreak(carBreak);
	}



	@Override
	public Page<CarBreak> queryCarBreak(Page<CarBreak> page, CarBreak carBreak) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from CarBreak a where 1=1  ");
		if(carBreak !=null){
			 
			if(StringHelper.isNotEmpty(carBreak.getSubscriberId())){
				sb.append(" and a.subscriberId = '"+carBreak.getSubscriberId()+"'");
			}
			if(StringHelper.isNotEmpty(carBreak.getSubscriberName())){
				sb.append(" and a.subscriberName like '%"+carBreak.getSubscriberName()+"%'");
			}
			if(StringHelper.isNotEmpty(carBreak.getCarId())){
				sb.append(" and a.carId = '"+carBreak.getCarId()+"'");
			}
			if(StringHelper.isNotEmpty(carBreak.getOrderId())){
				sb.append(" and a.orderId = '"+carBreak.getOrderId()+"'");
			}
			if(StringHelper.isNotEmpty(carBreak.getOrderNumber())){
				sb.append(" and a.orderNumber = '"+carBreak.getOrderNumber()+"'");
			}
			if(StringHelper.isNotEmpty(carBreak.getCarNumber())){
				sb.append(" and a.carNumber = '"+carBreak.getCarNumber()+"'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.ts desc" : "order by a."+page.getOrderByString().replace("order by", ""));
		page=carBreakDao.queryCarBreakByPage(page, sb.toString());
		return page;
	}



	@Override
	public CarBreak queryCarBreakByOrderId(String id) {
		String hql = " from CarBreak c where c.orderId = '"+id+"' ";
		List<CarBreak> list = carBreakDao.queryCarBreakByHql(hql);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}



	@Override
	public void deleteCarByOrderIds(String[] ids) {
		carBreakDao.deleteCarBreakArrays(ids);
	}



	@Override
	public CarBreak queryCarBreakById(String id) {
		return carBreakDao.queryCarBreakById(id);
	}
}
