package com.dearho.cs.carservice.dao;

import com.dearho.cs.carservice.pojo.CarCommon;
import com.dearho.cs.core.db.page.Page;

public interface CarCommonDao {

	/**
	 * 查询车辆评价
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<CarCommon> searchCarCommon(Page<CarCommon> page, String hql);

	CarCommon queryCarCommonById(String id);

}
