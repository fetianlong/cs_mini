package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 车辆事故
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface CarAccidentDAO {
	
	List<CarAccident> searchCarAccident(String hql);
	
	void addCarAccident(CarAccident carAccident);
	void updateCarAccident(CarAccident carAccident);
	void deleteCarAccident(final String[] checkdel);
	
	Page<CarAccident> searchCarAccident(Page<CarAccident> page,String hql);
	
	CarAccident searchCarAccidentById(String id);
}
