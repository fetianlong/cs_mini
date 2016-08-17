package com.dearho.cs.orders.pojo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dearho.cs.account.pojo.AccountTradeRecord;

/**
 * 订单详情
 * @author wangjing
 *
 */
public class OrdersDetail {

	/**主键ID*/
	private String id;
	/**主订单编号*/
	private String ordersNo;
	/**子订单编号*/
	private String ordersDetailNo;
	/**付款方式*/
	private Integer payType;
	/**总费用ID*/
	private BigDecimal totalFee;
	/**实际支付费用*/
	private BigDecimal actualFee;
	/**订单创建时间*/
	private Date createTime;
	/**订单开始时间*/
	private Date beginTime;
	/**订单开始时间字符*/
	private String beginTimeStr;
	/**订单结束时间*/
	private Date endTime;
	/**订单结束时间字符*/
	private String endTimeStr;
	/**计费规则ID*/
	private String strategyId;
	/**计费规则名称*/
	private String strategyTitle;
	/**消费记录ID*/
	private String tradeRecordId;
	/**是否超时*/
	private String isTimeout;
	/**对应超时策略ID*/
	private String timeoutStrategyid;
	/**关联罚单ID*/
	private String ticketsId;
	/**关联罚单金额*/
	private BigDecimal ticketsFee;
	/**是否在进行中*/
	private String isRunning;
	/**上笔订单ID*/
	private String preOrders;
	/**下笔订单ID*/
	private String nextOrders;
	/**是否预付费*/
	private String isPrePay;
	/**是否已经付款*/
	private String isPaid;
	/**注册折扣ID*/
	private String regDiscountId;
	/**租赁类型ID*/
	private String typeId;
	/**租赁类型名称*/
	private String typeName;
	/**标记记录超时的订单*/
	private String timeoutRemark;
	/**标记订单是否完结*/
	private String isOver;
	/**标记订单异常*/
	private String isException;
	/**主订单ID*/
	private String ordersId;
	/**创建人*/
	private String subscriberId;
	/**剩余时长*/
	private String restTimeStr;
	
	private  AccountTradeRecord accountTradeRecord;
	
	/**使用时长*/
	private String useTimeStr;
	
	/**里程数*/
	private BigDecimal mileage;
	
	/**里程费用*/
	private BigDecimal mileFee;
	
	/**时长费用*/
	private BigDecimal timeFee;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}
	public String getTradeRecordId() {
		return tradeRecordId;
	}
	public void setTradeRecordId(String tradeRecordId) {
		this.tradeRecordId = tradeRecordId;
	}
	public String getIsTimeout() {
		return isTimeout;
	}
	public void setIsTimeout(String isTimeout) {
		this.isTimeout = isTimeout;
	}
	public String getTicketsId() {
		return ticketsId;
	}
	public void setTicketsId(String ticketsId) {
		this.ticketsId = ticketsId;
	}
	public String getStrategyTitle() {
		return strategyTitle;
	}
	public void setStrategyTitle(String strategyTitle) {
		this.strategyTitle = strategyTitle;
	}
	public String getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(String isRunning) {
		this.isRunning = isRunning;
	}
	public String getPreOrders() {
		return preOrders;
	}
	public void setPreOrders(String preOrders) {
		this.preOrders = preOrders;
	}
	public String getIsPrePay() {
		return isPrePay;
	}
	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}
	public String getNextOrders() {
		return nextOrders;
	}
	public void setNextOrders(String nextOrders) {
		this.nextOrders = nextOrders;
	}
	public String getOrdersDetailNo() {
		return ordersDetailNo;
	}
	public void setOrdersDetailNo(String ordersDetailNo) {
		this.ordersDetailNo = ordersDetailNo;
	}
	public String getTimeoutStrategyid() {
		return timeoutStrategyid;
	}
	public void setTimeoutStrategyid(String timeoutStrategyid) {
		this.timeoutStrategyid = timeoutStrategyid;
	}
	public String getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}
	public BigDecimal getTotalFee() {
		if(totalFee != null){
			totalFee = new BigDecimal(totalFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		if(totalFee != null){
			totalFee = new BigDecimal(totalFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		this.totalFee = totalFee;
	}
	public BigDecimal getActualFee() {
		if(actualFee != null){
			actualFee = new BigDecimal(actualFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		return actualFee;
	}
	public void setActualFee(BigDecimal actualFee) {
		if(actualFee != null){
			actualFee = new BigDecimal(actualFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		this.actualFee = actualFee;
	}
	public String getRegDiscountId() {
		return regDiscountId;
	}
	public void setRegDiscountId(String regDiscountId) {
		this.regDiscountId = regDiscountId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTimeoutRemark() {
		return timeoutRemark;
	}
	public void setTimeoutRemark(String timeoutRemark) {
		this.timeoutRemark = timeoutRemark;
	}
	public String getIsOver() {
		return isOver;
	}
	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	public BigDecimal getTicketsFee() {
		if(ticketsFee != null){
			ticketsFee = new BigDecimal( ticketsFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		return ticketsFee;
	}
	public void setTicketsFee(BigDecimal ticketsFee) {
		if(ticketsFee != null){
			ticketsFee = new BigDecimal( ticketsFee.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		this.ticketsFee = ticketsFee;
	}
	public String getIsException() {
		return isException;
	}
	public void setIsException(String isException) {
		this.isException = isException;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getRestTimeStr() {
		if(endTime!=null){
			Date currentDate = new Date();
			Long ms = endTime.getTime()-currentDate.getTime();
			
		    Integer ss = 1000;  
		    Integer mi = ss * 60;  
		    Integer hh = mi * 60;  
		    Integer dd = hh * 24;  
		    
		    Long day = ms / dd;  
		    Long hour = (ms - day * dd) / hh;  
		    Long minute = (ms - day * dd - hour * hh) / mi;  
		    
		    StringBuffer sb = new StringBuffer("");  
		    if(day > 0) {  
		        sb.append(day+"天");  
		    }  
		    if(hour > 0) {  
		        sb.append(hour+"小时");  
		    }  
		    if(minute > 0) {  
		        sb.append(minute+"分钟");  
		    }  
		    if(sb.toString().length()==0) {  
		        sb.append("即将结束");  
		    }  
		    restTimeStr = sb.toString();
		    return restTimeStr; 
		}else{
			return ""; 
		}
		
	}
	public void setRestTimeStr(String restTimeStr) {
		this.restTimeStr = restTimeStr;
	}
	public String getBeginTimeStr() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(beginTime!=null){
			beginTimeStr = sdf.format(beginTime);
		}else{
			beginTimeStr = "";
		}
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public String getEndTimeStr() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(endTime!=null){
			endTimeStr = sdf.format(endTime);
		}else{
			endTimeStr = "";
		}
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public AccountTradeRecord getAccountTradeRecord() {
		return accountTradeRecord;
	}
	public void setAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		this.accountTradeRecord = accountTradeRecord;
	}
	public String getUseTimeStr() {
		if(beginTime!=null){
			Date currentDate = new Date();
			Long ms = currentDate.getTime()-beginTime.getTime();
			
		    Integer ss = 1000;  
		    Integer mi = ss * 60;  
		    Integer hh = mi * 60;  
		    Integer dd = hh * 24;  
		    
		    Long day = ms / dd;  
		    Long hour = (ms - day * dd) / hh;  
		    Long minute = (ms - day * dd - hour * hh) / mi;  
		    
		    StringBuffer sb = new StringBuffer("");  
		    if(day > 0) {  
		        sb.append(day+"天");  
		    }  
		    if(hour > 0) {  
		        sb.append(hour+"小时");  
		    }  
		    if(minute > 0) {  
		        sb.append(minute+"分钟");  
		    }  
		    
		    useTimeStr = sb.toString();
		    return useTimeStr; 
		}else{
			return ""; 
		}
		
	}
	public void setUseTimeStr(String useTimeStr) {
		this.useTimeStr = useTimeStr;
	}
	public BigDecimal getTimeFee() {
		return timeFee;
	}
	public void setTimeFee(BigDecimal timeFee) {
		this.timeFee = timeFee;
	}
	public BigDecimal getMileFee() {
		return mileFee;
	}
	public void setMileFee(BigDecimal mileFee) {
		this.mileFee = mileFee;
	}
	public BigDecimal getMileage() {
		return mileage;
	}
	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}
	
}
