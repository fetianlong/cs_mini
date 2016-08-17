package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Description: 组菜单权限
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class GroupMenu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 组id
	 */
	private String groupId;
	
	/**
	 * 菜单id
	 */
	private String menuId;
	/**
	 * 创建人id
	 */
	private String creatorId;

	private Date ts;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}

	

}
