
package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 车损上报
 * @package:com.dearho.cs.car.pojo
 * @fileName:CarBreak.java
 * @author:赵振明
 * @createTime:2016年7月12日下午5:19:45
 */
public class CarBreak implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	/**
	 * 会员ID
	 */
	private String subscriberId;
	/**
	 * 会员名称
	 */
	private String subscriberName;
	
	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 汽车ID
	 */
	private String carId;
	/**
	 * 车牌号
	 */
	private String carNumber;
	/**
	 * 汽车外表损坏
	 */
	private Integer breakTypeFacade;
	/**
	 * 汽车轮胎损坏
	 */
	private Integer breakTypeTyre;
	/**
	 * 汽车内饰损坏
	 */
	private Integer breakTypeDecoration;
	/**
	 * 汽车玻璃损坏
	 */
	private Integer breakTypeGlass;
	/**
	 * 汽车其他损坏
	 */
	private Integer breakTypeOther;
	/**
	 * 汽车内部异味
	 */
	private Integer breakTypeOdor;
	/**
	 * 损坏描述
	 */
	private String breakDesc;
	/**
	 * 损坏图片链接，以“|”分割
	 */
	private String breakImg;
	/**
	 * 创建时间
	 */
	private Date ts;
	

	
	
	
	
	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public Integer getBreakTypeFacade() {
		return breakTypeFacade;
	}
	public void setBreakTypeFacade(Integer breakTypeFacade) {
		this.breakTypeFacade = breakTypeFacade;
	}
	public Integer getBreakTypeTyre() {
		return breakTypeTyre;
	}
	public void setBreakTypeTyre(Integer breakTypeTyre) {
		this.breakTypeTyre = breakTypeTyre;
	}
	public Integer getBreakTypeDecoration() {
		return breakTypeDecoration;
	}
	public void setBreakTypeDecoration(Integer breakTypeDecoration) {
		this.breakTypeDecoration = breakTypeDecoration;
	}
	public Integer getBreakTypeGlass() {
		return breakTypeGlass;
	}
	public void setBreakTypeGlass(Integer breakTypeGlass) {
		this.breakTypeGlass = breakTypeGlass;
	}
	public Integer getBreakTypeOther() {
		return breakTypeOther;
	}
	public void setBreakTypeOther(Integer breakTypeOther) {
		this.breakTypeOther = breakTypeOther;
	}
	public Integer getBreakTypeOdor() {
		return breakTypeOdor;
	}
	public void setBreakTypeOdor(Integer breakTypeOdor) {
		this.breakTypeOdor = breakTypeOdor;
	}
	public String getBreakDesc() {
		return breakDesc;
	}
	public void setBreakDesc(String breakDesc) {
		this.breakDesc = breakDesc;
	}
	public String getBreakImg() {
		return breakImg;
	}
	public void setBreakImg(String breakImg) {
		this.breakImg = breakImg;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}
