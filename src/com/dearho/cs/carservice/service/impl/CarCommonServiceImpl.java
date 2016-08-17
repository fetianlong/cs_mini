package com.dearho.cs.carservice.service.impl;

import com.dearho.cs.carservice.dao.CarCommonDao;
import com.dearho.cs.carservice.pojo.CarCommon;
import com.dearho.cs.carservice.service.CarCommonService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

public class CarCommonServiceImpl implements CarCommonService{

	private CarCommonDao carCommonDao;

	public CarCommonDao getCarCommonDao() {
		return carCommonDao;
	}

	public void setCarCommonDao(CarCommonDao carCommonDao) {
		this.carCommonDao = carCommonDao;
	}

	@Override
	public Page<CarCommon> searchCarCommon(Page<CarCommon> page,
			String carId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from CarCommon a where 1=1 ");
		if(StringHelper.isNotEmpty(carId)){
			sb.append(" and a.carId = '"+carId+"'");
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.ts desc" : "order by a."+page.getOrderByString().replace("order by", ""));
		return carCommonDao.searchCarCommon(page,sb.toString());
	}

	@Override
	public CarCommon queryCarCommonById(String id) {
		return carCommonDao.queryCarCommonById(id);
	}

	 
	
}
