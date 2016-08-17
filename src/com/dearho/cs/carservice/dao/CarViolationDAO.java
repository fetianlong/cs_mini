package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 车辆违章
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface CarViolationDAO {
	
	List<CarViolation> searchCarViolation(String hql);
	
	void addCarViolation(CarViolation carViolation);
	void updateCarViolation(CarViolation carViolation);
	void deleteCarViolation(final String[] checkdel);
	
	Page<CarViolation> searchCarViolation(Page<CarViolation> page,String hql);
	
	CarViolation searchCarViolationById(String id);
}
