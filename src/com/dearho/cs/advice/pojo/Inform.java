package com.dearho.cs.advice.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.subscriber.pojo.Subscriber;

/**
 * 系统通知 消息实体类
 * @author jyt
 * @since 2016年7月20日 下午1:56:21
 */
public class Inform implements Serializable {
	private static final long serialVersionUID = 3171168402705503216L;
	
	private String id;//ID
	/**消息体**/
	private String informContent;
	/**通知0、短信1**/
	private Integer informType;
	/**1群发、2指定用户**/
	private Integer informSendType;
	/**创建日期***/
	private Date ts;
	
	//查询条件时
	private Integer isRead;//0未读1已读
	//会员姓名查询
	private String subscriberName;
	//查询条件 添加通知时
	private String subscriberId;//会员ID
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInformContent() {
		return informContent;
	}
	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}
	public Integer getInformType() {
		return informType;
	}
	public void setInformType(Integer informType) {
		this.informType = informType;
	}
	public Integer getInformSendType() {
		return informSendType;
	}
	public void setInformSendType(Integer informSendType) {
		this.informSendType = informSendType;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getSubscriberName() {
		return subscriberName;
	}
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	
	@Override
	public String toString() {
		return "Inform [id=" + id + ", informContent=" + informContent
				+ ", informType=" + informType + ", informSendType="
				+ informSendType + ", ts=" + ts + "]";
	}
	public Inform() {
		super();
	}
	public Inform(String id, String informContent, Integer informType,
			Integer informSendType, Date ts) {
		super();
		this.id =id;
		this.informContent = informContent;
		this.informType = informType;
		this.informSendType = informSendType;
		this.ts = ts;
	}
	public Inform(String id, String informContent, Integer informType,
			Integer informSendType, Date ts,  String subscriberId,String name) {
		super();
		this.id = id;
		this.informContent = informContent;
		this.informType = informType;
		this.informSendType = informSendType;
		this.ts = ts;
		//this.isRead = isRead;
		this.subscriberId = subscriberId;
		this.subscriberName =name;
	}
	
	
}
