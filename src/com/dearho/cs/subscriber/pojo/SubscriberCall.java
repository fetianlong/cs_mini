package com.dearho.cs.subscriber.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.sys.pojo.FieldComment;

public class SubscriberCall implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3260733556834487481L;
	
	
	public  static final Integer LEVEL_NOT_URGENT=0;
	public  static final Integer LEVEL_NORMAL=1;
	public  static final Integer LEVEL_URGENT=2;
	
	
	public  static final Integer STATE_WAIT_HANDLING=0;
	public  static final Integer STATE_HANDLING=1;
	public  static final Integer STATE_HANDLED=2;
	
	
	@FieldComment("ID")
	private String id;
	/**
	 * 来电号码
	 */
	@FieldComment("来电号码")
	private String callPhoneNo;
	
	/** 来电姓名 */
	@FieldComment("来电姓名")
	private String callName;
	
	@FieldComment("会员ID")
	private String subscriberId;
	
	/** 来电时间 */
	@FieldComment("来电时间")
	private Date callTime;
	/** 来电类别 */
	@FieldComment("来电类别")
	private Integer type ;
	/** 来电主题 */
	@FieldComment("来电主题")
	private String title;
	/** 受理内容*/
	@FieldComment("受理内容")
	private String content;
	/** 紧急程度 */
	@FieldComment("紧急程度")
	private Integer level;
	/** 通话时长 */
	@FieldComment("通话时长")
	private Long callDuration;
	/** 受理人Id */
	@FieldComment("受理人ID")
	private String acceptOperatorId;
	/** 受理人名称 */
	@FieldComment("受理人名称")
	private String acceptOperatorName;
	/** 状态  */
	@FieldComment("状态")
	private Integer state;
	
	private Date ts;
	
	
	
	
	public String getId() {
		return id;
	}
	
	
	/** 处理人 */
	/*@FieldComment("处理人")*/
	private String operatorId;
	/** 处理姓名 */
	@FieldComment("处理姓名")
	private String operatorName;
	
	/** 处理结果 */
	@FieldComment("处理结果")
	private String resultDesc;
	/** 处理时间 */
	@FieldComment("处理时间")
	private Date resultTime;
	

	public void setId(String id) {
		this.id = id;
	}
	public String getCallPhoneNo() {
		return callPhoneNo;
	}
	public void setCallPhoneNo(String callPhoneNo) {
		this.callPhoneNo = callPhoneNo;
	}
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public Date getCallTime() {
		return callTime;
	}
	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Long getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(Long callDuration) {
		this.callDuration = callDuration;
	}
	public String getAcceptOperatorId() {
		return acceptOperatorId;
	}
	public void setAcceptOperatorId(String acceptOperatorId) {
		this.acceptOperatorId = acceptOperatorId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Date getResultTime() {
		return resultTime;
	}
	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}
	public String getAcceptOperatorName() {
		return acceptOperatorName;
	}
	public void setAcceptOperatorName(String acceptOperatorName) {
		this.acceptOperatorName = acceptOperatorName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	
	
	
	

}
