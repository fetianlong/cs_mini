package com.dearho.cs.subscriber.action.call;

import org.apache.cxf.common.util.StringUtils;

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
public class SubscriberCallHandlingUpdateAction extends AbstractAction {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4041640479202439050L;
	
	private SubscriberCallService subscriberCallService;
	
	
	private SubscriberCall subscriberCall ;
	
	public SubscriberCallHandlingUpdateAction(){
		super();
		
	}
	@Override
	public String process() {
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		
		if(StringUtils.isEmpty(subscriberCall.getId()) || StringUtils.isEmpty(subscriberCall.getResultDesc())){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"参数缺失!");
			return SUCCESS;
		}
		SubscriberCall beforeObject=subscriberCallService.getSubscriberCallById(subscriberCall.getId());
		
		subscriberCallService.callHandling(subscriberCall, operator);
		SubscriberCall afterObject=subscriberCallService.getSubscriberCallById(subscriberCall.getId());
		
		
		try {
			SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject, (User)getSession().getAttribute(Constants.SESSION_USER), SystemOperateLogUtil.MODEL_CALL, afterObject.getCallName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"保存成功!");
	
		
		return SUCCESS;
	}
	
	public SubscriberCall getSubscriberCall() {
		return subscriberCall;
	}
	public void setSubscriberCall(SubscriberCall subscriberCall) {
		this.subscriberCall = subscriberCall;
	}
	public void setSubscriberCallService(SubscriberCallService subscriberCallService) {
		this.subscriberCallService = subscriberCallService;
	}
	
	
	
}
