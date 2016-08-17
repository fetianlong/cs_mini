package com.dearho.cs.charge.dao.impl;

import java.util.List;

import com.dearho.cs.charge.dao.ChargeStationDAO;
import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class ChargeStationDAOImpl extends AbstractDaoSupport implements ChargeStationDAO {

	@Override
	public List<ChargeStation> searchChargeStationByCode(String code) {
		String hql = "from ChargeStation r where 1=1 ";
		
		if(code != null){
			hql += "and r.code = '"+code+"'";
		}
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<ChargeStation> searchChargeStationByName(String name) {
		String hql = "from ChargeStation r where 1=1 ";
		
		if(name != null){
			hql += "and r.name like '%"+name+"%'";
		}
		return getHibernateTemplate().find(hql);
	}

	@Override
	public void addChargeStation(ChargeStation chargeStation) {
		addEntity(chargeStation);
	}

	@Override
	public void updateChargeStation(ChargeStation chargeStation) {
		updateEntity(chargeStation);
	}

	@Override
	public void deleteChargeStation(String[] checkdel) {
		final String queryString="delete ChargeStation where id in (:ids)";
		deleteEntity(ChargeStation.class, queryString, checkdel);
	}

	@Override
	public Page<ChargeStation> searchChargeStation(Page<ChargeStation> page,
			String hql) {
		Page<ChargeStation> resultPage = pageCache(ChargeStation.class ,page, hql);
		resultPage.setResults(idToObj(ChargeStation.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public ChargeStation searchChargeStationById(String id) {
		return get(ChargeStation.class, id);
	}

}
