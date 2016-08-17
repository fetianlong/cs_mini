package com.dearho.cs.carservice.service.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarPatrolDAO;
import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.carservice.service.CarPatrolService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

public class CarPatrolServiceImpl implements CarPatrolService{
	
	private CarPatrolDAO carPatrolDAO;
	 
	public void setCarPatrolDAO(CarPatrolDAO carPatrolDAO) {
		this.carPatrolDAO = carPatrolDAO;
	}
	public CarPatrolDAO getCarPatrolDAO() {
		return carPatrolDAO;
	}

	@Override
	public List<CarPatrol> searchCarPatrol(CarPatrol carPatrol) {
		String hql = "from CarPatrol r where 1=1 ";
		
		if(carPatrol != null){
			if(!StringHelper.isRealNull(carPatrol.getCode())){
				hql += "and r.code = '"+carPatrol.getCode()+"'";
			}
		}
		return carPatrolDAO.searchCarPatrol(hql);
	}

	@Override
	public void addCarPatrol(CarPatrol carPatrol) {
		carPatrolDAO.addCarPatrol(carPatrol);
	}

	@Override
	public void updateCarPatrol(CarPatrol carPatrol) {
		carPatrolDAO.updateCarPatrol(carPatrol);
	}

	@Override
	public void deleteCarPatrol(String[] checkdel) {
		carPatrolDAO.deleteCarPatrol(checkdel);
	}

	@Override
	public Page<CarPatrol> searchCarPatrol(Page<CarPatrol> page,
			CarPatrol carPatrol) {
		String hql = "select a.id from CarPatrol a  ";
		if(carPatrol != null){
			if(!StringHelper.isRealNull(carPatrol.getPlateNumber())){
				hql += ",Car c";
			}
			if(!StringHelper.isRealNull(carPatrol.getDotId())){
				hql += ",BranchDot bd";
			}
			if(!StringHelper.isRealNull(carPatrol.getParkingPatrolCode()) 
					|| !StringHelper.isRealNull(carPatrol.getParkingPatrolId())){
				hql += ",ParkingPatrol pp";
			}
			hql += " where 1=1 ";
			
			if(!StringHelper.isRealNull(carPatrol.getPlateNumber())){
				hql += " and a.carId = c.id and c.vehiclePlateId like '%" + carPatrol.getPlateNumber().trim() +"%'" ;
			}
			if(!StringHelper.isRealNull(carPatrol.getDotId())){
				hql += " and a.dotId = bd.id and bd.id = '"+carPatrol.getDotId().trim()+"'";
			}
			if(!StringHelper.isRealNull(carPatrol.getParkingPatrolCode()) 
					|| !StringHelper.isRealNull(carPatrol.getParkingPatrolId())){
				hql += " and a.parkingPatrolId = pp.id ";
				if(!StringHelper.isRealNull(carPatrol.getParkingPatrolCode())){
					hql += " and pp.code like '%"+carPatrol.getParkingPatrolCode().trim()+"%'";
				}
				if(!StringHelper.isRealNull(carPatrol.getParkingPatrolId())){
					hql += " and pp.id = '"+carPatrol.getParkingPatrolId().trim()+"'";
				}
			}
			
			if(StringHelper.isNotEmpty(carPatrol.getCode())){
				hql += " and a.code = '"+carPatrol.getCode().trim()+"'";
			}
			if(carPatrol.getIsDiscard() != null){
				hql += " and a.isDiscard = "+carPatrol.getIsDiscard();
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc" : page.getOrderByString());
		return carPatrolDAO.searchCarPatrol(page, hql);
	}

	@Override
	public CarPatrol searchCarPatrolById(String id) {
		return carPatrolDAO.searchCarPatrolById(id);
	}

}
