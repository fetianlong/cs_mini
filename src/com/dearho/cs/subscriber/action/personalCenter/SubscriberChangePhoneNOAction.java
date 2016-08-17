/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubscriberChangePassword.java creation date:[2015-5-27 上午11:14:06] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.action.personalCenter;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.Sha1Util;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-27
 *
 */
public class SubscriberChangePhoneNOAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6162385046376940993L;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	private SMSCodeService smsCodeService;
	private static final Log logger = LogFactory.getLog(SubscriberChangePasswordAction.class);


	public SubscriberChangePhoneNOAction(){
		super();
		subscriber=new Subscriber();
	}
	@Override
	public String process() {
		
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		if(Subscriber.EVENT_STATE_HALF.equals(((Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER)).getEventState())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已冻结，不能进行会员相关操作。<br/> 如有疑问，请联系客服。");
			return SUCCESS;
		}
		
		
		//新手机号
		if(StringUtils.isEmpty(subscriber.getPhoneNo())||!isPhone(subscriber.getPhoneNo())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "新手机号格式不正确！");
			return SUCCESS;
		}
		//短信验证码校验
		String code=getRequest().getParameter("code");
		if(StringUtils.isEmpty(code)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入验证码！");
			return SUCCESS;
		}
		
	
		SMSCode smsCode=new SMSCode();
		smsCode.setPhoneNo(subscriber.getPhoneNo());
		smsCode.setType(Constants.SUBSCIRBER_REGISTER_PHONE_CODE);
		smsCode.setChannel(Account.PAY_CHANNEL_APP);
		smsCode=smsCodeService.getLatestSMSCode(smsCode,Constants.REGISTER_SMS_VALID_MINUTE );
		if(smsCode==null){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "验证码已失效请重新获取!");
			return SUCCESS;
		}
		
		
		if(!code.equals(smsCode.getCode())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码有误！");
			return SUCCESS;
		}
		//手机号码已有校验
		Subscriber s=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
		if(s!=null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此手机号已注册！");
			return SUCCESS;
		}
		s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		s=subscriberService.querySubscriberById(s.getId());
		
	
		Object beforeObject=subscriberService.querySubscriberById(s.getId());;
		
		s.setPhoneNo(subscriber.getPhoneNo());
		subscriberService.updateSubscriber(s);
		getSession().setAttribute(Constants.SESSION_SUBSCRIBER,s);
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "修改成功！");
		
		Object afterObject=s;
		try {
			SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject,null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO, s.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 会员注册--下发手机验证码
	 * @return
	 */
	public String sendChangePhoneVerificationCode(){
		
		String phoneNo=getRequest().getParameter("phoneNo");
		try {
			 result=subscriberService.sendSMSCode(Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE,phoneNo , Account.PAY_CHANNEL_PORTAL);
			if("ok".equals(result)){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, result);
			}
			
			result = Ajax.JSONResult(Constants.APP_RESULT_CODE_FAIL, "发送短信失败！");
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.JSONResult(Constants.APP_RESULT_CODE_FAIL, "发送短信失败！");
		}
		return SUCCESS;
	}
	
	private  Boolean isPhone(String phoneNo){
		Pattern pattern = Pattern.compile("^0?(1)[0-9]{10}$");
		Matcher matcher = pattern.matcher(phoneNo);
		return matcher.matches();
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
	public SMSCodeService getSmsCodeService() {
		return smsCodeService;
	}
	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
	}

	
	
}
