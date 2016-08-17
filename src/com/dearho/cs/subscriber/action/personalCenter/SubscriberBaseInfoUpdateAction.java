/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubscriberChangePassword.java creation date:[2015-5-27 上午11:14:06] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.action.personalCenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-27
 *
 */
public class SubscriberBaseInfoUpdateAction extends AbstractAction{
	
	
	private static final long serialVersionUID = -3462403622732184508L;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;

	public SubscriberBaseInfoUpdateAction(){
		super();
		subscriber=new Subscriber();
	}
	@Override
	public String process() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		if(Subscriber.EVENT_STATE_HALF.equals(s.getEventState())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已冻结，不能进行会员相关操作。<br/> 如有疑问，请联系客服。");
			return SUCCESS;
		}
		if(!StringUtils.isEmpty(subscriber.getEmail())){
	        String check = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";    
	        Pattern regex = Pattern.compile(check);    
            Matcher m = regex.matcher(subscriber.getEmail()); 
            if(!m.matches()){
            	result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "邮箱格式有误请重新输入。");
    			return SUCCESS;
            }
		}
		s=subscriberService.querySubscriberById(s.getId());
		Object beforeObject=subscriberService.querySubscriberById(s.getId());;
	
	
		s.setCity(subscriber.getCity());
		s.setProvince(subscriber.getProvince());
		s.setCounty(subscriber.getCounty());
		s.setAddress(subscriber.getAddress());
		
		s.setEmail(subscriber.getEmail());
		s.setEmergencyContact(subscriber.getEmergencyContact());
		s.setEmergencyContactAddress(subscriber.getEmergencyContactAddress());
		s.setEmergencyContactPhone(subscriber.getEmergencyContactPhone());
		
		
		subscriberService.updateSubscriber(s);
		
		//更新session 用户状态可能变更
		getSession().setAttribute(Constants.SESSION_SUBSCRIBER,s);
		
		Object afterObject=s;

		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"修改成功");
		
		try {
			SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject,null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO, s.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String updateBillInfo() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);

		s.setBillPostCode(subscriber.getBillPostCode());
		s.setBillTitle(subscriber.getBillTitle());
		s.setPostAddress(subscriber.getPostAddress());
		
		subscriberService.updateSubscriber(s);
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
		//更新session 用户状态可能变更
		getSession().setAttribute(Constants.SESSION_SUBSCRIBER,s);
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
