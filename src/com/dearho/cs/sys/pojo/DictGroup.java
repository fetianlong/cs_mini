package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GaoYunpeng
 * @Description 字典组实体类
 * @version 1.0 2015年5月19日 下午3:02:37
 */
public class DictGroup implements Serializable{

	private static final long serialVersionUID = -7606753000978146034L;

	private String id;
	private String code;
	private String remark;

	private Date createTime;
	private String creatorId;
	private Date updateTime;
	private Date ts;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
