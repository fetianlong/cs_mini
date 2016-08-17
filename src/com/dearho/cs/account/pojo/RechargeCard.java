package com.dearho.cs.account.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.sys.pojo.FieldComment;


/**
 * 充值卡
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 */
public class RechargeCard  implements Serializable{
	
	private static final long serialVersionUID = -7747645354169733905L;
	
	public static final Integer IS_VALID_TRUE =1;
	public static final Integer IS_VALID_FALSE=0; 
	
	public static final String CHANNEL_PORTAL="P";
	public static final String CHANNEL_WECHAT="W";
	public static final String CHANNEL_CONSOLE="C";
	
	@FieldComment("ID")
	private String id;
	@FieldComment("名称")
	private String name;
	@FieldComment("展示名称")
	private String showName;
	@FieldComment("有效起始时间")
	private Date startValidTime;
	@FieldComment("有效终止时间")
	private Date endValidTime;
	@FieldComment("是否有效")
	private Integer isValid;
	@FieldComment("金额")
	private Double amount;
	@FieldComment("渠道")
	private String channel;
	@FieldComment("排序字段")
	private Integer sortField;
	@FieldComment("创建人")
	private String creatorId;
	@FieldComment("创建人姓名")
	private String creatorName;
	@FieldComment("创建时间")
	private Date createTime;
	private Date ts;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartValidTime() {
		return startValidTime;
	}
	public void setStartValidTime(Date startValidTime) {
		this.startValidTime = startValidTime;
	}
	public Date getEndValidTime() {
		return endValidTime;
	}
	public void setEndValidTime(Date endValidTime) {
		this.endValidTime = endValidTime;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Integer getSortField() {
		return sortField;
	}
	public void setSortField(Integer sortField) {
		this.sortField = sortField;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
