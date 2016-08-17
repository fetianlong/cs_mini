package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 车辆保险管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CarInsuranceService {
	
	List<CarInsurance> searchCarInsurance(CarInsurance carInsurance);
	
	void addCarInsurance(CarInsurance carInsurance);
	void updateCarInsurance(CarInsurance carInsurance);
	void deleteCarInsurance(final String[] checkdel);
	
	Page<CarInsurance> searchCarInsurance(Page<CarInsurance> page,CarInsurance carInsurance);

	CarInsurance searchCarInsuranceById(String id);
}
