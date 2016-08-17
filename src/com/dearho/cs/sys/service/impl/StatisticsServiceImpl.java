package com.dearho.cs.sys.service.impl;

import java.util.Date;
import java.util.Map;

import com.dearho.cs.sys.dao.StatisticsDao;
import com.dearho.cs.sys.pojo.OrderStatistics;
import com.dearho.cs.sys.service.StatisticsService;

public class StatisticsServiceImpl implements StatisticsService {

	
	private StatisticsDao statisticsDao;
	
	public   Map<String,OrderStatistics> orderFeeStatistics(String type,Date fromDate, Date toDate){
		return statisticsDao.orderFeeStatistics(type, fromDate,  toDate);
	}



	

	public StatisticsDao getStatisticsDao() {
		return statisticsDao;
	}

	public void setStatisticsDao(StatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}





	
	
}
