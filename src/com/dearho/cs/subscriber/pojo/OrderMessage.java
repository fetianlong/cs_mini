/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file OrderMessage.java creation date:[2015-6-12 下午02:59:40] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.pojo;

import java.io.Serializable;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-12
 *
 */
public class OrderMessage implements Serializable {

	private static final long serialVersionUID = -4515059695178142638L;
	
	
	public static final Integer CODE_SUCCESS_ACCOUNT=1;
	public static final Integer CODE_SUCCESS_CREDIT_CARD=2;
	public static final Integer CODE_SUCCESS_ALL=3;
	
	public static final Integer CODE_FAIL=5;
	
	
	private Integer code;
	private String description;
	private Subscriber subscriber;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	public void setResult(Integer code,String description){
		this.code=code;
		this.description=description;
	}
	

}
