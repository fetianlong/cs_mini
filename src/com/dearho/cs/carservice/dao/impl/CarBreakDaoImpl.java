package com.dearho.cs.carservice.dao.impl;

import java.util.List;

import com.dearho.cs.carservice.dao.CarBreakDao;
import com.dearho.cs.carservice.pojo.CarBreak;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarBreakDaoImpl extends AbstractDaoSupport implements CarBreakDao {
     

	@Override
	public void addCarBreak(CarBreak carBreak) {
		this.addEntity(carBreak);
	}

	@Override
	public void updateCarBreak(CarBreak carBreak) {
		this.updateEntity(carBreak);
	}

	@Override
	public Page<CarBreak> queryCarBreakByPage(Page<CarBreak> page, String hql) {
		Page<CarBreak> resultPage=pageCache(CarBreak.class, page, hql);
		resultPage.setResults(idToObj(CarBreak.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public CarBreak queryCarBreakById(String id) {
		 return this.get(CarBreak.class, id);
	}

	@Override
	public void deleteCarBreakArrays(String[] ids) {
		String hql="delete CarBreak where subscriberId in (:ids)";
		this.deleteEntity(CarBreak.class, hql, ids);
	}

	@Override
	public List<CarBreak> queryCarBreakByHql(String hql) {
		return  this.getList(CarBreak.class,queryFList(hql));
	}


}
