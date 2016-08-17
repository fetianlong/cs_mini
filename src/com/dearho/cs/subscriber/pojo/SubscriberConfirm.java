/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubConfirm.java creation date:[2015-5-21 下午04:55:08] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.pojo;

import java.io.Serializable;

import com.dearho.cs.sys.pojo.BusinessFlow;

/**
 * @Author liusong
 * @Description 会员审核
 * @Version 1.0,2015-5-21
 *
 */
public class SubscriberConfirm implements Serializable {

	private static final long serialVersionUID = 881544314760215177L;
	

	public static final Integer RESULT_SUCCESS =1;//审核成功
	public static final Integer RESULT_FAIL=0;//审核失败
	
	
	public static final Integer IS_COMPLETE_TRUE=1;//审核结束
	public static final Integer IS_COMPLETE_FALSE=0;//审核未结束
	public static final Integer IS_COMPLETE_EXCEPTION=2;//审核出现异常
	
	
	private String id;
	/**
	 * 会员id
	 */
	private String subscriberId;
	/**
	 * 审核结果描述
	 */
	private Integer result;
	
	private Integer isComplete;
	
	private Subscriber subscriber;
	
	private String  attribute_1;//预留属性
	private String  attribute_2;//预留属性
	private String  attribute_3;//预留属性
	private String  attribute_4;//预留属性
	
	private BusinessFlow businessFlow;
	
	private String resultDesc;
	private String remark;

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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public BusinessFlow getBusinessFlow() {
		return businessFlow;
	}

	public void setBusinessFlow(BusinessFlow businessFlow) {
		this.businessFlow = businessFlow;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getAttribute_1() {
		return attribute_1;
	}

	public void setAttribute_1(String attribute_1) {
		this.attribute_1 = attribute_1;
	}

	public String getAttribute_2() {
		return attribute_2;
	}

	public void setAttribute_2(String attribute_2) {
		this.attribute_2 = attribute_2;
	}

	public String getAttribute_3() {
		return attribute_3;
	}

	public void setAttribute_3(String attribute_3) {
		this.attribute_3 = attribute_3;
	}

	public String getAttribute_4() {
		return attribute_4;
	}

	public void setAttribute_4(String attribute_4) {
		this.attribute_4 = attribute_4;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	

	

	
	
	

}
