package com.dearho.cs.advice.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.subscriber.pojo.Subscriber;

/**
 * 消息记录表（记录消息发送情况）
 * @author jyt
 * @since 2016年7月20日 下午1:57:40
 */
public class InformRecord implements Serializable {

	private static final long serialVersionUID = 109132925453060506L;
	
	private String id;
	/**消息ID**/
	private String informId;
	/**会员ID***/
	private String subscriberId;
	/***0未读1已读***/
	private Integer isRead;
	/**创建时间***/
	private Date ts;
	
	
	@Override
	public String toString() {
		return "InformRecord [id=" + id + ", informId=" + informId
				+ ", subscriberId=" + subscriberId + ", isRead=" + isRead
				+ ", ts=" + ts + "]";
	}
	
	public InformRecord(String informId, String subscriberId,
			Integer isRead) {
		super();
		this.informId = informId;
		this.subscriberId = subscriberId;
		this.isRead = isRead;
	}


	public InformRecord( String informId, String subscriberId,
			Integer isRead, Date ts) {
		super();
		this.informId = informId;
		this.subscriberId = subscriberId;
		this.isRead = isRead;
		this.ts = ts;
	}
	public InformRecord() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInformId() {
		return informId;
	}
	public void setInformId(String informId) {
		this.informId = informId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}

}
