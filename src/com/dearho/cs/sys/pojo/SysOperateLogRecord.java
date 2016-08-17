package com.dearho.cs.sys.pojo;

import java.util.Date;

/**
 * 操作日志记录实体
 * @author wangjing
 *
 */
public class SysOperateLogRecord {

	private String id;
	
	private String operatorId;
	
	private String operatorName;
	
	private Date operateDate;
	
	private String operateContent;
	
	private String operateRemark;

	/**
	 * 操作的那一条数据的ID
	 */
	private String recordId;
	
	/**
	 * 所属模块
	 */
	private String modelName;
	
	/**
	 * 关键字段
	 */
	private String keyword;
	
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getOperateRemark() {
		return operateRemark;
	}

	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
