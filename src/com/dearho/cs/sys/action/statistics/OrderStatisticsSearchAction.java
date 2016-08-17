package com.dearho.cs.sys.action.statistics;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.OrderStatistics;
import com.dearho.cs.sys.service.StatisticsService;
import com.dearho.cs.util.ExcelUtil;


public class OrderStatisticsSearchAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StatisticsService statisticsService;
	
	private Page<OrderStatistics> page = new Page<OrderStatistics>();
	 Map<String,OrderStatistics> orderStatisticsMap;

	
	private String  type;
	private Date  fromDate;
	private Date  toDate;
	private String downLoadDateStr;
	public OrderStatisticsSearchAction(){
		super();
		type="day";
		page.setCurrentPage(1);
		page.setCountField("unitTime");
	}
	
	public String process() {
		
		
		Date today=null;
		Date lateMonth=null;
		
		Calendar calendar= Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		lateMonth=calendar.getTime();
        
		today=new Date();
		
		if("month".equals(type)){
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			String fromDateStr= getRequest().getParameter("fromDate");
			String toDateStr= getRequest().getParameter("toDate");
			
			downLoadDateStr=fromDateStr+"_"+toDateStr;
			if(StringUtils.isNotEmpty(fromDateStr)){
				try {
					fromDate=sdf.parse(fromDateStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(StringUtils.isNotEmpty(toDateStr)){
				try {
					toDate=sdf.parse(toDateStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			if(fromDate==null){
				fromDate=lateMonth;
			}
			if(toDate==null){
				toDate=today;
			}
			
			downLoadDateStr=transDate10String(fromDate)+"_"+transDate10String(toDate);
		}
		
		

		
		orderStatisticsMap=statisticsService.orderFeeStatistics(type,fromDate,toDate);
		return SUCCESS;
	}
	
	public void download(){
		
		process();
		
		List<Object> objList = new ArrayList<Object>();
		
		for (Map.Entry<String, OrderStatistics> entry : orderStatisticsMap.entrySet()) {
			OrderStatistics orderStatistics =entry.getValue();
			orderStatistics.setDay(entry.getKey());
			objList.add(orderStatistics);
		}
		
		ExcelUtil.exportExcel(objList, "订单统计"+downLoadDateStr+"_"+new Date().getTime());
		
		
	}


	public StatisticsService getStatisticsService() {
		return statisticsService;
	}


	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}


	public Page<OrderStatistics> getPage() {
		return page;
	}


	public void setPage(Page<OrderStatistics> page) {
		this.page = page;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public Map<String, OrderStatistics> getOrderStatisticsMap() {
		return orderStatisticsMap;
	}

	public void setOrderStatisticsMap(Map<String, OrderStatistics> orderStatisticsMap) {
		this.orderStatisticsMap = orderStatisticsMap;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	



}
