package com.dearho.cs.subscriber.action.subscriber;



/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberCheckAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberUpdateAction extends AbstractAction {
	


	private static final long serialVersionUID = 2811651585542430049L;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	public SubscriberUpdateAction() {
		super();
		subscriber=new Subscriber();
	}
		

	@Override
	public String process() {
		 Subscriber s=null;
		if(StringUtils.isEmpty(subscriber.getId())){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"会员id为空");
			return SUCCESS;
		}
		s=subscriberService.querySubscriberById(subscriber.getId());
		Object beforeObject=subscriberService.querySubscriberById(subscriber.getId());
		if(s==null){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"会员不存在");
			return SUCCESS;
		}
		
		s.setName(subscriber.getName());
		s.setEventState(subscriber.getEventState());
		s.setEmail(subscriber.getEmail());
		s.setSex(subscriber.getSex());
		s.setWebchatNo(subscriber.getWebchatNo());
		s.setCity(subscriber.getCity());
		s.setCounty(subscriber.getCounty());
		s.setProvince(subscriber.getProvince());
		s.setAddress(subscriber.getAddress());
		s.setPostAddress(subscriber.getPostAddress());
		s.setEmergencyContact(subscriber.getEmergencyContact());
		s.setEmergencyContactAddress(subscriber.getEmergencyContactAddress());
		s.setEmergencyContactPhone(subscriber.getEmergencyContactPhone());
		s.setRemark(subscriber.getRemark());
		
		subscriberService.updateSubscriber(s);
		Object afterObject=subscriberService.querySubscriberById(subscriber.getId());

		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"修改成功");
		
		try {
			SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject, (User)getSession().getAttribute(Constants.SESSION_USER), SystemOperateLogUtil.MODEL_SUBSCRIBER, s.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
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
