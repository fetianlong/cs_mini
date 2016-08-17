package com.dearho.cs.carservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dearho.cs.carservice.dao.CarAccidentDAO;
import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.carservice.service.CarAccidentService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarAccidentServiceImpl implements CarAccidentService{
	
	private CarAccidentDAO carAccidentDAO;
	 
	public void setCarAccidentDAO(CarAccidentDAO carAccidentDAO) {
		this.carAccidentDAO = carAccidentDAO;
	}
	public CarAccidentDAO getCarAccidentDAO() {
		return carAccidentDAO;
	}

	@Override
	public List<CarAccident> searchCarAccident(CarAccident carAccident) {
		String hql = "from CarAccident r where 1=1 ";
		
		if(carAccident != null){
			if(!StringHelper.isRealNull(carAccident.getCode())){
				hql += "and r.code = '"+carAccident.getCode()+"'";
			}
		}
		return carAccidentDAO.searchCarAccident(hql);
	}

	@Override
	public void addCarAccident(CarAccident carAccident) {
		carAccidentDAO.addCarAccident(carAccident);
	}

	@Override
	public void updateCarAccident(CarAccident carAccident) {
		carAccidentDAO.updateCarAccident(carAccident);
	}

	@Override
	public void deleteCarAccident(String[] checkdel) {
		carAccidentDAO.deleteCarAccident(checkdel);
	}

	@Override
	public Page<CarAccident> searchCarAccident(Page<CarAccident> page,
			CarAccident carAccident) {
		String hql = "select a.id from CarAccident a  ";
		if(carAccident != null){
			if(!StringHelper.isRealNull(carAccident.getOrderCode())){
				hql += ",Orders o";
			}
			if(!StringHelper.isRealNull(carAccident.getMemberName())){
				hql += ",Subscriber s";
			}
			if(!StringHelper.isRealNull(carAccident.getPlateNumber())){
				hql += ",Car c";
			}
			
			hql += " where 1=1 ";
			
			if(!StringHelper.isRealNull(carAccident.getOrderId())){
				hql += " and a.orderId = '"+carAccident.getOrderId()+"'";
			}
			if(!StringHelper.isRealNull(carAccident.getOrderCode())){
				hql += " and a.orderId = o.id and o.ordersNo = '" + carAccident.getOrderCode().trim() +"'" ;
			}
			if(!StringHelper.isRealNull(carAccident.getMemberId())){
				hql += " and a.memberId = '"+carAccident.getMemberId()+"'";
			}
			if(!StringHelper.isRealNull(carAccident.getMemberName())){
				hql += " and a.memberId = s.id and s.name like '%"+carAccident.getMemberName().trim()+"%'";
			}
			if(!StringHelper.isRealNull(carAccident.getCarId())){
				hql += " and a.carId = '"+carAccident.getCarId()+"'";
			}
			if(!StringHelper.isRealNull(carAccident.getPlateNumber())){
				hql += " and a.carId = c.id and c.vehiclePlateId ='" + carAccident.getPlateNumber().trim() + "'";
			}
			
			if(StringHelper.isNotEmpty(carAccident.getCode())){
				hql += " and a.code = '"+carAccident.getCode().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carAccident.getAccidentType())){
				hql += " and a.accidentType = '"+carAccident.getAccidentType().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carAccident.getCity())){
				hql += " and a.city = '"+carAccident.getCity().trim()+"'";
			}
			if(carAccident.getIsDiscard() != null){
				hql += " and a.isDiscard = "+carAccident.getIsDiscard();
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "order by a.createTime desc " : page.getOrderByString());
		return carAccidentDAO.searchCarAccident(page, hql);
	}

	@Override
	public CarAccident searchCarAccidentById(String id) {
		return carAccidentDAO.searchCarAccidentById(id);
	}
	
	@Override
	public List<CarAccident> searchCarAccidentCurrentDate() {
		Calendar todayStart = Calendar.getInstance();  
	    todayStart.set(Calendar.HOUR, 0);  
	    todayStart.set(Calendar.MINUTE, 0);  
	    todayStart.set(Calendar.SECOND, 0);  
	    todayStart.set(Calendar.MILLISECOND, 0);  
	    Date startDate = todayStart.getTime();  
	    Date endDate = new Date();
	    
	    String hql = "from CarAccident r where 1=1 and r.createTime >= '"+DateUtil.getChar19DateString(startDate)+"' and r.createTime <= '"+DateUtil.getChar19DateString(endDate)+"'";
		return carAccidentDAO.searchCarAccident(hql);
	}

}
