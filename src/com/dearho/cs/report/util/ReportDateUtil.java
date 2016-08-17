package com.dearho.cs.report.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.dearho.cs.util.DateUtil;

public class ReportDateUtil {

	/**
	* 获取几天前或几天后的日期
	* @param day     基础日期
	* @param dayIndex  日期差别
	* @param seType    类型  1代表0点  其他代表23点
	* @return
	* @return Date
	*/
	public static Date getOtherDay(Date day,int dayIndex,int seType){
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.add(Calendar.DAY_OF_YEAR, dayIndex);
		Date d = c.getTime();
		String str = DateUtil.formatDate(d, "yyyy-MM-dd");
		if(1 == seType){
			str += " 00:00:00";
		}
		else{
			str += " 23:59:59";
		}
		try {
			return DateUtil.parseDate(str, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* 获取几个月前或几个月后的日期
	* @param monthIndex 月份差别
	* @param seType  类型  1代表0点  其他代表23点
	* @return
	* @return Date
	*/
	public static Date getOtherMonth(int monthIndex,int seType){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, monthIndex);
		Date d = c.getTime();
		String str = DateUtil.formatDate(d, "yyyy-MM-dd");
		if(1 == seType){
			str += " 00:00:00";
		}
		else{
			str += " 23:59:59";
		}
		try {
			return DateUtil.parseDate(str, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* 获取相应类型的日期
	* @param dayType  1昨天  2今天  3本周  4本月
	* @param seType  类型  1代表0点  其他代表23点
	* @return
	* @return Date
	*/
	public static Date getDayData(int dayType,int seType) {
		Calendar c = Calendar.getInstance();
		String str = "";
		if(1 == dayType){
			c.setTime(new Date());
			c.add(Calendar.DAY_OF_YEAR, -1);
			Date d = c.getTime();
			str = DateUtil.formatDate(d, "yyyy-MM-dd");
			
		}
		else if(2 == dayType){
			Date d = new Date();
			str = DateUtil.formatDate(d, "yyyy-MM-dd");
		}
		else if(3 == dayType){
			c.setTime(new Date());
			if(c.get(Calendar.DAY_OF_WEEK) == 1){
				c.add(Calendar.DAY_OF_YEAR, -7);
			}
			int thisDay = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DAY_OF_WEEK, 0 - thisDay + 2);
			Date d = c.getTime();
			str = DateUtil.formatDate(d, "yyyy-MM-dd");
		}
		else if(4 == dayType){
			c.setTime(new Date());
			int thisDay = c.get(Calendar.DAY_OF_MONTH);
			c.add(Calendar.DAY_OF_MONTH, 0 - thisDay + 1);
			Date d = c.getTime();
			str = DateUtil.formatDate(d, "yyyy-MM-dd");
		}
		
		if(1 == seType){
			str += " 00:00:00";
		}
		else{
			str += " 23:59:59";
		}
		
		try {
			return DateUtil.parseDate(str, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* 获取距离现在几个月前的时间
	* @param monthType  月份类型  1代表月初  其他代表月末
	* @param timeType   时间类型  1代表0点  其他代表23点
	* @param count      月份间隔，第几个月以前
	* @return
	* @return Date
	*/
	public static Date getMonthDate(int monthType,int timeType,int count){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.get(Calendar.MONTH);
		
		if(1 == monthType){
			calendar.add(Calendar.MONTH, 0-count);
			calendar.add(Calendar.DAY_OF_MONTH, 0 - calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		else{
			calendar.add(Calendar.MONTH, 0-count+1);
			calendar.add(Calendar.DAY_OF_MONTH, 0 - calendar.get(Calendar.DAY_OF_MONTH)) ;
		}
		try {
			String str = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");
			if(1 == timeType){
				str += " 00:00:00";
			}
			else{
				str += " 23:59:59";
			}
			calendar.setTime(DateUtil.parseDate(str, "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.getTime();
	}
	public static void main(String[] args) throws ParseException {
//		System.out.println(DateUtil.formatDate(i.getDayData(3, 1),"yyyy-MM-dd HH"));
		System.out.println(DateUtil.formatDate(getMonthDate(2,2, 1),"yyyy-MM-dd HH"));
	}

	
}
