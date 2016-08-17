package com.dearho.cs.charge.service;

import java.util.List;

import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.core.db.page.Page;

public interface ChargeStationService {

	/**
	 * @Title:searchChargeStationByCode
	 * @Description:根据编码查询充电站，参数可以为空，则查询所有。
	 * @param code
	 * @return
	 * @throws
	 */
	List<ChargeStation> searchChargeStationByCode(String code);
	
	/**
	 * @Title:searchChargeStationByName
	 * @Description:根据名称模糊查询充电站，参数可以为空，则查询所有。
	 * @param name
	 * @return
	 * @throws
	 */
	List<ChargeStation> searchChargeStationByName(String name);
	
	void addChargeStation(ChargeStation chargeStation);
	void updateChargeStation(ChargeStation chargeStation);
	void deleteChargeStation(final String[] checkdel);
	
	Page<ChargeStation> searchChargeStation(Page<ChargeStation> page,ChargeStation chargeStation);

	ChargeStation searchChargeStationById(String id);

}
