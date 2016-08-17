/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file Orders.java creation date: [2015年6月1日 下午3:50:07] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dearho.cs.car.pojo.Car;

/**
 * @author lvlq
 * @Description:(订单表)
 * @Version 1.0, 2015年6月1日
 */
public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String STATE_BOOKING_CANCEL="0";
	public static final String STATE_BOOKING_SUCCESS="1";
	public static final String STATE_BOOKING_TIMEOUT="2";
	public static final String STATE_ORDER_START="3";
	public static final String STATE_ORDER_END="4";
	
	
	
	public static final Integer CHANNEL_WECHAT=1;
	public static final Integer CHANNEL_WEB=2;
	
	//是否需要发票
	public static final Integer IS_BILL_TRUE=1;
	public static final Integer IS_BILL_FALSE=0;
	

	/**主键ID*/
	private String id;
	/**预定取车时间*/
	private Date ordersTime;
	/**订单编号*/
	private String ordersNo;
	/**车辆iD*/
	private String carId;
	/**车牌号*/
	private String plateNumber;
	/**车型号*/
	private String vehicleModelName;
	/**实际取车时间*/
	private Date beginTime;
	/**取车地点*/
	private String beginSiteId;
	/**预计还车时间*/
	private Date ordersBackTime;
	private String ordersBackTimeStr;
	
	/**预计还车地点*/
	private String ordersBackSiteId;
	/**实际还车时间*/
	private Date endTime;
	/**实际还车地点*/
	private String endSiteId;
	/**用车时长*/
	private Integer ordersDuration;
	/**总费用*/
	private BigDecimal totalFee;
	/**实际支付金额=总费用-优惠券*/
	private BigDecimal actualFee;
	/**第三方实际支付金额*/
	private BigDecimal tposPayFee;
	/**账户实际支付金额*/
	private BigDecimal useBalance;
	/**优惠券*/
	private BigDecimal coupon_fee;
	/**订单状态*/
	private String state;
	/**订单状态名称*/
	private String stateName;
	
	/**下单人ID*/
	private String memberId;
	/**下单人名字*/
	private String memberName;
	/**下单人电话号*/
	private String memberPhoneNo;
	/**下单时间*/
	private Date createDate;
	
	/**订单来源*/
	private String source;
	/**订单是否异常 0 为正常 1为异常*/
	private Integer isAbnormity=0;
	/**订单是否评价 0 为未评价 1为评价*/
	private Integer isAppraise=0;
	/**订单结束操作人*/
	private String ordersEndOperater;
	/**操作时间*/
	private Date operateDate;
	/**时间戳*/
	private Date ts;
	/**支付方式*/
	private String payStyle;
	/**支付方式名称*/
	private String payStyleName;
	/**是否需要开发票 0 是不需要 1 是需要*/
	private Integer isBill;
	
	private String billId;
	
	private Car car;
	private OrdersBill ordersBill;
	
	private String ordersTimeStr;
	private String beginTimeStr;
	private String endTimeStr;
	
	/**是否开了发票*/
	private Integer isOpenBill;
	
	private Integer channel;
	
	private List<OrdersDetail> ordersDetail;
	
	/**
	 * 当前订单所执行的子订单ID
	 */
	private String runningDetailNo;
	
	/**
	 * 第一笔子订单号
	 */
	private String firstDetailNo;
	
	/**
	 * 第一笔子订单租赁类型
	 */
	private String firstOrderType;
	
	private String microWebModelPhoto;
	
	private BigDecimal beginMileage;//开始里程值
	private BigDecimal endMileage;//结束里程值
	
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOrdersTime() {
		return ordersTime;
	}
	public void setOrdersTime(Date ordersTime) {
		this.ordersTime = ordersTime;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public String getBeginSiteId() {
		return beginSiteId;
	}
	public void setBeginSiteId(String beginSiteId) {
		this.beginSiteId = beginSiteId;
	}
	public Date getOrdersBackTime() {
		return ordersBackTime;
	}
	public void setOrdersBackTime(Date ordersBackTime) {
		this.ordersBackTime = ordersBackTime;
	}
	public String getOrdersBackSiteId() {
		return ordersBackSiteId;
	}
	public void setOrdersBackSiteId(String ordersBackSiteId) {
		this.ordersBackSiteId = ordersBackSiteId;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getEndSiteId() {
		return endSiteId;
	}
	public void setEndSiteId(String endSiteId) {
		this.endSiteId = endSiteId;
	}
	public Integer getOrdersDuration() {
		return ordersDuration;
	}
	public void setOrdersDuration(Integer ordersDuration) {
		this.ordersDuration = ordersDuration;
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
		return actualFee;
	}
	public void setActualFee(BigDecimal actualFee) {
		this.actualFee = actualFee;
	}
	public BigDecimal getTposPayFee() {
		return tposPayFee;
	}
	public void setTposPayFee(BigDecimal tposPayFee) {
		this.tposPayFee = tposPayFee;
	}
	public BigDecimal getUseBalance() {
		return useBalance;
	}
	public void setUseBalance(BigDecimal useBalance) {
		this.useBalance = useBalance;
	}
	public BigDecimal getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(BigDecimal coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getIsAbnormity() {
		return isAbnormity;
	}
	public void setIsAbnormity(Integer isAbnormity) {
		this.isAbnormity = isAbnormity;
	}
	public Integer getIsAppraise() {
		return isAppraise;
	}
	public void setIsAppraise(Integer isAppraise) {
		this.isAppraise = isAppraise;
	}
	public String getOrdersEndOperater() {
		return ordersEndOperater;
	}
	public void setOrdersEndOperater(String ordersEndOperater) {
		this.ordersEndOperater = ordersEndOperater;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Orders() {
		super();
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	
	public String getPayStyle() {
		return payStyle;
	}
	public void setPayStyle(String payStyle) {
		this.payStyle = payStyle;
	}
	
	public Integer getIsBill() {
		return isBill;
	}
	public void setIsBill(Integer isBill) {
		this.isBill = isBill;
	}
	
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", ordersTime=" + ordersTime
				+ ", ordersNo=" + ordersNo + ", carId=" + carId
				+ ", beginTime=" + beginTime + ", beginSiteId=" + beginSiteId
				+ ", ordersBackTime=" + ordersBackTime + ", ordersBackSiteId="
				+ ordersBackSiteId + ", endTime=" + endTime + ", endSiteId="
				+ endSiteId + ", ordersDuration=" + ordersDuration
				+ ", totalFee=" + totalFee + ", state=" + state + ", memberId="
				+ memberId + ", createDate=" + createDate + ", source="
				+ source + ", isAbnormity=" + isAbnormity + ", isAppraise="
				+ isAppraise + ", ordersEndOperater=" + ordersEndOperater
				+ ", operateDate=" + operateDate + ", ts=" + ts + "]";
	}
	public String getVehicleModelName() {
		return vehicleModelName;
	}
	public void setVehicleModelName(String vehicleModelName) {
		this.vehicleModelName = vehicleModelName;
	}
	public String getMemberPhoneNo() {
		return memberPhoneNo;
	}
	public void setMemberPhoneNo(String memberPhoneNo) {
		this.memberPhoneNo = memberPhoneNo;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getPayStyleName() {
		return payStyleName;
	}
	public void setPayStyleName(String payStyleName) {
		this.payStyleName = payStyleName;
	}
	public String getOrdersTimeStr() {
		return ordersTimeStr;
	}
	public void setOrdersTimeStr(String ordersTimeStr) {
		this.ordersTimeStr = ordersTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getOrdersBackTimeStr() {
		return ordersBackTimeStr;
	}
	public void setOrdersBackTimeStr(String ordersBackTimeStr) {
		this.ordersBackTimeStr = ordersBackTimeStr;
	}

	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public OrdersBill getOrdersBill() {
		return ordersBill;
	}
	public void setOrdersBill(OrdersBill ordersBill) {
		this.ordersBill = ordersBill;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public List<OrdersDetail> getOrdersDetail() {
		return ordersDetail;
	}
	public void setOrdersDetail(List<OrdersDetail> ordersDetail) {
		this.ordersDetail = ordersDetail;
	}
	public String getRunningDetailNo() {
		return runningDetailNo;
	}
	public void setRunningDetailNo(String runningDetailNo) {
		this.runningDetailNo = runningDetailNo;
	}
	public String getFirstDetailNo() {
		return firstDetailNo;
	}
	public void setFirstDetailNo(String firstDetailNo) {
		this.firstDetailNo = firstDetailNo;
	}
	public Integer getIsOpenBill() {
		return isOpenBill;
	}
	public void setIsOpenBill(Integer isOpenBill) {
		this.isOpenBill = isOpenBill;
	}
	public String getFirstOrderType() {
		return firstOrderType;
	}
	public void setFirstOrderType(String firstOrderType) {
		this.firstOrderType = firstOrderType;
	}
	public String getMicroWebModelPhoto() {
		return microWebModelPhoto;
	}
	public void setMicroWebModelPhoto(String microWebModelPhoto) {
		this.microWebModelPhoto = microWebModelPhoto;
	}
	public BigDecimal getBeginMileage() {
		return beginMileage;
	}
	public void setBeginMileage(BigDecimal beginMileage) {
		this.beginMileage = beginMileage;
	}
	public BigDecimal getEndMileage() {
		return endMileage;
	}
	public void setEndMileage(BigDecimal endMileage) {
		this.endMileage = endMileage;
	}
	
}
