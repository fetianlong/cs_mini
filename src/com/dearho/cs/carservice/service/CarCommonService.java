package com.dearho.cs.carservice.service;

import com.dearho.cs.carservice.pojo.CarCommon;
import com.dearho.cs.core.db.page.Page;

public interface CarCommonService {

	/**
	 * 查询车辆评价
	 * @param page 
	 * @param carId
	 * @return
	 */
	Page<CarCommon> searchCarCommon(Page<CarCommon> page, String carId);

	/**
	 * 评价详情
	 * @param id
	 * @return
	 */
	CarCommon queryCarCommonById(String id);

}
