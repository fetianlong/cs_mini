package com.dearho.cs.teldintrf.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.pojo.TeldIntrfPile;

public interface TeldIntrfPileService {

	public TeldIntrfPile getTeldPileByCode(String pileCode);
	public Page<TeldIntrfPile> getTeldPilePages(Page<TeldIntrfPile> page,TeldIntrfPile pile);
	public void addOrUpdate(TeldIntrfPile pile);
	public List<TeldIntrfPile> getAllStations();
	
}
