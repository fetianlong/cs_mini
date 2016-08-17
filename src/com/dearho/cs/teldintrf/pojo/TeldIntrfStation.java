package com.dearho.cs.teldintrf.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TeldIntrfStation implements Serializable{

	private static final long serialVersionUID = 1714501193449733166L;

	private String id;
	private String staCode;//电站编号 如： "3702120241" 
	private String staName;//电站名称
	private String staType;//电站类型
	private String staOpstate;//电站运营状态
	private String province;//省名称
	private String city;//市名称
	private String region;//区名称
	private String staAddress;//电站详细地址
	private BigDecimal lng;//电站经度
	private BigDecimal lat;//电站纬度
	private BigDecimal price;//电站收费标准
	private Integer acNum;//直流终端总数
	private Integer dcNum;//交流终端总数
	private Integer acableNum;//可用直流数量
	private Integer dcableNum;//可用交流数量
	private Date createTime;
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStaCode() {
		return staCode;
	}
	public void setStaCode(String staCode) {
		this.staCode = staCode;
	}
	public String getStaName() {
		return staName;
	}
	public void setStaName(String staName) {
		this.staName = staName;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getStaAddress() {
		return staAddress;
	}
	public void setStaAddress(String staAddress) {
		this.staAddress = staAddress;
	}
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
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
	
	
}
