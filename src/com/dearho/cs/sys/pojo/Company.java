package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GaoYunpeng
 * @Description 公司企业
 * @version 1.0 2015年6月2日 下午3:19:58
 */
public class Company implements Serializable{

	private static final long serialVersionUID = 2840362276300706366L;

	private String id;
	private String code;
	private String name;
	
	private String address;
	private String bizScope;  //经营范围
	private String chairman;  //负责人
	private String telephone;  //公司电话
	private String linkman;   //联系人
	private String linkmanTelephone;  //联系电话
	private Date serviceStart;  //服务开始日期
	private Date serviceEnd;    //服务结束日期
	private String bizType;    //公司业务类型
	
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
	
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBizScope() {
		return bizScope;
	}
	public void setBizScope(String bizScope) {
		this.bizScope = bizScope;
	}
	public String getChairman() {
		return chairman;
	}
	public void setChairman(String chairman) {
		this.chairman = chairman;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkmanTelephone() {
		return linkmanTelephone;
	}
	public void setLinkmanTelephone(String linkmanTelephone) {
		this.linkmanTelephone = linkmanTelephone;
	}
	public Date getServiceStart() {
		return serviceStart;
	}
	public void setServiceStart(Date serviceStart) {
		this.serviceStart = serviceStart;
	}
	public Date getServiceEnd() {
		return serviceEnd;
	}
	public void setServiceEnd(Date serviceEnd) {
		this.serviceEnd = serviceEnd;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
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
