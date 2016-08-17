package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GaoYunpeng
 * @Description 行政区划
 * @version 1.0 2015年5月27日 下午4:30:07
 */
public class AdministrativeArea implements Serializable{

	private static final long serialVersionUID = 3132643676491998116L;

	private String id;
	private String code;
	private String name;
	private String parentCode;
	private String creatorId;
	private Date createTime;
	private Date updateTime;
	private Date ts;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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
