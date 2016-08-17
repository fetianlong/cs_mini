package com.dearho.cs.advice.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 催我建点实体类
 * @fileName:CHotDot.java
 * @author:赵振明
 * @createTime:2016年7月15日上午10:08:50
 *
 */
public class HotDot implements Serializable{

	private static final long serialVersionUID = -1869121092836417290L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 会员ID
	 */
	private String subscriberId;
	/**
	 * 纬度
	 */
	private Double lat;
	/**
	 * 经度
	 */
	private Double lng;
	/**
	 * 添加时间
	 */
	private Date ts;
	
	
	/**
	 * 非持久映射类字段
	 */
	private String subscriberName;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
}
