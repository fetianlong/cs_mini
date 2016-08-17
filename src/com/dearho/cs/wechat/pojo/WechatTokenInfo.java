package com.dearho.cs.wechat.pojo;

import java.util.Date;


/**
 * 微信服务器响应的信息实体（存储token等）
 * @author wangjing
 *
 */
public class WechatTokenInfo {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 名称
	 */
	private String wxKey;
	
	/**
	 * 名称对应的值
	 */
	private String wxValue;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 过期时间
	 */
	private Date deadTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWxKey() {
		return wxKey;
	}

	public void setWxKey(String wxKey) {
		this.wxKey = wxKey;
	}

	public String getWxValue() {
		return wxValue;
	}

	public void setWxValue(String wxValue) {
		this.wxValue = wxValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}


}
