package com.dearho.cs.report.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.corba.wsdl.Array;

import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.report.entity.IndexReportData;
import com.dearho.cs.report.service.IndexDataShowService;
import com.dearho.cs.report.util.ReportDateUtil;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.DateUtil;
import com.opensymphony.xwork.Action;

public class IndexDataShowAction extends AbstractAction{

	private static final long serialVersionUID = -4364917755111526766L;
	
	private IndexDataShowService indexDataShowService;

	@Override
	public String process() {
		
		//运营概况
		IndexReportData indexReportData = new IndexReportData();
		List<Object> loginNumberList = new ArrayList<Object>();
		loginNumberList.add(indexDataShowService.getLoginCount(ReportDateUtil.getDayData(1, 1),ReportDateUtil.getDayData(1, 2)));
		loginNumberList.add(indexDataShowService.getLoginCount(ReportDateUtil.getDayData(2, 1),new Date()));
		loginNumberList.add(indexDataShowService.getLoginCount(ReportDateUtil.getDayData(3, 1),new Date()));
		loginNumberList.add(indexDataShowService.getLoginCount(ReportDateUtil.getDayData(4, 1),new Date()));
		
		
		List<Object> regNumberList = new ArrayList<Object>();
		regNumberList.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getDayData(1, 1),ReportDateUtil.getDayData(1, 2)));
		regNumberList.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getDayData(2, 1),new Date()));
		regNumberList.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getDayData(3, 1),new Date()));
		regNumberList.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getDayData(4, 1),new Date()));
		
		List<Object> rechargeMoneyList = new ArrayList<Object>();
		rechargeMoneyList.add(indexDataShowService.getRechargeMoney(ReportDateUtil.getDayData(1, 1),ReportDateUtil.getDayData(1, 2)));
		rechargeMoneyList.add(indexDataShowService.getRechargeMoney(ReportDateUtil.getDayData(2, 1),new Date()));
		rechargeMoneyList.add(indexDataShowService.getRechargeMoney(ReportDateUtil.getDayData(3, 1),new Date()));
		rechargeMoneyList.add(indexDataShowService.getRechargeMoney(ReportDateUtil.getDayData(4, 1),new Date()));
		
		List<Object> orderNumList = new ArrayList<Object>();
		orderNumList.add(indexDataShowService.getOrderNums(ReportDateUtil.getDayData(1, 1),ReportDateUtil.getDayData(1, 2)));
		orderNumList.add(indexDataShowService.getOrderNums(ReportDateUtil.getDayData(2, 1),new Date()));
		orderNumList.add(indexDataShowService.getOrderNums(ReportDateUtil.getDayData(3, 1),new Date()));
		orderNumList.add(indexDataShowService.getOrderNums(ReportDateUtil.getDayData(4, 1),new Date()));
		
		List<Object> orderMoneyList = new ArrayList<Object>();
		orderMoneyList.add(indexDataShowService.getOrderMoney(ReportDateUtil.getDayData(1, 1),ReportDateUtil.getDayData(1, 2)));
		orderMoneyList.add(indexDataShowService.getOrderMoney(ReportDateUtil.getDayData(2, 1),new Date()));
		orderMoneyList.add(indexDataShowService.getOrderMoney(ReportDateUtil.getDayData(3, 1),new Date()));
		orderMoneyList.add(indexDataShowService.getOrderMoney(ReportDateUtil.getDayData(4, 1),new Date()));
		
		List<Object> orderHoursList = new ArrayList<Object>();
		orderHoursList.add(indexDataShowService.getOrderHours(ReportDateUtil.getDayData(1, 1),ReportDateUtil.getDayData(1, 2)));
		orderHoursList.add(indexDataShowService.getOrderHours(ReportDateUtil.getDayData(2, 1),new Date()));
		orderHoursList.add(indexDataShowService.getOrderHours(ReportDateUtil.getDayData(3, 1),new Date()));
		orderHoursList.add(indexDataShowService.getOrderHours(ReportDateUtil.getDayData(4, 1),new Date()));
		
		List<List<Object>> operateSurvey = new ArrayList<List<Object>>();
		operateSurvey.add(loginNumberList);
		operateSurvey.add(regNumberList);
		operateSurvey.add(rechargeMoneyList);
		operateSurvey.add(orderNumList);
		operateSurvey.add(orderMoneyList);
		operateSurvey.add(orderHoursList);
		indexReportData.setOperateSurvey(operateSurvey);
		
		//数据统计
		List<Integer> regMemberCount = new ArrayList<Integer>();
		regMemberCount.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getMonthDate(1,1, 6), ReportDateUtil.getMonthDate(2,2, 6),Subscriber.STATE_NORMAL));
		regMemberCount.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getMonthDate(1,1, 5), ReportDateUtil.getMonthDate(2,2, 5),Subscriber.STATE_NORMAL));
		regMemberCount.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getMonthDate(1,1, 4), ReportDateUtil.getMonthDate(2,2, 4),Subscriber.STATE_NORMAL));
		regMemberCount.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getMonthDate(1,1, 3), ReportDateUtil.getMonthDate(2,2, 3),Subscriber.STATE_NORMAL));
		regMemberCount.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getMonthDate(1,1, 2), ReportDateUtil.getMonthDate(2,2, 2),Subscriber.STATE_NORMAL));
		regMemberCount.add(indexDataShowService.getRegMemberCount(ReportDateUtil.getMonthDate(1,1, 1), ReportDateUtil.getMonthDate(2,2, 1),Subscriber.STATE_NORMAL));
		
		List<Integer> orderCount = new ArrayList<Integer>();
		orderCount.add(indexDataShowService.getOrderNums(ReportDateUtil.getMonthDate(1,1, 6), ReportDateUtil.getMonthDate(2,2, 6)));
		orderCount.add(indexDataShowService.getOrderNums(ReportDateUtil.getMonthDate(1,1, 5), ReportDateUtil.getMonthDate(2,2, 5)));
		orderCount.add(indexDataShowService.getOrderNums(ReportDateUtil.getMonthDate(1,1, 4), ReportDateUtil.getMonthDate(2,2, 4)));
		orderCount.add(indexDataShowService.getOrderNums(ReportDateUtil.getMonthDate(1,1, 3), ReportDateUtil.getMonthDate(2,2, 3)));
		orderCount.add(indexDataShowService.getOrderNums(ReportDateUtil.getMonthDate(1,1, 2), ReportDateUtil.getMonthDate(2,2, 2)));
		orderCount.add(indexDataShowService.getOrderNums(ReportDateUtil.getMonthDate(1,1, 1), ReportDateUtil.getMonthDate(2,2, 1)));

		
		indexReportData.setRegMemberCount(regMemberCount);
		indexReportData.setOrderCount(orderCount);
		
		//x轴数据
		List<String> xaxis = new ArrayList<String>(6);
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		String str2 = DateUtil.formatDate(c.getTime(), "yyyy年MM月");
		xaxis.add(DateUtil.formatDate(c.getTime(), "yyyy-MM"));
		for(int i = 0;i < 4; i++){
			c.add(Calendar.MONTH, -1);
			xaxis.add(DateUtil.formatDate(c.getTime(), "yyyy-MM"));
		}
		
		c.add(Calendar.MONTH, -1);
		String str1 = DateUtil.formatDate(c.getTime(), "yyyy年MM月");
		//标题
		indexReportData.setDataStatisticTitle("("+str1+" 至 "+str2+")");
		xaxis.add(DateUtil.formatDate(c.getTime(), "yyyy-MM"));
		Collections.reverse(xaxis);
		indexReportData.setXaxis(xaxis);
		
		Map<String, Integer> dataSurvey = new HashMap<String, Integer>();
		dataSurvey.put("allRegMember", indexDataShowService.getRegMemberCount(null, null));
		dataSurvey.put("auditMember", indexDataShowService.getRegMemberCount(null, null,3));
		dataSurvey.put("noAuditMember", indexDataShowService.getRegMemberCount(null, null,2));
//		dataSurvey.put("near7DayRegMember", indexDataShowService.getRegMemberCount(ReportDateUtil.getOtherDay(new Date(),-7, 1), new Date()));
//		dataSurvey.put("near7DayAuditMember", indexDataShowService.getBizFlow(1,1,getOtherDay(-7, 1), new Date()));
//		dataSurvey.put("near7DayRefundMember", indexDataShowService.getBizFlow(2,1,ReportDateUtil.getOtherDay(new Date(),-7, 1), new Date()));
		
		dataSurvey.put("allareaDotCount", indexDataShowService.getAreaDotCount());
		dataSurvey.put("allDotCount", indexDataShowService.getDotCount(1));
		dataSurvey.put("allChargeStationCount", indexDataShowService.getChargeStationCount());
//		dataSurvey.put("near3monthParkingCount", indexDataShowService.getDotCount(1));
//		dataSurvey.put("near7dayParkingCount", indexDataShowService.getDotCount(1));
		
		dataSurvey.put("allCarCount", indexDataShowService.getCarCount(0));
		dataSurvey.put("onlineCarCount", indexDataShowService.getCarCount(1));
		dataSurvey.put("weizujieCarCount", indexDataShowService.getCarCount(11));
		dataSurvey.put("yizujieCarCount", indexDataShowService.getCarCount(12));
		dataSurvey.put("xiaxianCarCount", indexDataShowService.getCarCount(13));
		dataSurvey.put("weixiuCarCount", indexDataShowService.getCarCount(14));
		dataSurvey.put("tzsyCarCount", indexDataShowService.getCarCount(15));
		dataSurvey.put("jqx7DayCarCount", indexDataShowService.getCarCount(4));
		
		
		dataSurvey.put("alljxzOrderCount", indexDataShowService.getOrderCount(Orders.STATE_ORDER_START,null,null,null));
		Dict jszdict = DictUtil.getDictByCodes("strategyBaseType", "jishizu");
		if(jszdict != null){
			dataSurvey.put("szjxzOrderCount", indexDataShowService.getOrderCount(Orders.STATE_ORDER_START,jszdict.getId(),null,null));
			dataSurvey.put("szywjOrderCount", indexDataShowService.getOrderCount(Orders.STATE_ORDER_END,jszdict.getId(),ReportDateUtil.getDayData(2, 1),ReportDateUtil.getDayData(2, 2)));
		}
		Dict rzdict = DictUtil.getDictByCodes("strategyBaseType", "rizu");
		if(rzdict != null){
			dataSurvey.put("rzjxzOrderCount", indexDataShowService.getOrderCount(Orders.STATE_ORDER_START,rzdict.getId(),null,null));
			dataSurvey.put("rzywjOrderCount", indexDataShowService.getOrderCount(Orders.STATE_ORDER_END,rzdict.getId(),ReportDateUtil.getDayData(2, 1),ReportDateUtil.getDayData(2, 2)));
		}
		
		
		
		dataSurvey.put("accidentCount", indexDataShowService.getCarCount(6));
		dataSurvey.put("violationCarCount", indexDataShowService.getCarCount(7));
		
		
		indexReportData.setDataSurveyMap(dataSurvey);
		
		result = Ajax.JSONResult(0, "", indexReportData);
		return Action.SUCCESS;
	}
	
	
	public IndexDataShowService getIndexDataShowService() {
		return indexDataShowService;
	}
	public void setIndexDataShowService(
			IndexDataShowService indexDataShowService) {
		this.indexDataShowService = indexDataShowService;
	}
	
	
}
