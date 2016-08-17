package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 车辆保险
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface CarInsuranceDAO {
	
	List<CarInsurance> searchCarInsurance(String hql);
	
	void addCarInsurance(CarInsurance carInsurance);
	void updateCarInsurance(CarInsurance carInsurance);
	void deleteCarInsurance(final String[] checkdel);
	
	Page<CarInsurance> searchCarInsurance(Page<CarInsurance> page,String hql);
	
	CarInsurance searchCarInsuranceById(String id);
}
