package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GaoYunpeng
 * @Description 网点巡检
 * @version 1.0 2015年6月30日 下午5:23:53
 */
public class ParkingPatrol implements Serializable{

	private static final long serialVersionUID = 81597282396805620L;

	private String id;
	private String code;
	private String city;
	private String dotId;
	private String userId;
	private Date patrolTime;
	private String remark;
	private Integer isDiscard;
	private String creatorId;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	
	private String userName;
	private String dotName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDotId() {
		return dotId;
	}
	public void setDotId(String dotId) {
		this.dotId = dotId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDotName() {
		return dotName;
	}
	public void setDotName(String dotName) {
		this.dotName = dotName;
	}
	public Date getPatrolTime() {
		return patrolTime;
	}
	public void setPatrolTime(Date patrolTime) {
		this.patrolTime = patrolTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsDiscard() {
		return isDiscard;
	}
	public void setIsDiscard(Integer isDiscard) {
		this.isDiscard = isDiscard;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
}
