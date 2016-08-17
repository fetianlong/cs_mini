package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 车辆事故管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CarAccidentService {
	
	List<CarAccident> searchCarAccident(CarAccident carAccident);
	List<CarAccident> searchCarAccidentCurrentDate();
	void addCarAccident(CarAccident carAccident);
	void updateCarAccident(CarAccident carAccident);
	void deleteCarAccident(final String[] checkdel);
	
	Page<CarAccident> searchCarAccident(Page<CarAccident> page,CarAccident carAccident);

	CarAccident searchCarAccidentById(String id);
}
