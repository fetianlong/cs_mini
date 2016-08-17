package com.dearho.cs.orders.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 罚单实体类
 * @author wangjing
 *
 */
public class Tickets {

	/**主键ID*/
	private String id;
	
	/**订单编号*/
	private String ordersNo;
	
	/**子订单编号*/
	private String ordersDetailNo;
	
	/**支付类型*/
	private int payType;
	
	/**罚单总费用*/
	private BigDecimal totalFee;
	
	/**创建时间*/
	private Date createTime;
	
	/**描述*/
	private String description;
	
	/**消费记录ID*/
	private String tradeRecordId;

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

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTradeRecordId() {
		return tradeRecordId;
	}

	public void setTradeRecordId(String tradeRecordId) {
		this.tradeRecordId = tradeRecordId;
	}

	public String getOrdersDetailNo() {
		return ordersDetailNo;
	}

	public void setOrdersDetailNo(String ordersDetailNo) {
		this.ordersDetailNo = ordersDetailNo;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
}
