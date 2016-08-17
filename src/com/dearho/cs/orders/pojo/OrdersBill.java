/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file OrdersBill.java creation date: [2015年6月11日 下午3:11:18] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.orders.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dearho.cs.sys.pojo.FieldComment;

/**
 * @author lvlq
 * @Description:(订单发票)
 * @Version 1.0, 2015年6月11日
 */
public class OrdersBill implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String TYPE_INDIVIDUAL = "0";//个人发票
	public static final String TYPE_CORPORATE = "1";//企业发票
	
	/**主键Id*/
	private String id;
	/**发票类型*/
	private String type;
	
	@FieldComment("发票编号") 
	private String billNo;
	
	@FieldComment("发票抬头") 
	private String title;
	
	@FieldComment("发票总金额") 
	private BigDecimal totalFee;
	
	@FieldComment("收件人") 
	private String recipients;
	
	@FieldComment("地址") 
	private String address;
	
    @FieldComment("快递名称") 
	private String expresName;
    
    @FieldComment("快递单号") 
	private String expresNo;
    
    @FieldComment("发快递日期") 
	private Date sendDate;
	
	@FieldComment("邮政编码") 
	private String postcode;

	@FieldComment("联系电话") 
	private String telphone;
	
	@FieldComment("创建时间") 
	private Date createDate;
	
	@FieldComment("处理人") 
	private String operatorName;
	
    @FieldComment("备注") 
	private String refuseReason;
    
    @FieldComment("发票状态") 
	private String stateDesc;
    
	/**状态*/
	private String state;
    /**是否收到*/
	private String isRecive;
	/**操作人ID*/
	private String operatorId;
	
	//添加subscriber_id 会员ID 满足 SUM(orders RMB)-SUM( Bill RMB) -  By subscriber's  ...
	private String subscriberId;
	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	@Override
	public String toString() {
		return "OrdersBill [id=" + id + ", type=" + type + ", title=" + title
				+ ", recipients=" + recipients + ", address=" + address
				+ ", postcode=" + postcode + ", telphone=" + telphone + "]";
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getExpresName() {
		return expresName;
	}
	public void setExpresName(String expresName) {
		this.expresName = expresName;
	}
	public String getExpresNo() {
		return expresNo;
	}
	public void setExpresNo(String expresNo) {
		this.expresNo = expresNo;
	}
	public String getIsRecive() {
		return isRecive;
	}
	public void setIsRecive(String isRecive) {
		this.isRecive = isRecive;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getStateDesc() {
		String stateDesc = "";
		if(state.equals("0")){
			stateDesc = "发票未开";
		}else if(state.equals("1")){
			stateDesc = "审核未通过";
		}else if(state.equals("2")){
			stateDesc = "审核通过";
		}else if(state.equals("3")){
			stateDesc = "发票已开";
		}else{
			stateDesc = "未处理";
		}
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

}
