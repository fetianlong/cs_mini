/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file BusinessFlow.java creation date:[2015-5-21 下午04:58:00] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.subscriber.pojo.Subscriber;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-21
 *
 */
public class BusinessFlow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6323387202058583372L;
	public  static  final Integer BUSINESS_TYPE_CONFIRM=1;//会员资料审核
	public  static  final Integer BUSINESS_TYPE_APPLY_REFUND=2;//申请退款流程
	
	public  static  final Integer RESULT_FAIL=0;
	public  static  final Integer RESULT_SUCCESS=1;
	
	private String id;
	private String confirmId;
	private String 	businessId;
	private Integer businessType;
	private String applicantId;
	private Date applyTime;
	private String 	reviewerId;
	private Date  reviewTime;
	private Integer result;
	private String resultDesc;
	private Date ts;
	
	/** 申请人，前端展示 **/
	private Subscriber applicantSubscriber;
	private Account   applicantAccount;
	
	/** 查询条件预留**/
	private Date applyDateStart;
	private Date applyDateEnd;
	
	private Date reviewDateStart;
	private Date reviewDateEnd;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public Date getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Date getApplyDateStart() {
		return applyDateStart;
	}
	public void setApplyDateStart(Date applyDateStart) {
		this.applyDateStart = applyDateStart;
	}
	public Date getApplyDateEnd() {
		return applyDateEnd;
	}
	public void setApplyDateEnd(Date applyDateEnd) {
		this.applyDateEnd = applyDateEnd;
	}
	public Date getReviewDateStart() {
		return reviewDateStart;
	}
	public void setReviewDateStart(Date reviewDateStart) {
		this.reviewDateStart = reviewDateStart;
	}
	public Date getReviewDateEnd() {
		return reviewDateEnd;
	}
	public void setReviewDateEnd(Date reviewDateEnd) {
		this.reviewDateEnd = reviewDateEnd;
	}
	public Subscriber getApplicantSubscriber() {
		return applicantSubscriber;
	}
	public void setApplicantSubscriber(Subscriber applicantSubscriber) {
		this.applicantSubscriber = applicantSubscriber;
	}
	public Account getApplicantAccount() {
		return applicantAccount;
	}
	public void setApplicantAccount(Account applicantAccount) {
		this.applicantAccount = applicantAccount;
	}
	public String getConfirmId() {
		return confirmId;
	}
	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}
	
	
	
}
