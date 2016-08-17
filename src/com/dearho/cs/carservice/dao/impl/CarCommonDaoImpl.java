package com.dearho.cs.carservice.dao.impl;

import com.dearho.cs.carservice.dao.CarCommonDao;
import com.dearho.cs.carservice.pojo.CarCommon;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarCommonDaoImpl extends AbstractDaoSupport implements CarCommonDao{

	@Override
	public Page<CarCommon> searchCarCommon(Page<CarCommon> page,
			String hql) {
		Page<CarCommon> resultPage = pageCache(CarCommon.class ,page, hql);
		resultPage.setResults(idToObj(CarCommon.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public CarCommon queryCarCommonById(String id) {
		return get(CarCommon.class,id);
	}

}
