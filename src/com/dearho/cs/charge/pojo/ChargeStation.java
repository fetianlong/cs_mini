package com.dearho.cs.charge.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author GaoYunpeng
 * @Description 充电站
 * @version 1.0 2015年6月2日 下午4:09:35
 */
public class ChargeStation implements Serializable{

	private static final long serialVersionUID = -4202966135865296648L;

	private String id;
	private String code;
	private String name;
	private String staType;//电站类型
	private String staOpstate;//电站运营状态
	private BigDecimal price;//电站收费标准
	private String address;
	private double lat;
	private double lng;
	private String manufacturer; // 生产厂家
	private Integer acNum;//直流终端总数
	private Integer dcNum;//交流终端总数
	private Integer acableNum;//可用直流数量
	private Integer dcableNum;//可用交流数量
	private Date createTime;
	private String creatorId;
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
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
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
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getStaType() {
		return staType;
	}
	public void setStaType(String staType) {
		this.staType = staType;
	}
	public String getStaOpstate() {
		return staOpstate;
	}
	public void setStaOpstate(String staOpstate) {
		this.staOpstate = staOpstate;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getAcNum() {
		return acNum;
	}
	public void setAcNum(Integer acNum) {
		this.acNum = acNum;
	}
	public Integer getDcNum() {
		return dcNum;
	}
	public void setDcNum(Integer dcNum) {
		this.dcNum = dcNum;
	}
	public Integer getAcableNum() {
		return acableNum;
	}
	public void setAcableNum(Integer acableNum) {
		this.acableNum = acableNum;
	}
	public Integer getDcableNum() {
		return dcableNum;
	}
	public void setDcableNum(Integer dcableNum) {
		this.dcableNum = dcableNum;
	}
	
	
}
