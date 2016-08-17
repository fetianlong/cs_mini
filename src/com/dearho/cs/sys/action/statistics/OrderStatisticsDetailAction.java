package com.dearho.cs.sys.action.statistics;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.service.StatisticsService;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;


public class OrderStatisticsDetailAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StatisticsService statisticsService;
	private OrdersService ordersService;
	private SubscriberService subscriberService;
	
	private Page<Orders> page;
	

	private String unitTime;
	public OrderStatisticsDetailAction(){
		super();
		page= new Page<Orders>();
		page.setCountField("a.id");
		page.setCurrentPage(1);
	}
	
	public String process() {
		
		if(StringUtils.isEmpty(unitTime)){
			return SUCCESS;
		}
		String startTime=null;
		String endTime=null;
		if(unitTime.length()==8){
			 try {
				 SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
				 
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(DateUtil.parseDate(unitTime, "yyyyMMdd"));
				 calendar.add(Calendar.DATE, 1); 
				
				 endTime= time.format(calendar.getTime());
				 startTime=unitTime;

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(unitTime.length()==6){
			 try {
				 SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
				 
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(DateUtil.parseDate(unitTime, "yyyyMM"));
				 calendar.add(Calendar.MONTH, 1); 
				
				 endTime= time.format(calendar.getTime());
				 startTime=unitTime+"01";

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Orders orders =new Orders();
		orders.setState(Orders.STATE_ORDER_END);
		page=ordersService.queryOrdersPageByEndTime(page, orders, startTime, endTime);
		 BigDecimal totelFee = new BigDecimal(0d);
		if(page!=null && page.getResults()!=null && page.getResults().size()>0){
			for(int i=0;i<page.getResults().size();i++){
				Orders order=(Orders)page.getResults().get(i);
				if(order.getTotalFee()!=null){
					totelFee=totelFee.add(order.getTotalFee());
				}
				
				
				if(!StringHelper.isRealNull(order.getMemberId())){
					order.setMemberName(getMemberName(order.getMemberId()));
				}
			}
			
		}
		getRequest().setAttribute("totelFee", totelFee);
		
	        
		return SUCCESS;
	}


	public String getMemberName(String id){
		Subscriber subscriber=subscriberService.querySubscriberById(id);
		if(subscriber!=null){
			return subscriber.getName();
		}
		return null;
	}
	
	public StatisticsService getStatisticsService() {
		return statisticsService;
	}


	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}


	public static void main(String[] args) throws Exception {
		 SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(DateUtil.parseDate("201512", "yyyyMM"));
		 calendar.add(Calendar.MONTH, 1); 
		
		System.out.println(time.format(calendar.getTime()));

	}


	public String getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(String unitTime) {
		this.unitTime = unitTime;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public Page<Orders> getPage() {
		return page;
	}

	public void setPage(Page<Orders> page) {
		this.page = page;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
	
	


}
