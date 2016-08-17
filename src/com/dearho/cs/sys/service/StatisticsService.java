package com.dearho.cs.sys.service;

import java.util.Date;
import java.util.Map;

import com.dearho.cs.sys.pojo.OrderStatistics;

public interface StatisticsService {

	public   Map<String,OrderStatistics> orderFeeStatistics(String type, Date fromDate, Date toDate);
	

	
}
