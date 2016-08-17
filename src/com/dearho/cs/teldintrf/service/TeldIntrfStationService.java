package com.dearho.cs.teldintrf.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.pojo.TeldIntrfStation;

public interface TeldIntrfStationService {

	public TeldIntrfStation getTeldStationByCode(String staCode);
	public TeldIntrfStation getTeldStationById(String id);
	public Page<TeldIntrfStation> getTeldStationPages(Page<TeldIntrfStation> page,TeldIntrfStation station);
	public void addOrUpdate(TeldIntrfStation station);
	public List<TeldIntrfStation> getAllStations();
	/**
	 * 查询所有电站
	 * @param page
	 * @param teldIntrfStation
	 * @return
	 */
	public Page<TeldIntrfStation> getAllTeldStationPages(
			Page<TeldIntrfStation> page, TeldIntrfStation teldIntrfStation);
	
}
