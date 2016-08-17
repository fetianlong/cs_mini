package com.dearho.cs.teldintrf.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.dao.TeldIntrfStationDAO;
import com.dearho.cs.teldintrf.pojo.TeldIntrfStation;
import com.dearho.cs.teldintrf.service.TeldIntrfStationService;
import com.dearho.cs.util.StringHelper;

public class TeldIntrfStationServiceImpl implements TeldIntrfStationService {

	private TeldIntrfStationDAO teldIntrfStationDAO;

	@Override
	public TeldIntrfStation getTeldStationByCode(String staCode) {
		String hql = "from TeldIntrfStation a where a.staCode = ?";
		List<TeldIntrfStation> stations = teldIntrfStationDAO.findByAllHql(hql,staCode);
		if(stations != null && stations.size() > 0){
			return stations.get(0);
		}
		return null;
	}

	@Override
	public TeldIntrfStation getTeldStationById(String id) {
		String hql = "from TeldIntrfStation a where a.id = ?";
		List<TeldIntrfStation> stations = teldIntrfStationDAO.findByAllHql(hql,id);
		if(stations != null && stations.size() > 0){
			return stations.get(0);
		}
		return null;
	}

	@Override
	public Page<TeldIntrfStation> getTeldStationPages(
			Page<TeldIntrfStation> page, TeldIntrfStation station) {
		String hql = "select a.id from TeldIntrfStation a where 1=1 ";
		if(station != null){
			if(StringHelper.isNotEmpty(station.getStaCode())){
				hql += " and a.staCode = '"+station.getStaCode()+"'";
			}
			if(StringHelper.isNotEmpty(station.getStaName())){
				hql += " and a.staName like '%"+station.getStaName()+"%'";
			}
			if(StringHelper.isNotEmpty(station.getStaAddress())){
				hql += " and a.staAddress like '%"+station.getStaAddress()+"%'";
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		
		return teldIntrfStationDAO.getTeldStationPages(page,hql);
	}

	
	public TeldIntrfStationDAO getTeldIntrfStationDAO() {
		return teldIntrfStationDAO;
	}
	public void setTeldIntrfStationDAO(TeldIntrfStationDAO teldIntrfStationDAO) {
		this.teldIntrfStationDAO = teldIntrfStationDAO;
	}

	@Override
	public void addOrUpdate(TeldIntrfStation station) {
		if(station == null){
			return;
		}
		else if(StringHelper.isEmpty(station.getId())){
			teldIntrfStationDAO.addTeldIntrfStation(station);
		}
		else{
			teldIntrfStationDAO.updateTeldIntrfStation(station);
		}
	}

	@Override
	public List<TeldIntrfStation> getAllStations() {
		String hql = "from TeldIntrfStation a ";
		List<TeldIntrfStation> stations = teldIntrfStationDAO.findByAllHql(hql);
		return stations;
	}

	@Override
	public Page<TeldIntrfStation> getAllTeldStationPages(
			Page<TeldIntrfStation> page, TeldIntrfStation station) {
		String hql = "from TeldIntrfStation a where 1=1 ";
		if(station != null){
			if(StringHelper.isNotEmpty(station.getStaCode())){
				hql += " and a.staCode = '"+station.getStaCode()+"'";
			}
			if(StringHelper.isNotEmpty(station.getStaName())){
				hql += " and a.staName like '%"+station.getStaName()+"%'";
			}
			if(StringHelper.isNotEmpty(station.getStaAddress())){
				hql += " and a.staAddress like '%"+station.getStaAddress()+"%'";
			}
		}
		List<TeldIntrfStation> stations = teldIntrfStationDAO.findByAllHql(hql);
		page.setResults(stations);
		return page;
	}
}
