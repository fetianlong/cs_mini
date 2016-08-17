package com.dearho.cs.advice.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.subscriber.pojo.Subscriber;

/**
 * 一键投诉
 * @author jyt
 * @since 2016年7月15日 上午11:43:41
 */
public class Feedback  implements Serializable{

	private static final long serialVersionUID = -6385236020784904855L;
	/** ID**/
	private String id;
	/** 会员id**/
	private String subscriberId;
	/** 意见反馈**/
	private String feedbackDesc;
	/** 联系方式**/
	private String contactType;
	/** 投诉照片 ：以“|”分割**/
	private String feedbackImg;
	/****投诉处理状态***/
	private String state;
	/***处理备注***/
	private String comment;
	/** 创建时间**/
	private Date ts;
	
	private Subscriber subscriber;
	
	/***会员姓名**/
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getFeedbackDesc() {
		return feedbackDesc;
	}
	public void setFeedbackDesc(String feedbackDesc) {
		this.feedbackDesc = feedbackDesc;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getFeedbackImg() {
		return feedbackImg;
	}
	public void setFeedbackImg(String feedbackImg) {
		this.feedbackImg = feedbackImg;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", subscriberId=" + subscriberId
				+ ", feedbackDesc=" + feedbackDesc + ", contactType="
				+ contactType + ", feedbackImg=" + feedbackImg + ", state="
				+ state + ", comment=" + comment + ", ts=" + ts
				+ ", subscriber=" + subscriber + ", name=" + name + "]";
	}
	public Feedback(String id, String subscriberId, String feedbackDesc,
			String contactType, String feedbackImg, String state,
			String comment, Date ts,String name) {
		super();
		this.id = id;
		this.subscriberId = subscriberId;
		this.feedbackDesc = feedbackDesc;
		this.contactType = contactType;
		this.feedbackImg = feedbackImg;
		this.state = state;
		this.comment = comment;
		this.ts = ts;
		this.name = name;
	}
	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

}
