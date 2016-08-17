package com.dearho.cs.carservice.service.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.ParkingPatrolDAO;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

public class ParkingPatrolServiceImpl implements ParkingPatrolService{
	
	private ParkingPatrolDAO parkingPatrolDAO;
	 
	public void setParkingPatrolDAO(ParkingPatrolDAO parkingPatrolDAO) {
		this.parkingPatrolDAO = parkingPatrolDAO;
	}
	public ParkingPatrolDAO getParkingPatrolDAO() {
		return parkingPatrolDAO;
	}

	@Override
	public List<ParkingPatrol> searchParkingPatrol(ParkingPatrol parkingPatrol) {
		String hql = "from ParkingPatrol r where 1=1 ";
		
		if(parkingPatrol != null){
			if(!StringHelper.isRealNull(parkingPatrol.getCode())){
				hql += "and r.code = '"+parkingPatrol.getCode()+"'";
			}
		}
		return parkingPatrolDAO.searchParkingPatrol(hql);
	}

	@Override
	public void addParkingPatrol(ParkingPatrol parkingPatrol) {
		parkingPatrolDAO.addParkingPatrol(parkingPatrol);
	}

	@Override
	public void updateParkingPatrol(ParkingPatrol parkingPatrol) {
		parkingPatrolDAO.updateParkingPatrol(parkingPatrol);
	}

	@Override
	public void deleteParkingPatrol(String[] checkdel) {
		parkingPatrolDAO.deleteParkingPatrol(checkdel);
	}

	@Override
	public Page<ParkingPatrol> searchParkingPatrol(Page<ParkingPatrol> page,
			ParkingPatrol parkingPatrol) {
		String hql = "select a.id from ParkingPatrol a  ";
		if(parkingPatrol != null){
			if(!StringHelper.isRealNull(parkingPatrol.getDotName())){
				hql += ",BranchDot bd";
			}
			if(!StringHelper.isRealNull(parkingPatrol.getUserName())){
				hql += ",User u";
			}
			
			hql += " where 1=1 ";
			
			if(!StringHelper.isRealNull(parkingPatrol.getDotName())){
				hql += " and a.parkingId = bd.id and bd.name like '%" + parkingPatrol.getDotName().trim() +"%'" ;
			}
			if(!StringHelper.isRealNull(parkingPatrol.getUserName())){
				hql += " and a.userId = u.id and u.name like '%"+parkingPatrol.getUserName().trim()+"%'";
			}
			
			if(StringHelper.isNotEmpty(parkingPatrol.getCode())){
				hql += " and a.code = '"+parkingPatrol.getCode().trim()+"'";
			}
			if(StringHelper.isNotEmpty(parkingPatrol.getCity())){
				hql += " and a.city = '"+parkingPatrol.getCity().trim()+"'";
			}
			if(parkingPatrol.getIsDiscard() != null){
				hql += " and a.isDiscard = "+parkingPatrol.getIsDiscard();
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc" : page.getOrderByString());
		return parkingPatrolDAO.searchParkingPatrol(page, hql);
	}

	@Override
	public ParkingPatrol searchParkingPatrolById(String id) {
		return parkingPatrolDAO.searchParkingPatrolById(id);
	}
	@Override
	public boolean checkHasChildPatrol(String pid) {
		return parkingPatrolDAO.checkHasChildPatrol(pid);
	}

}
