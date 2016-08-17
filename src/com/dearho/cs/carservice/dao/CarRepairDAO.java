package com.dearho.cs.carservice.dao;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.core.db.page.Page;
/**
 * @author GaoYunpeng
 * @Description 车辆维修
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface CarRepairDAO {
	
	List<CarRepair> searchCarRepair(String hql);
	
	void addCarRepair(CarRepair carRepair);
	void updateCarRepair(CarRepair carRepair);
	void deleteCarRepair(final String[] checkdel);
	
	Page<CarRepair> searchCarRepair(Page<CarRepair> page,String hql);
	
	CarRepair searchCarRepairById(String id);
}
