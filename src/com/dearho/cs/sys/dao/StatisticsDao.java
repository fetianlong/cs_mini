package com.dearho.cs.sys.dao;

import java.util.Date;
import java.util.Map;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.OrderStatistics;

public interface  StatisticsDao {
	public  Map<String,OrderStatistics>  orderFeeStatistics(String type, Date fromDate, Date toDate);
	

}
