package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 车辆违章管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CarViolationService {
	
	List<CarViolation> searchCarViolation(CarViolation carViolation);
	List<CarViolation> searchCarViolationCurrentDate();
	void addCarViolation(CarViolation carViolation);
	void updateCarViolation(CarViolation carViolation);
	void deleteCarViolation(final String[] checkdel);
	
	Page<CarViolation> searchCarViolation(Page<CarViolation> page,CarViolation carViolation);

	CarViolation searchCarViolationById(String id);

	/**
	* 查询近15天处理的违章
	* @param page
	* @param carViolation
	* @return
	* @return Page<CarViolation>
	*/
	Page<CarViolation> searchCarViolationNear15Days(Page<CarViolation> page,
			CarViolation carViolation);
}
