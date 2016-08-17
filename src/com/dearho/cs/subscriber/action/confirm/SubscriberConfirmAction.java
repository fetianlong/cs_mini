package com.dearho.cs.subscriber.action.confirm;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberCheckAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 用户信息审核
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberConfirmAction extends AbstractAction {
	

	private static final long serialVersionUID = -8871763447510238571L;
	private SubscriberConfirmService subscriberConfirmService;
	private SubscriberConfirm subscriberConfirm ;
	
	
	public SubscriberConfirmAction() {
		super();
		subscriberConfirm=new SubscriberConfirm();
	}
		

	@Override
	public String process()  {

		Integer checkResult=subscriberConfirm.getResult();
		if(SubscriberConfirm.RESULT_SUCCESS.equals(checkResult)){
			
			if(subscriberConfirm.getSubscriber()==null || StringUtils.isEmpty(subscriberConfirm.getSubscriber().getDrivingLicenseNo())){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"驾驶证编号不能为空!");
				return ERROR;
			}
		}else{
			if( StringUtils.isEmpty(subscriberConfirm.getResultDesc())){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"审核失败原因不能为空!");
				return ERROR;
			}
			
			subscriberConfirm.setResultDesc(subscriberConfirm.getResultDesc().replaceAll(" ", ""));
		}
	
		
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		try {
			subscriberConfirmService.confirm(subscriberConfirm, operator);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"操作成功");
		return SUCCESS;
	}


	public SubscriberConfirmService getSubscriberConfirmService() {
		return subscriberConfirmService;
	}


	public void setSubscriberConfirmService(
			SubscriberConfirmService subscriberConfirmService) {
		this.subscriberConfirmService = subscriberConfirmService;
	}


	public SubscriberConfirm getSubscriberConfirm() {
		return subscriberConfirm;
	}


	public void setSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		this.subscriberConfirm = subscriberConfirm;
	}

	
	
	

	

	
}
