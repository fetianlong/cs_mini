package com.dearho.cs.teldintrf.service.impl;

import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.dao.TeldIntrfPileDAO;
import com.dearho.cs.teldintrf.pojo.TeldIntrfPile;
import com.dearho.cs.teldintrf.service.TeldIntrfPileService;
import com.dearho.cs.util.StringHelper;

public class TeldIntrfPileServiceImpl implements TeldIntrfPileService {

	private TeldIntrfPileDAO teldIntrfStationDAO;

	@Override
	public TeldIntrfPile getTeldPileByCode(String pileCode) {
		String hql = "from TeldIntrfPile a where a.pileCode = ?";
		List<TeldIntrfPile> piles = teldIntrfStationDAO.findByAllHql(hql,pileCode);
		if(piles != null && piles.size() > 0){
			return piles.get(0);
		}
		return null;
	}


	@Override
	public Page<TeldIntrfPile> getTeldPilePages(
			Page<TeldIntrfPile> page, TeldIntrfPile pile) {
		String hql = "select a.pileCode from TeldIntrfPile a where 1=1 ";
		if(pile != null){
			if(StringHelper.isNotEmpty(pile.getPileCode())){
				hql += " and a.pileCode = '"+pile.getPileCode()+"'";
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		
		return teldIntrfStationDAO.getTeldPilePages(page,hql);
	}

	
	public TeldIntrfPileDAO getTeldIntrfPileDAO() {
		return teldIntrfStationDAO;
	}
	public void setTeldIntrfPileDAO(TeldIntrfPileDAO teldIntrfStationDAO) {
		this.teldIntrfStationDAO = teldIntrfStationDAO;
	}

	@Override
	public void addOrUpdate(TeldIntrfPile pile) {
		if(pile == null || StringHelper.isEmpty(pile.getPileCode())){
			return;
		}
		TeldIntrfPile teldIntrfPile = getTeldPileByCode(pile.getPileCode());
		if(teldIntrfPile == null){
			pile.setCreateTime(new Date());
			teldIntrfStationDAO.addTeldIntrfPile(pile);
		}
		else{
			teldIntrfPile.setUpdateTime(new Date());
			teldIntrfPile.setPileName(pile.getPileName());
			teldIntrfPile.setPileType(pile.getPileType());
			teldIntrfStationDAO.updateTeldIntrfPile(teldIntrfPile);
		}
	}

	@Override
	public List<TeldIntrfPile> getAllStations() {
		String hql = "from TeldIntrfPile a ";
		List<TeldIntrfPile> piles = teldIntrfStationDAO.findByAllHql(hql);
		return piles;
	}
}
