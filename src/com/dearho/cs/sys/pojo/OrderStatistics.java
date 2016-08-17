package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderStatistics implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@FieldComment("日期") 
	private String day;
	@FieldComment("总订单数 ") 
	private Integer orderTotalNum;
	
	@FieldComment("总金额") 
	private Double orderTotalFee;
	
	@FieldComment("时租订单数") 
	private Integer hourOrdersTotalNum;//时租订单总数
	
	@FieldComment("时租总金额 ") 
	private Double hourOrdersTotalFee;//时租订单总金额
	
	@FieldComment("日租订单数") 
	private Integer dayOrdersTotalNum;//日租订单总数
	
	@FieldComment("日租总金额 ") 
	private Double dayOrdersTotalFee;//日租订单总金额
	
	
	
	
	public Integer getOrderTotalNum() {
		return orderTotalNum;
	}
	public void setOrderTotalNum(Integer orderTotalNum) {
		this.orderTotalNum = orderTotalNum;
	}
	public Integer getHourOrdersTotalNum() {
		return hourOrdersTotalNum;
	}
	public void setHourOrdersTotalNum(Integer hourOrdersTotalNum) {
		this.hourOrdersTotalNum = hourOrdersTotalNum;
	}
	public Integer getDayOrdersTotalNum() {
		return dayOrdersTotalNum;
	}
	public void setDayOrdersTotalNum(Integer dayOrdersTotalNum) {
		this.dayOrdersTotalNum = dayOrdersTotalNum;
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Double getOrderTotalFee() {
		return orderTotalFee;
	}
	public void setOrderTotalFee(Double orderTotalFee) {
		this.orderTotalFee = orderTotalFee;
	}
	public Double getHourOrdersTotalFee() {
		return hourOrdersTotalFee;
	}
	public void setHourOrdersTotalFee(Double hourOrdersTotalFee) {
		this.hourOrdersTotalFee = hourOrdersTotalFee;
	}
	public Double getDayOrdersTotalFee() {
		return dayOrdersTotalFee;
	}
	public void setDayOrdersTotalFee(Double dayOrdersTotalFee) {
		this.dayOrdersTotalFee = dayOrdersTotalFee;
	}
	
	
	
	

}
