package com.dearho.cs.subscriber.action.call;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dearho.cs.subscriber.pojo.SubscriberCall;
import com.dearho.cs.subscriber.service.SubscriberCallService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author 
 * @Description 
 * @Version 1.0,2015-5-20
 *
 */
public class SubscriberCallAddAction extends AbstractAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SubscriberCallService subscriberCallService;
	private SubscriberCall subscriberCall ;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SubscriberCallAddAction(){
		super();
		subscriberCall=new SubscriberCall();
	}
	@Override
	public String process() {
	
		
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		subscriberCall.setCallTime(new Date());
		subscriberCall.setAcceptOperatorId(operator.getId());
		subscriberCall.setAcceptOperatorName(operator.getName());
		subscriberCall.setTs(new Date());
		subscriberCallService.addSubscriberCall(subscriberCall);
		
		
		
		
		try {
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("手机号码", subscriberCall.getCallPhoneNo());
			SystemOperateLogUtil.sysAddOperateLog(subscriberCall.getId(), (User)getSession().getAttribute(Constants.SESSION_USER), SystemOperateLogUtil.MODEL_CALL,contentMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"保存成功!");
	
		
		return SUCCESS;
	}
	public SubscriberCallService getSubscriberCallService() {
		return subscriberCallService;
	}
	public void setSubscriberCallService(SubscriberCallService subscriberCallService) {
		this.subscriberCallService = subscriberCallService;
	}
	public SubscriberCall getSubscriberCall() {
		return subscriberCall;
	}
	public void setSubscriberCall(SubscriberCall subscriberCall) {
		this.subscriberCall = subscriberCall;
	}
	
	
	
	
	
}
