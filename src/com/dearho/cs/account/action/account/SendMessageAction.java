package com.dearho.cs.account.action.account;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-10-20
 *
 */
public class SendMessageAction extends AbstractAction {

	private static final long serialVersionUID = 5319491601258587610L;

	
	private SubscriberService subscriberService;
	private String phoneNo;
	private String message;
	private String subscriberId;
	public SendMessageAction(){
		super();
	}

	@Override
	public String process() {
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		if(operator==null){
			return "login";
		}
		if(StringUtils.isEmpty(phoneNo)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"手机号码不能为空");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(message)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"短信内容不能为空！");
			return SUCCESS;
		}
		if(message.length()>200){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"短信内容过长！");
			return SUCCESS;
		}
		try {
			SMSUtil.sendSMS(phoneNo, message,SMSRecord.TYPE_NOTICE,operator.getId(),operator.getName());
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"下发短信失败!");
			log.error(e);
		}
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"下发成功！");
		
		return SUCCESS;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public String gotoSendMessage(){
		if(!StringUtils.isEmpty(subscriberId)){
			Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
			phoneNo=subscriber.getPhoneNo();
		}
		
		return SUCCESS;
	}
	
	
	
}
