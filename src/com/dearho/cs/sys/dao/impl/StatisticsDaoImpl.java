package com.dearho.cs.sys.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.sys.dao.StatisticsDao;
import com.dearho.cs.sys.pojo.OrderStatistics;
import com.dearho.cs.util.DateUtil;

public class StatisticsDaoImpl extends AbstractDaoSupport implements  StatisticsDao{

	
	
	public  Map<String,OrderStatistics>  orderFeeStatistics(String type,Date fromDate, Date toDate){
			StringBuffer hql=new StringBuffer();
			if("day".equals(type)){
				 
				hql.append("select  DATE_FORMAT(o.end_time,'%Y%m%d') as days, sum(o.total_fee)as orderFee ,");
				hql.append("count(*) as orderNum, ");
				hql.append("CASE d.type_id ");
				hql.append("WHEN \"402881ed5118bdc4015118c24bb00002\" THEN \"day\" ");
				hql.append("WHEN \"402881ed5118bdc4015118c1c8e20001\" THEN \"hour\" ");
				hql.append("END AS orderType ");
				hql.append("from  ord_orders o,ord_orders_detail d  ");
				hql.append("where o.first_detail_no =d.orders_detail_no ");
				hql.append("and o.state=4 ");  
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if(fromDate!=null){
					hql.append(" and o.end_time > '").append(format.format(fromDate)).append(" 00:00:00'");
				}
				if(toDate!=null){
					Calendar calendar =Calendar.getInstance();
					calendar.setTime(toDate);
					calendar.add(Calendar.DATE, 1);
					hql.append(" and o.end_time  <='"+format.format(calendar.getTime())+" 00:00:00'");
				}
				hql.append("group by d.type_id  order by days desc");
			
			}else {

				hql.append("select  DATE_FORMAT(o.end_time,'%Y%m') as days, sum(o.total_fee)as orderFee ,");
				hql.append("count(*) as orderNum, ");
				hql.append("CASE d.type_id ");
				hql.append("WHEN \"402881ed5118bdc4015118c24bb00002\" THEN \"day\" ");
				hql.append("WHEN \"402881ed5118bdc4015118c1c8e20001\" THEN \"hour\" ");
				hql.append("END AS orderType ");
				hql.append("from  ord_orders o,ord_orders_detail d  ");
				hql.append("where o.first_detail_no =d.orders_detail_no ");
				hql.append("and o.state=4 ");  
				SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
				if(fromDate!=null){
					hql.append(" and o.end_time > '").append(format.format(fromDate)).append("01 00:00:00'");
				}
				if(toDate!=null){
					Calendar calendar =Calendar.getInstance();
					calendar.setTime(toDate);
					calendar.add(Calendar.MONTH, 1);
					hql.append(" and o.end_time  <'"+format.format(calendar.getTime())+"01 00:00:00'");
				}
				hql.append("group by d.type_id order by days desc");
			}
			
			Map<String,OrderStatistics> map=new TreeMap<String,OrderStatistics>();
			
		
			List list=getSession().createSQLQuery(hql.toString()).list();
			if(list!=null && list.size()>0){
				
				for(int i=0;i<list.size();i++){
					Object[] m=(Object[])list.get(i);
					String day=(String)m[0];
					Double orderFee=((java.math.BigDecimal)m[1]).doubleValue();
					Integer orderNum=((java.math.BigInteger)m[2]).intValue();
					String ordersType=(String)m[3];
					
					OrderStatistics orderStatistics=map.get(day);
					if(orderStatistics==null){
						orderStatistics = new OrderStatistics();
						orderStatistics.setDay(day);
					}
					if("hour".equals(ordersType)){
						orderStatistics.setHourOrdersTotalFee(orderFee);
						orderStatistics.setHourOrdersTotalNum(orderNum);
					}else if("day".equals(ordersType)){
						orderStatistics.setDayOrdersTotalFee(orderFee);
						orderStatistics.setDayOrdersTotalNum(orderNum);
					}
					map.put(day, orderStatistics);
				}
				
			}
			
			for (Map.Entry<String, OrderStatistics> entry : map.entrySet()) {
					OrderStatistics orderStatistics= entry.getValue();
					if(orderStatistics.getDayOrdersTotalFee()==null){
						orderStatistics.setDayOrdersTotalFee(0d);
					}
					if(orderStatistics.getHourOrdersTotalFee()==null){
						orderStatistics.setHourOrdersTotalFee(0d);
					}
					if(orderStatistics.getDayOrdersTotalNum()==null){
						orderStatistics.setDayOrdersTotalNum(0);
					}
					if(orderStatistics.getHourOrdersTotalNum()==null){
						orderStatistics.setHourOrdersTotalNum(0);
					}
					BigDecimal b1 = new BigDecimal(orderStatistics.getDayOrdersTotalFee());  
					BigDecimal b2 = new BigDecimal(orderStatistics.getHourOrdersTotalFee());
					orderStatistics.setOrderTotalFee(b1.add(b2).doubleValue());  
					  
					orderStatistics.setOrderTotalNum(orderStatistics.getDayOrdersTotalNum()+orderStatistics.getHourOrdersTotalNum());
				}
			
			return map;
		
	}


	
	
	
}
