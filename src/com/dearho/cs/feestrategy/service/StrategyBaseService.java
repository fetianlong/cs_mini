package com.dearho.cs.feestrategy.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.entity.StrategyCarShowInfo;
import com.dearho.cs.feestrategy.pojo.StrategyBase;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface StrategyBaseService {
	StrategyBase searchStrategyBaseById(String id);
	
	void addStrategyBase(StrategyBase strategyBase);
	
	void updateStrategyBase(StrategyBase strategyBase);
	
	Page<StrategyBase> searchStrategyBase(Page<StrategyBase> page,StrategyBase strategyBase);

	/**
	 * 根据策略名称查询正在使用的策略中有没有
	 * @param name
	 * @param isUsed
	 * @return
	 */
	StrategyBase getStrategyBaseByName(String name, int isUsed);

	void deleteStrategyBase(String[] checkdel);
	
	/**
	 * 计费
	 * @param strategyBaseId  策略id
	 * @param startTime       开始时间
	 * @param endTime         结束时间
	 * @param carId          租用车辆id
	 * @param subscriberId   租车人
	 * @return
	 */
	BigDecimal conMoney(String strategyBaseId, Date startTime, Date endTime,String subscriberId,String carId);

	/**
	 * 
	 * 根据时长和单位，计算总时间（单位毫秒）
	 * @param timeLong
	 * @param unitId
	 * @return
	 */
	BigDecimal getTimeLongByUnit(BigDecimal timeLong,String unitId);

	/**
	 * 根据车型查询策略展示信息
	 * @param modelId
	 * @return
	 */
	List<StrategyCarShowInfo> getShowInfo(String modelId);

	/**
	 * 计算里程费用
	 * @param strategyBaseId
	 * @param startTime
	 * @param endTime
	 * @param carId
	 * @return
	 */
	BigDecimal conMileFee(String strategyBaseId, Date startTime, Date endTime,
			String carId);

	/**
	 * 计算里程
	 * @param strategyBaseId
	 * @param startTime
	 * @param endTime
	 * @param carId
	 * @return
	 */
	BigDecimal conMile(String strategyBaseId, Date startTime, Date endTime,
			String carId);

	BigDecimal conTimeFee(String strategyId, Date beginTime, Date date,
			String carId);
}
