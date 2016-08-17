package com.dearho.cs.subscriber.action.register;


/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberRegisterOneAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
/**
 * @Author liusong
 * @Description 会员注册第一步 
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberRegisterPerfectAction extends AbstractAction {

	private static final long serialVersionUID = -3312264055146365391L;
	
	private static final Log logger = LogFactory.getLog(SubscriberRegisterPerfectAction.class);
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	
	private String retMsg;
	
	
	private File upload;  //文件
	private String uploadContentType;  //文件类型
	private String uploadFileName;   //文件名
	
	
	
	
	
	public SubscriberRegisterPerfectAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {
		return SUCCESS;
	}
	
	/**
	 * 第一步会员注册成功
	 * @return
	 */
	public String registerNextStep(){
		return SUCCESS;
	}
	
	

	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public SubscriberService getSubscriberService() {
		return subscriberService;
	}


	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}


	public Subscriber getSubscriber() {
		return subscriber;
	}


	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}



	

}
