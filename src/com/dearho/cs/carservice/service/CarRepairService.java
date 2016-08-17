package com.dearho.cs.carservice.service;

import java.util.List;

import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.core.db.page.Page;

/**
 * @author GaoYunpeng
 * @Description 车辆维修管理业务
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CarRepairService {
	
	List<CarRepair> searchCarRepair(CarRepair carRepair);
	
	void addCarRepair(CarRepair carRepair);
	void updateCarRepair(CarRepair carRepair);
	void deleteCarRepair(final String[] checkdel);
	
	Page<CarRepair> searchCarRepair(Page<CarRepair> page,CarRepair carRepair);

	CarRepair searchCarRepairById(String id);

	List<CarRepair> searchCarRepairCurrentDate();
}
