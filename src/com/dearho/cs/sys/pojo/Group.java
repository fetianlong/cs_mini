package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Description: 组
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class Group implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组主键
	 */
	private String groupId;
	
	/**
	 * 组名称
	 */
	private String groupName;
	
	/**
	 * 组备注
	 */
	private String groupRemark;
	/**
	 * 组创建人id
	 */
	private String groupCreatorId;
	/**
	 * 组创建日期
	 */
	private Date groupCreateTime;
	private Date ts;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupRemark() {
		return groupRemark;
	}
	public void setGroupRemark(String groupRemark) {
		this.groupRemark = groupRemark;
	}
	public String getGroupCreatorId() {
		return groupCreatorId;
	}
	public void setGroupCreatorId(String groupCreatorId) {
		this.groupCreatorId = groupCreatorId;
	}
	public Date getGroupCreateTime() {
		return groupCreateTime;
	}
	public void setGroupCreateTime(Date groupCreateTime) {
		this.groupCreateTime = groupCreateTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
	

}
