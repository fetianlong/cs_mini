package com.dearho.cs.report.service;

import java.util.Date;

public interface IndexDataShowService {

	/**
	*  根据时间段和状态，查询用户数量
	* @param startDate
	* @param endDate
	* @param states
	* @return void
	*/
	Integer getRegMemberCount(Date startDate, Date endDate, Integer... states);

	/**
	* 根据时间段查询用户登录数量
	* @param startDate
	* @param endDate
	* @return
	* @return Integer
	*/
	Integer getLoginCount(Date startDate, Date endDate);

	/**
	* 根据时间段查询充值金额
	* @param startDate
	* @param endDate
	* @return
	* @return Double
	*/
	Double getRechargeMoney(Date startDate, Date endDate);

	/**
	* 根据时间段查询订单数量
	* @param startDate
	* @param  endDate
	* @return
	* @return Integer
	*/
	Integer getOrderNums(Date startDate, Date endDate);

	/**
	* 根据时间段查询订单金额
	* @param startDate
	* @param endDate
	* @return
	* @return Double
	*/
	Double getOrderMoney(Date startDate, Date endDate);

	/**
	* 根据时间段查询订单总时长
	* @param startDate
	* @param endDate
	* @return
	* @return Double
	*/
	Double getOrderHours(Date startDate, Date endDate);

	/**
	* 根据时间段，业务类型，结果，查询业务流程
	* @param bizType
	* @param result
	* @param startDate
	* @param endDate
	* @return
	* @return Integer
	*/
	Integer getBizFlow(Integer bizType, Integer result, Date startDate, Date endDate);

	/**
	* 查询合约日期在最终日期在之前的网点数量
	* @param contractDate
	* @return
	* @return Integer
	*/
	Integer getDotCount(int isActive);


	/**
	* 查询所有网点数量
	* @return
	* @return Integer
	*/
	Integer getAreaDotCount();

	/**
	* 根据类型查询车辆数量
	* @param i
	* @return
	* @return Integer
	*/
	Integer getCarCount(int type);

	/**
	* 根据订单状态和策略类型查询订单数量,如果是已完结订单，则可以根据时间进行查询
	* @param stateDictId
	* @param strategyType
	* @param startTime
	* @param endTime
	* @return
	* @return Integer
	*/
	Integer getOrderCount(String stateDictId,String strategyType,Date startTime,Date endTime);

	/**
	 * 查询充电站数量
	 * @return
	 */
	Integer getChargeStationCount();

}
