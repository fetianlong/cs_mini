package com.dearho.cs.charge.service.impl;

import java.util.List;

import com.dearho.cs.charge.dao.ChargeStationDAO;
import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.charge.service.ChargeStationService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

public class ChargeStationServiceImpl implements ChargeStationService {
	
	private ChargeStationDAO chargeStationDAO;

	@Override
	public List<ChargeStation> searchChargeStationByCode(String code) {
		return chargeStationDAO.searchChargeStationByCode(code);
	}

	@Override
	public List<ChargeStation> searchChargeStationByName(String name) {
		return chargeStationDAO.searchChargeStationByName(name);
	}

	@Override
	public void addChargeStation(ChargeStation chargeStation) {
		chargeStationDAO.addChargeStation(chargeStation);
	}

	@Override
	public void updateChargeStation(ChargeStation chargeStation) {
		chargeStationDAO.updateChargeStation(chargeStation);
	}

	@Override
	public void deleteChargeStation(String[] checkdel) {
		chargeStationDAO.deleteChargeStation(checkdel);
	}

	@Override
	public Page<ChargeStation> searchChargeStation(Page<ChargeStation> page,
			ChargeStation chargeStation) {
		StringBuffer hql = new StringBuffer("select a.id from ChargeStation a where 1=1");
		if(chargeStation != null){
			if(StringHelper.isNotEmpty(chargeStation.getCode())){
				hql.append(" and a.code = '").append(chargeStation.getCode()).append("'");
			}
			if(StringHelper.isNotEmpty(chargeStation.getName())){
				hql.append(" and a.name like '%").append(chargeStation.getName()).append("%'");
			}
			if(StringHelper.isNotEmpty(chargeStation.getAddress())){
				hql.append(" and a.address like '%").append(chargeStation.getAddress()).append("%'");
			}
			if(StringHelper.isNotEmpty(chargeStation.getStaType())){
				hql.append(" and a.staType = '").append(chargeStation.getStaType()).append("'");
			}
			if(StringHelper.isNotEmpty(chargeStation.getStaOpstate())){
				hql.append(" and a.staOpstate = '").append(chargeStation.getStaOpstate()).append("'");
			}
			if(StringHelper.isNotEmpty(chargeStation.getManufacturer())){
				hql.append(" and a.manufacturer = '").append(chargeStation.getManufacturer()).append("'");
			}
			
		}
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc " : page.getOrderByString());
		return chargeStationDAO.searchChargeStation(page, hql.toString());
	}

	@Override
	public ChargeStation searchChargeStationById(String id) {
		return chargeStationDAO.searchChargeStationById(id);
	}

	public ChargeStationDAO getChargeStationDAO() {
		return chargeStationDAO;
	}
	public void setChargeStationDAO(ChargeStationDAO chargeStationDAO) {
		this.chargeStationDAO = chargeStationDAO;
	}


}
