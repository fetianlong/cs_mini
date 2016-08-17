package com.dearho.cs.carservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dearho.cs.carservice.dao.CarRepairDAO;
import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.carservice.service.CarRepairService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarRepairServiceImpl implements CarRepairService{
	
	private CarRepairDAO carRepairDAO;
	 
	public void setCarRepairDAO(CarRepairDAO carRepairDAO) {
		this.carRepairDAO = carRepairDAO;
	}
	public CarRepairDAO getCarRepairDAO() {
		return carRepairDAO;
	}

	@Override
	public List<CarRepair> searchCarRepair(CarRepair carRepair) {
		String hql = "from CarRepair r where 1=1 ";
		
		if(carRepair != null){
			if(!StringHelper.isRealNull(carRepair.getCode())){
				hql += "and r.code = '"+carRepair.getCode()+"'";
			}
		}
		return carRepairDAO.searchCarRepair(hql);
	}

	@Override
	public void addCarRepair(CarRepair carRepair) {
		carRepairDAO.addCarRepair(carRepair);
	}

	@Override
	public void updateCarRepair(CarRepair carRepair) {
		carRepairDAO.updateCarRepair(carRepair);
	}

	@Override
	public void deleteCarRepair(String[] checkdel) {
		carRepairDAO.deleteCarRepair(checkdel);
	}

	@Override
	public Page<CarRepair> searchCarRepair(Page<CarRepair> page,
			CarRepair carRepair) {
		String hql = "select a.id from CarRepair a  ";
		if(carRepair != null){
			if(!StringHelper.isRealNull(carRepair.getMemberName())){
				hql += ",Subscriber s";
			}
			if(!StringHelper.isRealNull(carRepair.getPlateNumber())){
				hql += ",Car c";
			}
			
			hql += " where 1=1 ";
			
			if(!StringHelper.isRealNull(carRepair.getMemberName())){
				hql += " and a.memberId = s.id and s.name like '%"+carRepair.getMemberName().trim()+"%'";
			}
			if(!StringHelper.isRealNull(carRepair.getPlateNumber())){
				hql += " and a.carId = c.id and c.vehiclePlateId ='" + carRepair.getPlateNumber().trim() + "'";
			}
			if(StringHelper.isNotEmpty(carRepair.getRepairOrderCode())){
				hql += " and a.repairOrderCode = '"+carRepair.getRepairOrderCode().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carRepair.getStatus())){
				hql += " and a.status = '"+carRepair.getStatus().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carRepair.getCode())){
				hql += " and a.code = '"+carRepair.getCode().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carRepair.getCity())){
				hql += " and a.city = '"+carRepair.getCity().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carRepair.getStatus())){
				hql += " and a.status = '"+carRepair.getStatus().trim()+"'";
			}
			if(carRepair.getIsDiscard() != null){
				hql += " and a.isDiscard = "+carRepair.getIsDiscard();
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc" : page.getOrderByString());
		return carRepairDAO.searchCarRepair(page, hql);
	}

	@Override
	public CarRepair searchCarRepairById(String id) {
		return carRepairDAO.searchCarRepairById(id);
	}
	@Override
	public List<CarRepair> searchCarRepairCurrentDate() {
		Calendar todayStart = Calendar.getInstance();  
	    todayStart.set(Calendar.HOUR, 0);  
	    todayStart.set(Calendar.MINUTE, 0);  
	    todayStart.set(Calendar.SECOND, 0);  
	    todayStart.set(Calendar.MILLISECOND, 0);  
	    Date startDate = todayStart.getTime();  
	    Date endDate = new Date();
	    
	    String hql = "from CarRepair r where 1=1 and r.createTime >= '"+DateUtil.getChar19DateString(startDate)+"' and r.createTime <= '"+DateUtil.getChar19DateString(endDate)+"'";
		return carRepairDAO.searchCarRepair(hql);
	}

}
